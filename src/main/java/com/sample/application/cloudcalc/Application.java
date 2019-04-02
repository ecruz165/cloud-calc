package com.sample.application.cloudcalc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application extends org.springframework.boot.web.servlet.support.SpringBootServletInitializer {

	 public static void main(String[] args) {
	    configureApplication(new SpringApplicationBuilder()).run(args);
	 }

	 static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
	    return builder.sources(Application.class);
	}
	
}