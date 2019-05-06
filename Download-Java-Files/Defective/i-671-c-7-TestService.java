package com.kamlesh.poc.jersey.service;

import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import com.kamlesh.poc.jersey.di.MyDiscoverableFeature;
import org.jvnet.hk2.annotations.Service;

import com.kamlesh.poc.jersey.di.DependencyResolver;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Log4j2
public class TestService {
	private static final Logger logger = LoggerFactory.getLogger(MyDiscoverableFeature.class.getName());

	private static AtomicInteger count = new AtomicInteger(0);

	@Inject
	DependencyResolver resolver;

	public TestService() {
		logger.info("Constructor called count {}", count.incrementAndGet());
	}

	public boolean doTask() {
		return true;
	}

}
