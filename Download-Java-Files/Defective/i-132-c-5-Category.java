package seedu.address.model.attributes;

/**
 * Represents a category in the finance tracker.
 */
public enum Category {
    FOOD, TRANSPORT, SHOPPING, WORK, UTILITIES, HEALTHCARE, ENTERTAINMENT, TRAVEL, OTHERS;

    public static final String MESSAGE_CONSTRAINTS =
            "Category should only be one of the following: FOOD, TRANSPORT, SHOPPING, WORK, UTILITIES, "
                    + "HEALTHCARE, ENTERTAINMENT, TRAVEL, OTHERS.";

    /**
     * Method to check if string belongs to one of the possible enum values.
     *
     * @param categoryName is a string containing possible enum value.
     * @return true if string is a valid enum; false if string is not a valid enum.
     */
    public static boolean isValid(String categoryName) {
        Category[] categories = Category.values();

        for (Category category : categories) {
            if (category.toString().equals(categoryName.toUpperCase())) {
                return true;
            }
        }

        return false;
    }
}
