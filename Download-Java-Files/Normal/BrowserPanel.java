package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.budget.Budget;
import seedu.address.model.debt.Debt;
import seedu.address.model.expense.Expense;
import seedu.address.model.recurring.Recurring;
import seedu.address.model.statistics.Statistics;


/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));

    public static final URL EXPENSES_PAGE_URL =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "selectedexpense.html"));
    public static final URL DEBTS_PAGE_URL =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "selecteddebt.html"));
    public static final URL BUDGETS_PAGE_URL =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "selectedbudget.html"));
    public static final URL RECURRINGS_PAGE_URL =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "selectedrecurring.html"));
    public static final URL STATISTICS_PAGE_URL =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "statistics.html"));

    // Page titles for testing purposes
    public static final String EXPENSE_PAGE_TITLE = "Selected Expense Page";
    public static final String DEBT_PAGE_TITLE = "Selected Debt Page";
    public static final String BUDGET_PAGE_TITLE = "Selected Budget Page";
    public static final String RECURRING_PAGE_TITLE = "Selected Recurring Page";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel(ObservableValue<Expense> selectedExpense, ObservableValue<Debt> selectedDebt,
                        ObservableValue<Budget> selectedBudget, ObservableValue<Recurring> selectedRecurring,
                        ObservableValue<Statistics> statistics) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Load expense page when selected expense changes.
        selectedExpense.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadObjectPage(newValue);
        });

        // Load debt page when selected debt changes.
        selectedDebt.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadObjectPage(newValue);
        });

        // Load budget page when selected budget changes.
        selectedBudget.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadObjectPage(newValue);
        });

        // Load recurring page when selected recurring changes.
        selectedRecurring.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadObjectPage(newValue);
        });

        //Load statistics page when selected statistics changes.
        statistics.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            loadObjectPage(newValue);
        });

        loadDefaultPage();
    }

    /**
     * Runs load page with url that is set based on object
     */
    private void loadObjectPage(Object object) {
        String html = "";

        if (object instanceof Budget) {

            BufferedInputStream bis = new BufferedInputStream(convertUrlToInputStream(BUDGETS_PAGE_URL));
            html = convertInputStreamToString(bis);

            html = html.replace("$category", ((Budget) object).getCategory().toString());
            html = html.replace("$amount", ((Budget) object).getAmount().toString());
            html = html.replace("$duration", ((Budget) object).getDuration());
            DecimalFormat totalSpentFormat = new DecimalFormat("0.00");
            html = html.replace("$totalspent", ("$" + totalSpentFormat.format((double) ((Budget) object)
                    .getTotalSpent() / 100)));
            html = html.replace("$percentage", Double.toString(((Budget) object).getPercentage()));
            html = html.replace("$remarks", ((Budget) object).getRemarks());
        } else if (object instanceof Recurring) {

            BufferedInputStream bis = new BufferedInputStream(convertUrlToInputStream(RECURRINGS_PAGE_URL));

            html = convertInputStreamToString(bis);

            html = html.replace("$name", ((Recurring) object).getName().name);
            html = html.replace("$category", ((Recurring) object).getCategory().toString());
            html = html.replace("$amount", ((Recurring) object).getAmount().toString());
            html = html.replace("$date", ((Recurring) object).getDate().toString());
            html = html.replace("$frequency", ((Recurring) object).getFrequency().toString());
            html = html.replace("$occurrence", ((Recurring) object).getOccurrence().toString());
            html = html.replace("$remarks", ((Recurring) object).getRemarks());
        } else if (object instanceof Debt) {

            BufferedInputStream bis = new BufferedInputStream(convertUrlToInputStream(DEBTS_PAGE_URL));
            html = convertInputStreamToString(bis);

            html = html.replace("$personowed", ((Debt) object).getPersonOwed().name);
            html = html.replace("$category", ((Debt) object).getCategory().toString());
            html = html.replace("$amount", ((Debt) object).getAmount().toString());
            html = html.replace("$deadline", ((Debt) object).getDeadline().toString());
            html = html.replace("$remarks", ((Debt) object).getRemarks());
        } else if (object instanceof Expense) {

            BufferedInputStream bis = new BufferedInputStream(convertUrlToInputStream(EXPENSES_PAGE_URL));

            html = convertInputStreamToString(bis);

            html = html.replace("$name", ((Expense) object).getName().name);
            html = html.replace("$category", ((Expense) object).getCategory().toString());
            html = html.replace("$amount", ((Expense) object).getAmount().toString());
            html = html.replace("$date", ((Expense) object).getDate().toString());
            html = html.replace("$remarks", ((Expense) object).getRemarks());
        } else if (object instanceof Statistics) {

            BufferedInputStream bis = new BufferedInputStream(convertUrlToInputStream(STATISTICS_PAGE_URL));

            html = convertInputStreamToString(bis);

            html = html.replace("$result", ((Statistics) object).getHtml());

        }
        loadPage(html);
    }

    /**
     * Loads an HTML file in terms of string format
     * @param html represents the html content in string format
     */
    public void loadPage(String html) {
        logger.log(Level.INFO, "Loading item page...");
        Platform.runLater(() -> browser.getEngine().loadContent(html));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        logger.log(Level.INFO, "Loading default page...");
        Platform.runLater(() -> browser.getEngine().load(DEFAULT_PAGE.toExternalForm()));
    }

    /**
     * Converts the content of buffered input stream to string format
     */
    private String convertInputStreamToString(BufferedInputStream bis) {
        StringBuilder sb = new StringBuilder();
        try {
            byte[] contents = new byte[4096];
            int bytesRead;
            while ((bytesRead = bis.read(contents)) != -1) {
                sb.append(new String(contents, 0, bytesRead));
            }
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Converts the url to input stream format
     */
    private InputStream convertUrlToInputStream(URL url) {
        try {
            return url.openStream();
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }
}
