package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.expensecommands.AddExpenseCommand;
import seedu.address.logic.commands.expensecommands.ClearExpenseCommand;
import seedu.address.logic.commands.expensecommands.DeleteExpenseCommand;
import seedu.address.logic.commands.expensecommands.EditExpenseCommand;
import seedu.address.logic.commands.expensecommands.ListExpenseCommand;
import seedu.address.logic.commands.expensecommands.SelectExpenseCommand;
import seedu.address.logic.commands.generalcommands.ExitCommand;
import seedu.address.logic.commands.generalcommands.FindCommand;
import seedu.address.logic.commands.generalcommands.HelpCommand;
import seedu.address.logic.commands.generalcommands.HistoryCommand;
import seedu.address.logic.commands.generalcommands.RedoCommand;
import seedu.address.logic.commands.generalcommands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attributes.View;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.NameContainsKeywordsPredicateForExpense;
import seedu.address.testutil.EditExpenseDescriptorBuilder;
import seedu.address.testutil.ExpenseBuilder;
import seedu.address.testutil.ExpenseUtil;

public class FinanceTrackerParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final FinanceTrackerParser parser = new FinanceTrackerParser();

    @Test
    public void parseCommand_add() throws Exception {
        Expense expense = new ExpenseBuilder().build();
        AddExpenseCommand command = (AddExpenseCommand) parser.parseCommand(ExpenseUtil.getAddCommand(expense));
        assertEquals(new AddExpenseCommand(expense), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearExpenseCommand.COMMAND_WORD) instanceof ClearExpenseCommand);
        assertTrue(parser.parseCommand(ClearExpenseCommand.COMMAND_WORD + " 3")
                instanceof ClearExpenseCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteExpenseCommand command = (DeleteExpenseCommand) parser.parseCommand(
                DeleteExpenseCommand.COMMAND_WORD + " " + INDEX_FIRST_EXPENSE.getOneBased());
        assertEquals(new DeleteExpenseCommand(INDEX_FIRST_EXPENSE), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Expense expense = new ExpenseBuilder().build();
        EditExpenseCommand.EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder(expense).build();
        EditExpenseCommand command = (EditExpenseCommand) parser.parseCommand(EditExpenseCommand.COMMAND_WORD
                + " " + INDEX_FIRST_EXPENSE.getOneBased() + " "
                + ExpenseUtil.getEditExpenseDescriptorDetails(descriptor));
        assertEquals(new EditExpenseCommand(INDEX_FIRST_EXPENSE, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicateForExpense(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD + " " + PREFIX_VIEW + View.ALL)
                instanceof ListExpenseCommand);
        assertTrue(parser.parseCommand(ListExpenseCommand.COMMAND_WORD + " " + PREFIX_VIEW + View.FOOD)
                instanceof ListExpenseCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectExpenseCommand command = (SelectExpenseCommand) parser.parseCommand(
                SelectExpenseCommand.COMMAND_WORD + " " + INDEX_FIRST_EXPENSE.getOneBased());
        assertEquals(new SelectExpenseCommand(INDEX_FIRST_EXPENSE), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
