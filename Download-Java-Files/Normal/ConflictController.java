package propra2.leihOrDie.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import propra2.leihOrDie.propay.ProPayWrapper;
import propra2.leihOrDie.dataaccess.*;
import propra2.leihOrDie.model.*;
import propra2.leihOrDie.response.ResponseBuilder;
import propra2.leihOrDie.security.AuthorizationHandler;

import java.util.List;

@Controller
public class ConflictController {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    BuyRepository buyRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    TransactionRepository transactionRepository;

    private ResponseBuilder responseBuilder = new ResponseBuilder();
    @Autowired
    private ProPayWrapper proPayWrapper;
    @Autowired
    private AuthorizationHandler authorizationHandler = new AuthorizationHandler(sessionRepository);


    @PostMapping("/conflict/open/{loanId}")
    public ResponseEntity openConflict(Model model, @PathVariable Long loanId,
                                       @CookieValue(value="SessionID", defaultValue="") String sessionId) {
        if (!loanRepository.findById(loanId).isPresent()) {
            return responseBuilder.createBadRequestResponse("Die angefragte Ausleihe existiert nicht.");
        }

        Loan loan = loanRepository.findById(loanId).get();

        if (!(authorizationHandler.isAuthorized(sessionId, loan.getItem()) ||
                authorizationHandler.isAuthorized(sessionId, loan.getUser()))) {
            return responseBuilder.createUnauthorizedResponse();
        }

        if (!(loan.getState().equals("active") || loan.getState().equals("accepted"))) {
            return responseBuilder.createBadRequestResponse("Status der Ausleihe ist nicht \"aktiv\".");
        }

        loan.setState("conflict");
        loanRepository.save(loan);

        loan.getItem().setAvailability(false);
        itemRepository.save(loan.getItem());

        String message = "Konflikt wurde erstellt. Bitte sende eine Email mit der genauen Beschreibung Deines Problems " +
                "und dem Betreff \"" + sessionRepository.findUserBySessionCookie(sessionId).getUsername() + " - " +
                loan.getId().toString() + "\" an conflict@leihordie.de";

        return responseBuilder.createSuccessResponse(message);
    }

    @PostMapping("/conflict/solve/{loanId}/and/{userName}")
    public ResponseEntity solveConflict(Model model, @PathVariable Long loanId, @PathVariable String userName,
                                        @CookieValue(value="SessionID", defaultValue="") String sessionId) {
        if (!authorizationHandler.isAdmin(sessionId)) {
            return responseBuilder.createUnauthorizedResponse();
        }

        if (!loanRepository.findById(loanId).isPresent()) {
            return responseBuilder.createBadRequestResponse("Loan " + loanId + " exisitert nicht.");
        }

        Loan loan = loanRepository.findById(loanId).get();

        if (userRepository.findUserByName(userName).isEmpty()) {
            return responseBuilder.createBadRequestResponse("User " + userName + " existiert nicht.");
        }

        User convenantee = userRepository.findUserByName(userName).get(0);

        // when the person who gets the money is the person who wanted to loan the item
        if (convenantee.getEmail().equals(loan.getUser().getEmail())) {
            try {
                proPayWrapper.freeReservationOfUser(convenantee.getEmail(), loan.getProPayReservationId());
                Transaction transaction = new Transaction(convenantee, convenantee,
                        loan.getItem().getDeposit(), "Kaution - Konfliktstelle");
                transactionRepository.save(transaction);
            } catch (Exception e) {
                return responseBuilder.createProPayErrorResponse();
            }

            // when the person who gets the money is the person that the item belongs to him
        } else if(convenantee.getEmail().equals(loan.getItem().getUser().getEmail())) {
            try {
                proPayWrapper.punishAccount(loan.getUser().getEmail(), loan.getProPayReservationId());
                Transaction transaction = new Transaction(loan.getUser(), convenantee,
                        loan.getItem().getDeposit(), "Kaution - Konfliktstelle");
                transactionRepository.save(transaction);
            } catch (Exception e) {
                return responseBuilder.createProPayErrorResponse();
            }

        } else {
            return responseBuilder.createBadRequestResponse("User " + userName + " steht nicht im Kontext mit Loan " +
                    loan.getId() + ".");
        }

        loan.setState("completed");
        loanRepository.save(loan);

        Item item = loan.getItem();
        item.setAvailability(true);
        itemRepository.save(item);

        return responseBuilder.createSuccessResponse("Konflikt wurde gelöst.");
    }

