package seedu.address.model.debt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.DebtBuilder;

public class NameContainsKeywordsPredicateForDebtTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicateForDebt firstPredicate =
                new NameContainsKeywordsPredicateForDebt(firstPredicateKeywordList);
        NameContainsKeywordsPredicateForDebt secondPredicate =
                new NameContainsKeywordsPredicateForDebt(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicateForDebt firstPredicateCopy =
                new NameContainsKeywordsPredicateForDebt(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different expense -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicateForDebt predicate = new NameContainsKeywordsPredicateForDebt(
                Collections.singletonList("Jane"));
        assertTrue(predicate.test(new DebtBuilder().withPersonOwed("Jane Doe").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicateForDebt(Arrays.asList("Jane", "Doe"));
        assertTrue(predicate.test(new DebtBuilder().withPersonOwed("Jane Doe").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicateForDebt(Arrays.asList("Jane", "Doe"));
        assertTrue(predicate.test(new DebtBuilder().withPersonOwed("John Doe").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicateForDebt(Arrays.asList("jAnE", "DOe"));
        assertTrue(predicate.test(new DebtBuilder().withPersonOwed("Jane Doe").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicateForDebt predicate =
                new NameContainsKeywordsPredicateForDebt(Collections.emptyList());
        assertFalse(predicate.test(new DebtBuilder().withPersonOwed("Jane Doe").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicateForDebt(Arrays.asList("John"));
        assertFalse(predicate.test(new DebtBuilder().withPersonOwed("Jane Doe").build()));

        // Keywords match amount, date and category, but does not match name
        predicate = new NameContainsKeywordsPredicateForDebt(
                Arrays.asList("1.10", "10-10-2019", "food", "FOOD"));
        assertFalse(predicate.test(new DebtBuilder().withPersonOwed("Chicken Rice").withAmount("1.10")
                .withDeadline("10-03-2019").withCategory("food").build()));
    }
}
