package com.sample.application.cloudcalc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTest {

	@Test
	  void test_applicationContextLoads() {
		Application.main(new String[] {});
	}

	
	
}
