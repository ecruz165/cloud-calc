package com.sample.application.cloudcalc.service;

import java.util.List;

import com.sample.application.cloudcalc.domain.Equation;

public interface CalculatorService {
	
	Equation add(String label, double a, double b);
	
	Equation subtract(String label, double a, double b);
	
	Equation multiply(String label, double a, double b);
	
	Equation divide(String label, double a, double b);
	
	List<Equation> findAll();

	void removeEquation(Equation eq);

}
