package seedu.address.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.attributes.Category;
import seedu.address.model.budget.Budget;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the middle index of the expense in the {@code model}'s expense list.
     */
    public static Index getMidIndex(Model model) {
        return Index.fromOneBased(model.getFilteredExpenseList().size() / 2);
    }

    /**
     * Returns the last index of the expense in the {@code model}'s expense list.
     */
    public static Index getLastIndex(Model model) {
        return Index.fromOneBased(model.getFilteredExpenseList().size());
    }

    /**
     * Returns the expense in the {@code model}'s expense list at {@code index}.
     */
    public static Expense getExpense(Model model, Index index) {
        return model.getFilteredExpenseList().get(index.getZeroBased());
    }

    /**
     * Returns the debt in the {@code model}'s debt list at {@code index}.
     */
    public static Debt getDebt(Model model, Index index) {
        return model.getFilteredDebtList().get(index.getZeroBased());
    }

    /**
     * Returns the budget in the {@code model}'s budget list with {@code category}.
     */
    public static Budget getBudget(Model model, Category category) {
        int index = -1;
        for (Budget budget : model.getFilteredBudgetList()) {
            if (budget.getCategory() == category) {
                index = model.getFilteredBudgetList().indexOf(budget);
            }
        }
        return model.getFilteredBudgetList().get(index);
    }
}
