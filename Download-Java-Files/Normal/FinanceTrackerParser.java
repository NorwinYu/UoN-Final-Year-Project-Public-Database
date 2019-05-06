package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.budgetcommands.AddBudgetCommand;
import seedu.address.logic.commands.budgetcommands.ClearBudgetCommand;
import seedu.address.logic.commands.budgetcommands.DeleteBudgetCommand;
import seedu.address.logic.commands.budgetcommands.EditBudgetCommand;
import seedu.address.logic.commands.budgetcommands.SelectBudgetCommand;
import seedu.address.logic.commands.debtcommands.AddDebtCommand;
import seedu.address.logic.commands.debtcommands.ClearDebtCommand;
import seedu.address.logic.commands.debtcommands.DeleteDebtCommand;
import seedu.address.logic.commands.debtcommands.EditDebtCommand;
import seedu.address.logic.commands.debtcommands.ListDebtCommand;
import seedu.address.logic.commands.debtcommands.PayDebtCommand;
import seedu.address.logic.commands.debtcommands.SelectDebtCommand;
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
import seedu.address.logic.commands.recurringcommands.AddRecurringCommand;
import seedu.address.logic.commands.recurringcommands.ClearRecurringCommand;
import seedu.address.logic.commands.recurringcommands.ConvertRecurringToExpenseCommand;
import seedu.address.logic.commands.recurringcommands.DeleteRecurringCommand;
import seedu.address.logic.commands.recurringcommands.EditRecurringCommand;
import seedu.address.logic.commands.recurringcommands.ListRecurringCommand;
import seedu.address.logic.commands.recurringcommands.SelectRecurringCommand;
import seedu.address.logic.commands.statscommands.StatsCommand;
import seedu.address.logic.commands.statscommands.StatsCompareCommand;
import seedu.address.logic.commands.statscommands.StatsTrendCommand;
import seedu.address.logic.parser.budgetparsers.AddBudgetCommandParser;
import seedu.address.logic.parser.budgetparsers.DeleteBudgetCommandParser;
import seedu.address.logic.parser.budgetparsers.EditBudgetCommandParser;
import seedu.address.logic.parser.budgetparsers.SelectBudgetCommandParser;
import seedu.address.logic.parser.debtparsers.AddDebtCommandParser;
import seedu.address.logic.parser.debtparsers.DeleteDebtCommandParser;
import seedu.address.logic.parser.debtparsers.EditDebtCommandParser;
import seedu.address.logic.parser.debtparsers.ListDebtCommandParser;
import seedu.address.logic.parser.debtparsers.PayDebtCommandParser;
import seedu.address.logic.parser.debtparsers.SelectDebtCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.expenseparsers.AddExpenseCommandParser;
import seedu.address.logic.parser.expenseparsers.DeleteExpenseCommandParser;
import seedu.address.logic.parser.expenseparsers.EditExpenseCommandParser;
import seedu.address.logic.parser.expenseparsers.ListExpenseCommandParser;
import seedu.address.logic.parser.expenseparsers.SelectExpenseCommandParser;
import seedu.address.logic.parser.recurringparsers.AddRecurringCommandParser;
import seedu.address.logic.parser.recurringparsers.DeleteRecurringCommandParser;
import seedu.address.logic.parser.recurringparsers.EditRecurringCommandParser;
import seedu.address.logic.parser.recurringparsers.ListRecurringCommandParser;
import seedu.address.logic.parser.recurringparsers.SelectRecurringCommandParser;
import seedu.address.logic.parser.statsparsers.StatsCommandParser;
import seedu.address.logic.parser.statsparsers.StatsCompareCommandParser;
import seedu.address.logic.parser.statsparsers.StatsTrendCommandParser;

/**
 * Parses user input.
 */
