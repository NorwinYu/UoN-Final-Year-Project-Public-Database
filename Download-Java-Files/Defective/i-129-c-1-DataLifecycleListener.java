package org.seedstack.samples.store.infrastructure;

import org.seedstack.business.data.DataManager;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.LifecycleListener;
import org.seedstack.seed.transaction.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class DataLifyCycle Listener.
 */
public class DataLifecycleListener implements LifecycleListener {
    @Inject
    private DataManager dataManager;

    /**
     * Method.
     */
    @Override
    @Transactional
    @JpaUnit("store")
    public void started() {
        try (InputStream is = DataLifecycleListener.class.getResourceAsStream("/data.json")) {
            dataManager.importData(is);
        } catch (IOException e) {
            throw new RuntimeException("Unable to import data", e);
        }
    }

    /**
     * Method.
     */
    @Override
    public void stopping() {
        // Empty.
    }
}
