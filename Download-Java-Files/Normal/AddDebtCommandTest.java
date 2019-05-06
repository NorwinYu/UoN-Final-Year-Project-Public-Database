package seedu.address.logic.commands.debtcommands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandtestutil.ModelStub;
import seedu.address.model.FinanceTracker;
import seedu.address.model.ReadOnlyFinanceTracker;
import seedu.address.model.debt.Debt;
import seedu.address.testutil.DebtBuilder;

public class AddDebtCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullDebt_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddDebtCommand(null);
    }

    @Test
    public void execute_debtAcceptedByModel_addSuccessful() {
        ModelStubAcceptingDebtAdded modelStub = new ModelStubAcceptingDebtAdded();
        Debt validDebt = new DebtBuilder().build();

        CommandResult commandResult = new AddDebtCommand(validDebt).execute(modelStub, commandHistory);

        assertEquals(String.format(AddDebtCommand.MESSAGE_SUCCESS, validDebt), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validDebt), modelStub.debtsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        Debt alice = new DebtBuilder().withPersonOwed("Alice").build();
        Debt bob = new DebtBuilder().withPersonOwed("Bob").build();
        AddDebtCommand addAliceCommand = new AddDebtCommand(alice);
        AddDebtCommand addBobCommand = new AddDebtCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddDebtCommand addAliceCommandCopy = new AddDebtCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different debt -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A Model stub that always accept the debt being added.
     */
    private class ModelStubAcceptingDebtAdded extends ModelStub {
        final ArrayList<Debt> debtsAdded = new ArrayList<>();

        @Override
        public boolean hasDebt(Debt debt) {
            requireNonNull(debt);
            return debtsAdded.stream().anyMatch(debt::isSameDebt);
        }

        @Override
        public void addDebt(Debt debt) {
            requireNonNull(debt);
            debtsAdded.add(debt);
        }

        @Override
        public void commitFinanceTracker() {
            // called by {@code AddDebtCommand#execute()}
        }

        @Override
        public ReadOnlyFinanceTracker getFinanceTracker() {
            return new FinanceTracker();
        }
    }

}
