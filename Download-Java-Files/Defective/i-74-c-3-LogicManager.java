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
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;
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
    private final FinanceTrackerParser FinanceTrackerParser;
    private boolean FinanceTrackerModified;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        FinanceTrackerParser = new FinanceTrackerParser();

        // Set FinanceTrackerModified to true whenever the models' Finance Tracker is modified.
        model.getFinanceTracker().addListener(observable -> FinanceTrackerModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        FinanceTrackerModified = false;

        CommandResult commandResult;
        try {
            Command command = FinanceTrackerParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        if (FinanceTrackerModified) {
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
    public void setSelectedPerson(Expense expense) {
        model.setSelectedExpense(expense);
    }
}
