package seedu.address.model.attributes;

/**
 * Represents a list view in the finance tracker.
 */
public enum View {
    ALL, DAY, WEEK, MONTH, YEAR, FOOD, TRANSPORT, SHOPPING, WORK, UTILITIES, HEALTHCARE, ENTERTAINMENT, TRAVEL, $10,
    $100, $1000, OTHERS;

    public static final String MESSAGE_CONSTRAINTS =
            "View should only be one of the following:\nALL, DAY, WEEK, MONTH, FOOD, TRANSPORT, SHOPPING, WORK, "
                    + "UTILITIES, HEALTHCARE, ENTERTAINMENT, TRAVEL, OTHERS, $10, $100, $1000.";

    /**
     * Method to check if string belongs to one of the possible View enum values.
     *
     * @param viewName is a string containing possible enum value.
     * @return true if string is a valid View enum; false if string is not a valid View enum.
     */
    public static boolean isValidView(String viewName) {
        View[] views = View.values();

        for (View view : views) {
            if (view.toString().equals(viewName.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    /** Get the output message of views */
    public String getMessage() {
        switch (this) {
        case DAY:
        case WEEK:
        case MONTH:
        case YEAR:
            return "Within a " + this.toString();
        case FOOD:
        case TRANSPORT:
        case TRAVEL:
        case SHOPPING:
        case WORK:
        case UTILITIES:
        case HEALTHCARE:
        case ENTERTAINMENT:
        case OTHERS:
            return "With category under " + this.toString();
        case $10:
        case $100:
        case $1000:
            return "With amount greater than or equal to " + this.toString();
        default:
            return this.toString();
        }
    }
}
