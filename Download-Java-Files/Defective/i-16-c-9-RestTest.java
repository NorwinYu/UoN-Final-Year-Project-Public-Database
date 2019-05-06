package com.qa.ApartmentManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RestTest {

	@Before
	public void setup() {
		
	}
	
	@After
	public void teardown() {
		
	}
	
	@Test
	public void testy() {
		String hi = "hello";
		Assert.assertEquals("hello", hi);
	}
}
