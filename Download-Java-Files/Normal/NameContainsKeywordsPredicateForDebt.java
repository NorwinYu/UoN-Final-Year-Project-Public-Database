package seedu.address.model.debt;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Debt}'s {@code personOwed} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicateForDebt implements Predicate<Debt> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicateForDebt(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Debt debt) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(debt.getPersonOwed().name, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicateForDebt // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicateForDebt) other).keywords)); // state check
    }

}
