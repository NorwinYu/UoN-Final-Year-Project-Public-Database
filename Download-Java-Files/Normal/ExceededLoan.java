package propra2.leihOrDie.model;

import lombok.Data;

import javax.persistence.Entity;

@Data
public class ExceededLoan {
    private Loan loan;
    private int numberOfExceededDays;

    public ExceededLoan() {}

    public ExceededLoan(Loan loan, int numberOfExceededDays) {
        this.loan = loan;
        this.numberOfExceededDays = numberOfExceededDays;
    }
}
