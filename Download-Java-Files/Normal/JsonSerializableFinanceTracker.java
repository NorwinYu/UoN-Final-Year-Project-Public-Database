package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.FinanceTracker;
import seedu.address.model.ReadOnlyFinanceTracker;
import seedu.address.model.budget.Budget;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;
import seedu.address.model.recurring.Recurring;

/**
 * An Immutable FinanceTracker that is serializable to JSON format.
 */
@JsonRootName(value = "financetracker")
class JsonSerializableFinanceTracker {

    private final List<JsonAdaptedExpense> expenses = new ArrayList<>();
    private final List<JsonAdaptedDebt> debts = new ArrayList<>();
    private final List<JsonAdaptedBudget> budgets = new ArrayList<>();
    private final List<JsonAdaptedRecurring> recurrings = new ArrayList<>();


    /**
     * Constructs a {@code JsonSerializableFinanceTracker} with the given expenses.
     */
    @JsonCreator
    public JsonSerializableFinanceTracker(@JsonProperty("expenses") List<JsonAdaptedExpense> expenses,
                                          @JsonProperty("debts") List<JsonAdaptedDebt> debts,
                                          @JsonProperty("budgets") List<JsonAdaptedBudget> budgets,
                                          @JsonProperty("recurrings") List<JsonAdaptedRecurring> recurrings) {
        this.expenses.addAll(expenses);
        this.debts.addAll(debts);
        this.budgets.addAll(budgets);
        this.recurrings.addAll(recurrings);
    }

    /**
     * Converts a given {@code ReadOnlyFinanceTracker} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFinanceTracker}.
     */
    public JsonSerializableFinanceTracker(ReadOnlyFinanceTracker source) {
        expenses.addAll(source.getExpenseList().stream().map(JsonAdaptedExpense::new).collect(Collectors.toList()));
        debts.addAll(source.getDebtList().stream().map(JsonAdaptedDebt::new).collect(Collectors.toList()));
        budgets.addAll(source.getBudgetList().stream().map(JsonAdaptedBudget::new).collect(Collectors.toList()));
        recurrings.addAll(source.getRecurringList().stream().map(
                JsonAdaptedRecurring::new).collect(Collectors.toList()));
    }

    /**
     * Converts this finance tracker into the model's {@code FinanceTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FinanceTracker toModelType() throws IllegalValueException {
        FinanceTracker financeTracker = new FinanceTracker();
        for (JsonAdaptedExpense jsonAdaptedExpense : expenses) {
            Expense expense = jsonAdaptedExpense.toModelType();
            financeTracker.addExpense(expense);
        }
        for (JsonAdaptedDebt jsonAdaptedDebt : debts) {
            Debt debt = jsonAdaptedDebt.toModelType();
            financeTracker.addDebt(debt);
        }
        for (JsonAdaptedBudget jsonAdaptedBudget : budgets) {
            Budget budget = jsonAdaptedBudget.toModelType();
            financeTracker.addBudget(budget);
        }
        for (JsonAdaptedRecurring jsonAdaptedRecurring : recurrings) {
            Recurring recurring = jsonAdaptedRecurring.toModelType();
            financeTracker.addRecurring(recurring);
        }
        return financeTracker;
    }

}
