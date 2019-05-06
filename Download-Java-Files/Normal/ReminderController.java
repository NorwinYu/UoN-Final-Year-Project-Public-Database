package propra2.leihOrDie.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import propra2.leihOrDie.dataaccess.LoanRepository;
import propra2.leihOrDie.dataaccess.SessionRepository;
import propra2.leihOrDie.dataaccess.UserRepository;
import propra2.leihOrDie.model.ExceededLoan;
import propra2.leihOrDie.model.Loan;
import propra2.leihOrDie.model.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReminderController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    SessionRepository sessionRepository;

    @GetMapping("/reminder")
    public String getReminded(Model model, @CookieValue(value="SessionID", defaultValue="") String sessionId) {
        User user = sessionRepository.findUserBySessionCookie(sessionId);
        List<Loan> exceededLoansOfUser = getExceededLoans(user);
        List<ExceededLoan> exceededLoans = getExceededLoansList(exceededLoansOfUser);

        model.addAttribute("exceededLoans", exceededLoans);

        return "loan-reminder";
    }

    private List<Loan> getExceededLoans(User user) {
        List<Loan> loans = loanRepository.findLoansOfUser(user.getUsername());
        List<Loan> returnLoans = new ArrayList<>();

        for (Loan loan: loans) {
            if (loan.isExceeded()) {
                returnLoans.add(loan);
            }
        }

        return returnLoans;
    }

    private List<ExceededLoan> getExceededLoansList(List<Loan> exceededLoansOfUser) {
        List<ExceededLoan> exceededLoans = new ArrayList<>();

        for (Loan loan: exceededLoansOfUser) {
            exceededLoans.add(new ExceededLoan(loan, numberOfExceededDays(loan)));
        }

        return exceededLoans;
    }

    private int numberOfExceededDays(Loan loan) {
        LocalDate startDate = loan.getDayOfRental();
        LocalDate now = LocalDate.now();
        Period differenceBetweenDays = Period.between(now, startDate);

        return differenceBetweenDays.getDays();
    }
}
