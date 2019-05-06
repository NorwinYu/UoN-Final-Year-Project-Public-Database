package propra2.leihOrDie.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class LoanForm {
    @NotNull
    @Min(1)
    private int loanDuration;
}
