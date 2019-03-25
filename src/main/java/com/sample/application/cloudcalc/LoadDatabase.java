package com.sample.application.cloudcalc;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sample.application.cloudcalc.domain.Expression;
import com.sample.application.cloudcalc.repositories.CalculatorRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LoadDatabase {
	@Bean
	CommandLineRunner initDatabase(CalculatorRepository repository) {
		return args -> {
			log.info("Preloading " + repository.save(new Expression("2+3", new BigDecimal("6"))));
			log.info("Preloading " + repository.save(new Expression("2+3", new BigDecimal("6"))));			
			log.info("Preloading " + repository.save(new Expression("3.145725", new BigDecimal("3.145725"))));
		};
	}
}
