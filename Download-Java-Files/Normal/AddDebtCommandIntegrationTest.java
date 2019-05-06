package seedu.address.logic.commands.debtcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDebts.getTypicalFinanceTrackerWithDebts;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.debt.Debt;
import seedu.address.testutil.DebtBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddDebtCommand}.
 */
public class AddDebtCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalFinanceTrackerWithDebts(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Debt validDebt = new DebtBuilder().build();

        Model expectedModel = new ModelManager(model.getFinanceTracker(), new UserPrefs());
        expectedModel.addDebt(validDebt);
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(new AddDebtCommand(validDebt), model, commandHistory,
                String.format(AddDebtCommand.MESSAGE_SUCCESS, validDebt), expectedModel);
    }
}
