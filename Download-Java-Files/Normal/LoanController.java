package propra2.leihOrDie.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import propra2.leihOrDie.model.Transaction;
import propra2.leihOrDie.propay.ProPayWrapper;
import propra2.leihOrDie.response.ResponseBuilder;
import propra2.leihOrDie.security.AuthorizationHandler;
import propra2.leihOrDie.dataaccess.*;
import propra2.leihOrDie.form.LoanForm;
import propra2.leihOrDie.model.Item;
import propra2.leihOrDie.model.Loan;
import propra2.leihOrDie.model.User;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@Controller
public class LoanController {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    private AuthorizationHandler authorizationHandler = new AuthorizationHandler(sessionRepository);
  
    private ResponseBuilder responseBuilder = new ResponseBuilder();
    @Autowired
    private ProPayWrapper proPayWrapper;

    @PostMapping("/request/{itemId}")
    public ResponseEntity requestLoan(Model model, @Valid LoanForm form, BindingResult bindingResult,
                                      @CookieValue(value="SessionID", defaultValue="") String sessionId,
                                      @PathVariable Long itemId) {
        if (bindingResult.hasErrors()) {
            return responseBuilder.createBadRequestResponse("Bitte gewünschten Zeitraum eingeben!");
        }

        User user = sessionRepository.findUserBySessionCookie(sessionId);
        Item item = itemRepository.findById(itemId).get();

        if (form.getLoanDuration() == 0) {
            return responseBuilder.createBadRequestResponse("Die minimale Ausleihdauer beträgt einen Tag.");
        }

        if (!item.isAvailability()) {
           return responseBuilder.createBadRequestResponse("Dieser Gegenstand ist nicht verfügbar.");
        }

        if (form.getLoanDuration() > item.getAvailableTime()) {
            return responseBuilder.createBadRequestResponse("Die maximale Ausleihdauer ist überschritten.");
        }

        if (item.getUser() == user) {
            return responseBuilder.createBadRequestResponse("Du kannst deinen eigenen Gegenstand nicht ausleihen.");
        }

        Long proPayReservationId;
        try {
            proPayReservationId = proPayWrapper.reserve(user.getEmail(), item.getUser().getEmail(), item.getDeposit()).getId();
        } catch (Exception e) {
            return responseBuilder.createBadRequestResponse("ProPay Fehler");
        }

        Loan loan = new Loan("pending", form.getLoanDuration(), user, item, proPayReservationId);
        loanRepository.save(loan);

        item.setAvailability(false);
        itemRepository.save(item);
        return responseBuilder.createSuccessResponse("Eine Anfrage wurde gesendet.");
    }

    @PostMapping("/request/accept/{loanId}")
    public ResponseEntity changeStatusToAccepted(Model model, @PathVariable Long loanId,
                                                 @CookieValue(value="SessionID", defaultValue="") String sessionId) {
        Loan loan = loanRepository.findById(loanId).get();

        if (!authorizationHandler.isAuthorized(sessionId, loan.getItem())) {
            return responseBuilder.createUnauthorizedResponse();
        }

        loan.setState("accepted");
        loanRepository.save(loan);

        return responseBuilder.createSuccessResponse("Bestätigt.");
    }

    @PostMapping("/request/decline/{loanId}")
    public ResponseEntity changeStatusToDeclined(Model model, @PathVariable Long loanId,
                                                 @CookieValue(value="SessionID", defaultValue="") String sessionId) {
        Loan loan = loanRepository.findById(loanId).get();
        Item item = itemRepository.findById(loan.getItem().getId()).get();

        if (!authorizationHandler.isAuthorized(sessionId, loan.getItem())) {
            return responseBuilder.createUnauthorizedResponse();
        }

        loan.setState("declined");
        loanRepository.save(loan);

        item.setAvailability(true);
        itemRepository.save(item);

        return responseBuilder.createSuccessResponse("Bestätigt.");
    }

    @PostMapping("/request/activate/{loanId}")
    public ResponseEntity changeStatusToActive(Model model, @PathVariable Long loanId, @CookieValue(value="SessionID", defaultValue="") String sessionId) {
        Loan loan = loanRepository.findById(loanId).get();

        if (!authorizationHandler.isAuthorized(sessionId, loan.getItem().getUser())) {
            return responseBuilder.createUnauthorizedResponse();
        }

        loan.setDayOfRental(LocalDate.now());
        loan.setState("active");
        loanRepository.save(loan);

        return responseBuilder.createSuccessResponse("Bestätigt");
    }

    @PostMapping("/request/return/{loanId}")
    public ResponseEntity changeStatusToCompleted(Model model, @PathVariable Long loanId, @CookieValue(value="SessionID", defaultValue="") String sessionId) {
        User user = sessionRepository.findUserBySessionCookie(sessionId);
        Loan loan = loanRepository.findById(loanId).get();

        if (!authorizationHandler.isAuthorized(sessionId, loan.getItem().getUser())) {
            return responseBuilder.createUnauthorizedResponse();
        }

        double amount = loan.getDuration() * loan.getItem().getCost();

        loan.setDayOfReturn(LocalDate.now());
      
        try {
            proPayWrapper.transferMoney(loan.getUser().getEmail(), user.getEmail(), amount);
            Transaction transaction = new Transaction(loan.getUser(), user, amount, "Danke für die Ausleihe");
            transactionRepository.save(transaction);
        } catch (Exception e) {
            loan.setState("error");
            loanRepository.save(loan);
            return responseBuilder.createBadRequestResponse("Es war nicht möglich den Betrag zu überweisen. Bitte sende eine Email mit der genauen Beschreibung Deines Problems und dem Betreff \"" + sessionRepository.findUserBySessionCookie(sessionId).getUsername() + " - " + loan.getId().toString() + "\" an conflict@leihordie.de");
        }

        loan.setState("completed");
        loanRepository.save(loan);

        return responseBuilder.createSuccessResponse("Erfolgreich!");
    }
  
    @Scheduled(cron = "0 0 7 * * ?")
    private void determineExceededLoans() {
        List<Loan> allActivLoans = loanRepository.findLoansByState("active");
        LocalDate now = LocalDate.now();

        for (Loan loan: allActivLoans) {
            saveExceededLoans(loan, now);
        }
    }

    private void saveExceededLoans(Loan loan, LocalDate now) {
        if (now.isAfter(loan.getDayOfRental().plusDays(loan.getDuration()))) {
            loan.setExceeded(true);
            loanRepository.save(loan);
        }
    }
}
