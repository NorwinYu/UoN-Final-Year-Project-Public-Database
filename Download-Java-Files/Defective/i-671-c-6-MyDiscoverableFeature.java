package com.kamlesh.poc.jersey.di;

import java.io.IOException;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.Populator;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ClasspathDescriptorFileFinder;
import org.glassfish.hk2.utilities.DuplicatePostProcessor;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

import gov.va.oia.HK2Utilities.HK2RuntimeInitializer;
import com.kamlesh.poc.jersey.Main;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Log4j2
public class MyDiscoverableFeature implements Feature {
	private static final Logger logger = LoggerFactory.getLogger(MyDiscoverableFeature.class.getName());

	@Override
	public boolean configure(FeatureContext context) {
		ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator(Main.SERVICE_LOCATOR_NAME);
		logger.info("MyDiscoverableFeature configure {}", locator);
		DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class);
		try {
			HK2RuntimeInitializer.init(Main.SERVICE_LOCATOR_NAME, true, Main.PACKAGE_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Populator populator = dcs.getPopulator();
		try {
			populator.populate(new ClasspathDescriptorFileFinder(this.getClass().getClassLoader()), new DuplicatePostProcessor());
		} catch (IOException | MultiException ex) {
			logger.error(null, ex);
		}
		return true;
	}

}