    @PostMapping("/conflict/solveerror/loan/{loanId}")
    public ResponseEntity solveLoanError(Model model, @PathVariable Long loanId,
                                     @CookieValue(value="SessionID", defaultValue="") String sessionId) {
        if (!authorizationHandler.isAdmin(sessionId)) {
            return responseBuilder.createUnauthorizedResponse();
        }

        Loan loan = loanRepository.findById(loanId).get();

        if (!loan.getState().equals("error")) {
            return responseBuilder.createBadRequestResponse("Es liegt kein Fehler bei dieser Ausleihe vor.");
        }

        int amount = loan.getItem().getCost() * loan.getDuration();
        String lenderEmail = loan.getUser().getEmail();
        String ownerEmail = loan.getItem().getUser().getEmail();

        try {
            proPayWrapper.transferMoney(lenderEmail, ownerEmail, amount);
            Transaction transaction = new Transaction(loan.getUser(), loan.getItem().getUser(), amount,
                    "Überweisung");
            transactionRepository.save(transaction);
            proPayWrapper.freeReservationOfUser(loan.getUser().getEmail(), loan.getProPayReservationId());
            transaction = new Transaction(loan.getUser(), loan.getUser(),
                    loan.getItem().getDeposit(), "Kaution");
            transactionRepository.save(transaction);
        } catch (Exception e) {
            return responseBuilder.createProPayErrorResponse();
        }

        loan.setState("completed");
        loanRepository.save(loan);

        return responseBuilder.createSuccessResponse("Überweisung wurde getätigt.");
    }

    @PostMapping("/conflict/solveerror/buy/{buyId}")
    public ResponseEntity solveBuyError(Model model, @PathVariable Long buyId,
                                         @CookieValue(value="SessionID", defaultValue="") String sessionId) {
        if (!authorizationHandler.isAdmin(sessionId)) {
            return responseBuilder.createUnauthorizedResponse();
        }

        Buy buy= buyRepository.findById(buyId).get();

        if (!buy.getStatus().equals("error")) {
            return responseBuilder.createBadRequestResponse("Es liegt kein Fehler bei diesem Kauf vor.");
        }

        int amount = buy.getPurchasePrice();
        String buyerEmail = buy.getBuyer().getEmail();
        String sellerEmail = buy.getItem().getUser().getEmail();

        try {
            proPayWrapper.transferMoney(buyerEmail, sellerEmail, amount);
            Transaction transaction = new Transaction(buy.getBuyer(), buy.getItem().getUser(), amount, "Kauf");
            transactionRepository.save(transaction);
        } catch (Exception e) {
            return responseBuilder.createProPayErrorResponse();
        }

        buy.setStatus("completed");
        buyRepository.save(buy);

        return responseBuilder.createSuccessResponse("Überweisung wurde getätigt.");
    }

    @GetMapping("/conflict/admin")
    public String getOpenConflicts(Model model, @CookieValue(value="SessionID", defaultValue="") String sessionId) {
        if (!authorizationHandler.isAdmin(sessionId)) {
            return "redirect:/";
        }

        List<Loan> openConflicts = loanRepository.findLoansByState("conflict");
        model.addAttribute("conflicts", openConflicts);

        List<Loan> openLoanErrors = loanRepository.findLoansByState("error");
        model.addAttribute("loanErrors", openLoanErrors);

        List<Buy> openBuyErrors = buyRepository.findBuysByState("error");
        model.addAttribute("buyErrors", openBuyErrors);

        return "conflict-list";
    }
}
