package seedu.address.model.expense;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Expense}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicateForExpense implements Predicate<Expense> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicateForExpense(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Expense expense) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(expense.getName().name, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicateForExpense // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicateForExpense) other).keywords)); // state check
    }

}