public class FinanceTrackerParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddExpenseCommand.COMMAND_WORD:
        case AddExpenseCommand.COMMAND_WORD_SHORTCUT:
            return new AddExpenseCommandParser().parse(arguments);

        case AddDebtCommand.COMMAND_WORD:
        case AddDebtCommand.COMMAND_WORD_SHORTCUT:
            return new AddDebtCommandParser().parse(arguments);

        case AddRecurringCommand.COMMAND_WORD:
        case AddRecurringCommand.COMMAND_WORD_SHORTCUT:
            return new AddRecurringCommandParser().parse(arguments);

        case ConvertRecurringToExpenseCommand.COMMAND_WORD:
        case ConvertRecurringToExpenseCommand.COMMAND_WORD_SHORTCUT:
            return new ConvertRecurringToExpenseCommand();

        case AddBudgetCommand.COMMAND_WORD:
        case AddBudgetCommand.COMMAND_WORD_SHORTCUT:
            return new AddBudgetCommandParser().parse(arguments);

        case EditExpenseCommand.COMMAND_WORD:
        case EditExpenseCommand.COMMAND_WORD_SHORTCUT:
            return new EditExpenseCommandParser().parse(arguments);

        case EditDebtCommand.COMMAND_WORD:
        case EditDebtCommand.COMMAND_WORD_SHORTCUT:
            return new EditDebtCommandParser().parse(arguments);

        case EditRecurringCommand.COMMAND_WORD:
        case EditRecurringCommand.COMMAND_WORD_SHORTCUT:
            return new EditRecurringCommandParser().parse(arguments);

        case EditBudgetCommand.COMMAND_WORD:
        case EditBudgetCommand.COMMAND_WORD_SHORTCUT:
            return new EditBudgetCommandParser().parse(arguments);

        case SelectExpenseCommand.COMMAND_WORD:
        case SelectExpenseCommand.COMMAND_WORD_SHORTCUT:
            return new SelectExpenseCommandParser().parse(arguments);

        case SelectDebtCommand.COMMAND_WORD:
        case SelectDebtCommand.COMMAND_WORD_SHORTCUT:
            return new SelectDebtCommandParser().parse(arguments);

        case SelectBudgetCommand.COMMAND_WORD:
        case SelectBudgetCommand.COMMAND_WORD_SHORTCUT:
            return new SelectBudgetCommandParser().parse(arguments);

        case SelectRecurringCommand.COMMAND_WORD:
        case SelectRecurringCommand.COMMAND_WORD_SHORTCUT:
            return new SelectRecurringCommandParser().parse(arguments);

        case DeleteExpenseCommand.COMMAND_WORD:
        case DeleteExpenseCommand.COMMAND_WORD_SHORTCUT:
            return new DeleteExpenseCommandParser().parse(arguments);

        case DeleteDebtCommand.COMMAND_WORD:
        case DeleteDebtCommand.COMMAND_WORD_SHORTCUT:
            return new DeleteDebtCommandParser().parse(arguments);

        case DeleteBudgetCommand.COMMAND_WORD:
        case DeleteBudgetCommand.COMMAND_WORD_SHORTCUT:
            return new DeleteBudgetCommandParser().parse(arguments);

        case DeleteRecurringCommand.COMMAND_WORD:
        case DeleteRecurringCommand.COMMAND_WORD_SHORTCUT:
            return new DeleteRecurringCommandParser().parse(arguments);

        case ClearExpenseCommand.COMMAND_WORD:
        case ClearExpenseCommand.COMMAND_WORD_SHORTCUT:
            return new ClearExpenseCommand();

        case ClearBudgetCommand.COMMAND_WORD:
        case ClearBudgetCommand.COMMAND_WORD_SHORTCUT:
            return new ClearBudgetCommand();

        case ClearDebtCommand.COMMAND_WORD:
        case ClearDebtCommand.COMMAND_WORD_SHORTCUT:
            return new ClearDebtCommand();

        case ClearRecurringCommand.COMMAND_WORD:
        case ClearRecurringCommand.COMMAND_WORD_SHORTCUT:
            return new ClearRecurringCommand();

        case PayDebtCommand.COMMAND_WORD:
        case PayDebtCommand.COMMAND_WORD_SHORTCUT:
            return new PayDebtCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListExpenseCommand.COMMAND_WORD:
        case ListExpenseCommand.COMMAND_WORD_SHORTCUT:
            return new ListExpenseCommandParser().parse(arguments);

        case ListDebtCommand.COMMAND_WORD:
        case ListDebtCommand.COMMAND_WORD_SHORTCUT:
            return new ListDebtCommandParser().parse(arguments);

        case ListRecurringCommand.COMMAND_WORD:
        case ListRecurringCommand.COMMAND_WORD_SHORTCUT:
            return new ListRecurringCommandParser().parse(arguments);

        case StatsCommand.COMMAND_WORD:
        case StatsCommand.COMMAND_WORD_SHORTCUT:
            return new StatsCommandParser().parse(arguments);

        case StatsCompareCommand.COMMAND_WORD:
        case StatsCompareCommand.COMMAND_WORD_SHORTCUT:
            return new StatsCompareCommandParser().parse(arguments);

        case StatsTrendCommand.COMMAND_WORD:
        case StatsTrendCommand.COMMAND_WORD_SHORTCUT:
            return new StatsTrendCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
