package seedu.address.model.recurring;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Recurrings}'s {@code Name} matches any of the keywords given.
 */
public class RecurringNameContainsKeywordsPredicate implements Predicate<Recurring> {
    private final List<String> keywords;

    public RecurringNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Recurring recurring) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(recurring.getName().name, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecurringNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((RecurringNameContainsKeywordsPredicate) other).keywords)); // state check
    }
}
