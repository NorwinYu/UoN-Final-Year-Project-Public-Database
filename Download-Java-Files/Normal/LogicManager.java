package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.FinanceTrackerParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyFinanceTracker;
import seedu.address.model.budget.Budget;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;
import seedu.address.model.recurring.Recurring;
import seedu.address.model.statistics.Statistics;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final FinanceTrackerParser financeTrackerParser;
    private boolean financeTrackerModified;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        financeTrackerParser = new FinanceTrackerParser();

        // Set financeTrackerModified to true whenever the models' Finance Tracker is modified.
        model.getFinanceTracker().addListener(observable -> financeTrackerModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        financeTrackerModified = false;

        CommandResult commandResult;
        try {
            Command command = financeTrackerParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } catch (CommandException ce) {
            //commandResult = new CommandResult(ce.getMessage(), false, false);
            throw new CommandException(ce.getMessage());
        } catch (ParseException pe) {
            //commandResult = new CommandResult(pe.getMessage(), false, false);
            throw new ParseException(pe.getMessage());
        }
        history.add(commandText);


        if (financeTrackerModified) {
            logger.info("Finance Tracker modified, saving to file.");
            try {
                storage.saveFinanceTracker(model.getFinanceTracker());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override

    public ReadOnlyFinanceTracker getFinanceTracker() {
        return model.getFinanceTracker();

    }

    @Override
    public ObservableList<Expense> getFilteredExpenseList() {
        return model.getFilteredExpenseList();
    }

    @Override
    public ObservableList<Debt> getFilteredDebtList() {
        return model.getFilteredDebtList();
    }

    @Override
    public ObservableList<Budget> getFilteredBudgetList() {
        return model.getFilteredBudgetList();
    }

    @Override
    public ObservableList<Recurring> getFilteredRecurringList() {
        return model.getFilteredRecurringList();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getFinanceTrackerFilePath() {
        return model.getFinanceTrackerFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ReadOnlyProperty<Expense> selectedExpenseProperty() {
        return model.selectedExpenseProperty();
    }

    @Override
    public void setSelectedExpense(Expense expense) {
        model.setSelectedExpense(expense);
    }

    @Override
    public ReadOnlyProperty<Debt> selectedDebtProperty() {
        return model.selectedDebtProperty();
    }

    @Override
    public void setSelectedDebt(Debt debt) {
        model.setSelectedDebt(debt);
    }

    @Override
    public ReadOnlyProperty<Budget> selectedBudgetProperty() {
        return model.selectedBudgetProperty();
    }

    @Override
    public void setSelectedBudget(Budget budget) {
        model.setSelectedBudget(budget);
    }

    @Override
    public ReadOnlyProperty<Recurring> selectedRecurringProperty() {
        return model.selectedRecurringProperty();
    }

    @Override
    public void setSelectedRecurring(Recurring recurring) {
        model.setSelectedRecurring(recurring);
    }

    @Override
    public ReadOnlyProperty<Statistics> statisticsProperty() {
        return model.statisticsProperty();
    }

    @Override
    public void setStatistics(Statistics statistics) {
        model.setStatistics(statistics);
    }
}
