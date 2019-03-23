package com.sample.application.cloudcalc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application extends org.springframework.boot.web.servlet.support.SpringBootServletInitializer {

	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	    return configureApplication(builder); 
	}

	 public static void main(String[] args) {
	    configureApplication(new SpringApplicationBuilder()).run(args);
	 }

	 private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
	    return builder.sources(Application.class);
	}
	
}
