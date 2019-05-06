package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.table.ReadOnlyTables;
import seedu.address.model.statistics.ReadOnlyStatistics;
import seedu.address.model.order.ReadOnlyOrders;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.RestOrRant;
import seedu.address.model.UserPrefs;
import seedu.address.model.menu.ReadOnlyMenu;
import seedu.address.storage.JsonMenuStorage;
import seedu.address.storage.JsonOrdersStorage;
import seedu.address.storage.JsonStatisticsStorage;
import seedu.address.storage.JsonTablesStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.MenuStorage;
import seedu.address.storage.OrdersStorage;
import seedu.address.storage.StatisticsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.TablesStorage;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 1, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing RestOrRant ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        OrdersStorage ordersStorage = new JsonOrdersStorage(userPrefs.getOrdersFilePath());
        MenuStorage menuStorage = new JsonMenuStorage(userPrefs.getMenuFilePath());
        StatisticsStorage statisticsStorage = new JsonStatisticsStorage(userPrefs.getStatisticsFilePath());
        TablesStorage tablesStorage = new JsonTablesStorage(userPrefs.getTablesFilePath());
        storage = new StorageManager(userPrefsStorage, ordersStorage, menuStorage, statisticsStorage, tablesStorage);
        

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s RestOrRant and {@code userPrefs}. <br>
     * Sample data will be used instead if any {@code storage} data file is not found,
     * or an empty RestOrRant will be used instead if errors occur when reading from any {@code storage} data file.
     * TODO: Write the sample RestOrRant files.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyOrders> ordersOptional;
        Optional<ReadOnlyMenu> menuOptional;
        Optional<ReadOnlyTables> tablesOptional; 
        Optional<ReadOnlyStatistics> statisticsOptional;
        RestOrRant initialData;
        try {
            // addressBookOptional = storage.readRestOrRant();
            ordersOptional = storage.readOrders();
            menuOptional = storage.readMenu();
            tablesOptional = storage.readTables();
            statisticsOptional = storage.readStatistics();
            if (!ordersOptional.isPresent()) {
                logger.info("Orders data file not found. Will be starting with an empty RestOrRant");
                initialData = new RestOrRant();
            } else if (!menuOptional.isPresent()) {
                logger.info("Menu data file not found. Will be starting with an empty RestOrRant");
                initialData = new RestOrRant();
            } else if (!tablesOptional.isPresent()) {
                logger.info("Tables data file not found. Will be starting with an empty RestOrRant");
                initialData = new RestOrRant();
            } else if (!statisticsOptional.isPresent()) {
                logger.info("Statistics data file not found. Will be starting with an empty RestOrRant");
                initialData = new RestOrRant();
            } else {
                initialData = new RestOrRant(ordersOptional.get(), menuOptional.get(), 
                        tablesOptional.get(), statisticsOptional.get());
            }

            // initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty RestOrRant");
            initialData = new RestOrRant();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty RestOrRant");
            initialData = new RestOrRant();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty RestOrRant");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting RestOrRant " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping RestOrRant ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
