package seedu.address.model.budget;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.DecimalFormat;

import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;

/**
 * Represents a Budget in the finance tracker.
 */
public class Budget {

    public static final String MESSAGE_CONSTRAINTS_START_DATE =
            "Start date has to be today or later.";
    public static final String MESSAGE_CONSTRAINTS_END_DATE =
            "Start date has to be before end date.";
    public static final String MESSAGE_CONSTRAINTS_END_DATE_AFTER_TODAY =
            "You are trying to set a budget in the past, you may enter 'deletebudget c/CATEGORY' to delete a budget"
                    + " if you no longer need it.";
    private int index;
    private Category category;
    private Amount amount;
    private Date startDate;
    private Date endDate;
    private String remarks;
    private int totalSpent; // in cents
    private double percentage;
    private boolean isAboutToExceed; // when percentage reaches 90

    public Budget(Category category, Amount amount, Date startDate, Date endDate, String remarks, int totalSpent,
                  double percentage) {
        requireAllNonNull(category, amount, endDate);
        this.category = category;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        if (remarks == null) {
            this.remarks = "";
        } else {
            this.remarks = remarks;
        }
        this.totalSpent = totalSpent;
        this.percentage = (((double) totalSpent) / amount.value) * 100;
        isAboutToExceed = false;
    }

    public Budget(Budget toCopy) {
        requireNonNull(toCopy);
        this.category = toCopy.category;
        this.amount = toCopy.amount;
        this.startDate = toCopy.startDate;
        this.endDate = toCopy.endDate;
        if (toCopy.remarks == null) {
            this.remarks = "";
        } else {
            this.remarks = toCopy.remarks;
        }
        totalSpent = toCopy.totalSpent;
        percentage = (((double) totalSpent) / amount.value) * 100;
        isAboutToExceed = toCopy.isAboutToExceed;
    }

    public Category getCategory() {
        return category;
    }

    public Amount getAmount() {
        return amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public int getTotalSpent() {
        return totalSpent;
    }

    public String getTotalSpentString() {
        return Integer.toString(totalSpent);
    }

    public double getPercentage() {
        DecimalFormat percentageFormat = new DecimalFormat("#.##");
        return Double.parseDouble(percentageFormat.format(percentage));
    }

    public String getPercentageString() {
        return Double.toString(percentage);
    }

    public boolean getIsAboutToExceed() {
        return isAboutToExceed;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setTotalSpent(int totalSpent) {
        this.totalSpent = totalSpent;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public void setIsAboutToExceed() {
        if (isAboutToExceed == true) {
            isAboutToExceed = false;
        } else {
            isAboutToExceed = true;
        }
    }

    public void updateTotalSpent(double difference) {
        this.totalSpent += difference;
    }

    public void updatePercentage() {
        this.percentage = (((double) totalSpent) / amount.value) * 100;
    }

    /**
     * Checks if budget overlaps in terms of date with another budget.
     * @return true if budget overlaps with {@code other}, false if otherwise
     */
    public boolean overlaps(Budget other) {
        if (this.category != other.category) {
            return false;
        }
        if (((this.startDate.compareTo(other.startDate) < 0) && (this.endDate.compareTo(other.startDate) < 0))
                || ((other.startDate.compareTo(this.startDate) < 0)
                && (other.startDate.compareTo(this.startDate) < 0))) {
            return false;
        }
        return true;
    }

    /**
     * Returns the duration of budget in String format.
     */
    public String getDuration() {
        return startDate.toString() + " till " + endDate.toString();
    }

    /**
     * Check on whether the two budgets are the same based on their variables.
     * They need not be the same instance variable.
     * @return true if the two budgets have the same attributes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Budget)) {
            return false;
        }

        Budget otherBudget = (Budget) other;
        return otherBudget != null
                && otherBudget.getCategory().equals(getCategory())
                && otherBudget.getAmount().equals(getAmount())
                && otherBudget.getStartDate().equals(getStartDate())
                && otherBudget.getEndDate().equals(getEndDate())
                && otherBudget.getRemarks().equals(getRemarks());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Category: ")
                .append(category)
                .append(" Amount: ")
                .append(amount);
        if (startDate != null) {
            builder.append(" Start date: ").append(startDate);
        }
        builder.append(" End date: ").append(endDate);
        if (remarks != null) {
            builder.append(" Remarks: ").append(remarks);
        }
        return builder.toString();
    }
}
