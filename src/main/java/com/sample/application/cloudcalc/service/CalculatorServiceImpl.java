package com.sample.application.cloudcalc.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.application.cloudcalc.domain.Equation;
import com.sample.application.cloudcalc.repositories.CalculatorRepository;


@Service("calculatorService")
public class CalculatorServiceImpl implements CalculatorService {

	private CalculatorRepository calculatorRepositor;

	@Autowired
	public CalculatorServiceImpl(CalculatorRepository calculatorRepository) {
		this.calculatorRepositor  = calculatorRepository;
	}
	
	@Override
	public Equation add(String label, double a, double b) {
		Equation eq = new Equation(label, a, "+", b, Double.sum(a, b));
		calculatorRepositor.save(eq);
		return eq;
	}

	@Override
	public Equation subtract(String label, double a, double b) {
		Equation eq = new Equation(label, a, "+", b, a-b);
		eq = calculatorRepositor.save(eq);
		return eq;
	}

	@Override
	public Equation multiply(String label, double a, double b) {
		BigDecimal bdA = new BigDecimal(a);
		BigDecimal bdB = new BigDecimal(b);
		BigDecimal result = bdA.multiply(bdB);
		Equation eq = new Equation(label, a,"*",b, result.doubleValue());
		eq = calculatorRepositor.save(eq);
		return eq;
	}

	@Override
	public Equation divide(String label, double a, double b) {
		BigDecimal bdA = new BigDecimal(a);
		BigDecimal bdB = new BigDecimal(b);
		BigDecimal result = bdA.divide(bdB);
		Equation eq = new Equation(label, a,"*",b, result.doubleValue());
		eq = calculatorRepositor.save(eq);
		return eq;
	}

	@Override
	public void removeEquation(Equation eq) {
		calculatorRepositor.delete(eq);
	}

	@Override
	public List<Equation> findAll() {
		return calculatorRepositor.findAll();
	}

}