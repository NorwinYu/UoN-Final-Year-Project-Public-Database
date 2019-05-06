package tivoli.presentation;

import tivoli.presentation.language.EN.English;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * A form bean for the availability form.
 */
public class AvailabilityForm {

    @NotEmpty(message = "{availability.from.empty}")
    @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))", message = "Invalid date format. Use YYYY-MM-DD")
    private String fromDate;

    @NotEmpty(message = "{availability.from.empty}")
    @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))", message = "Invalid date format. Use YYYY-MM-DD")
    private String toDate;

    private String fromLabel = English.from;
    private String toLabel = English.to;

    public String getFromLabel() {
        return fromLabel;
    }

    public String getToLabel() {
        return toLabel;
    }

    public String getToDate() {
        return toDate;
    }


    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }
}
