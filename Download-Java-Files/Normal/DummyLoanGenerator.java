package propra2.leihOrDie;

import propra2.leihOrDie.model.Item;
import propra2.leihOrDie.model.Loan;
import propra2.leihOrDie.model.User;

public class DummyLoanGenerator {
    public Loan generateLoan(Item item, User user) throws Exception {
        int duration = 5;
        String state = "Laufend";

        Loan loan = new Loan(state, duration, user, item, 20);
        return loan;
    }
}