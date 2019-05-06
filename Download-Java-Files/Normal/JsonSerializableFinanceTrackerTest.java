package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.FinanceTracker;
import seedu.address.testutil.TypicalFinanceTracker;

public class JsonSerializableFinanceTrackerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableFinanceTrackerTest");
    private static final Path TYPICAL_FINANCE_TRACKER_FILE = TEST_DATA_FOLDER.resolve("typicalFinanceTracker.json");
    private static final Path INVALID_FINANCE_TRACKER_FILE = TEST_DATA_FOLDER.resolve("invalidFinanceTracker.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalFinanceTrackerFile_success() throws Exception {
        JsonSerializableFinanceTracker dataFromFile = JsonUtil.readJsonFile(TYPICAL_FINANCE_TRACKER_FILE,
                JsonSerializableFinanceTracker.class).get();
        FinanceTracker financeTrackerFromFile = dataFromFile.toModelType();
        FinanceTracker typicalFinanceTracker = TypicalFinanceTracker.getTypicalFinanceTracker();
        assertEquals(financeTrackerFromFile, typicalFinanceTracker);
    }

    @Test
    public void toModelType_invalidFinanceTrackerFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFinanceTracker dataFromFile = JsonUtil.readJsonFile(INVALID_FINANCE_TRACKER_FILE,
                JsonSerializableFinanceTracker.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}
