package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalExpenses.DUCK_RICE;
import static seedu.address.testutil.TypicalExpenses.JAPAN;
import static seedu.address.testutil.TypicalExpenses.STOCKS;
import static seedu.address.testutil.TypicalExpenses.getTypicalFinanceTrackerWithExpenses;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.FinanceTracker;
import seedu.address.model.ReadOnlyFinanceTracker;

public class JsonFinanceTrackerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get(
            "src", "test", "data", "JsonFinanceTrackerStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readFinanceTracker_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readFinanceTracker(null);
    }

    private java.util.Optional<ReadOnlyFinanceTracker> readFinanceTracker(String filePath) throws Exception {
        return new JsonFinanceTrackerStorage(Paths.get(filePath)).readFinanceTracker(
                addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readFinanceTracker("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readFinanceTracker("notJsonFormatFinanceTracker.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readFinanceTracker_invalidExpenseFinanceTracker_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readFinanceTracker("invalidFinanceTracker.json");
    }

    @Test
    public void readFinanceTracker_invalidAndValidExpenseFinanceTracker_throwDataConversionException()
            throws Exception {
        thrown.expect(DataConversionException.class);
        readFinanceTracker("invalidAndValidFinanceTracker.json");
    }

    @Test
    public void readAndSaveFinanceTracker_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempFinanceTracker.json");
        FinanceTracker original = getTypicalFinanceTrackerWithExpenses();
        JsonFinanceTrackerStorage jsonFinanceTrackerStorage = new JsonFinanceTrackerStorage(filePath);

        // Save in new file and read back
        jsonFinanceTrackerStorage.saveFinanceTracker(original, filePath);
        ReadOnlyFinanceTracker readBack = jsonFinanceTrackerStorage.readFinanceTracker(filePath).get();
        assertEquals(original, new FinanceTracker(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addExpense(JAPAN);
        original.removeExpense(DUCK_RICE);
        jsonFinanceTrackerStorage.saveFinanceTracker(original, filePath);
        readBack = jsonFinanceTrackerStorage.readFinanceTracker(filePath).get();
        assertEquals(original, new FinanceTracker(readBack));

        // Save and read without specifying file path
        original.addExpense(STOCKS);
        jsonFinanceTrackerStorage.saveFinanceTracker(original); // file path not specified
        readBack = jsonFinanceTrackerStorage.readFinanceTracker().get(); // file path not specified
        assertEquals(original, new FinanceTracker(readBack));

    }

    @Test
    public void saveFinanceTracker_nullFinanceTracker_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveFinanceTracker(null, "SomeFile.json");
    }

    /**
     * Saves {@code financeTracker} at the specified {@code filePath}.
     */
    private void saveFinanceTracker(ReadOnlyFinanceTracker financeTracker, String filePath) {
        try {
            new JsonFinanceTrackerStorage(Paths.get(filePath))
                    .saveFinanceTracker(financeTracker, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveFinanceTracker_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveFinanceTracker(new FinanceTracker(), null);
    }
}
