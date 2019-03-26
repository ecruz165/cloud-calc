package com.sample.application.cloudcalc.service;

import java.util.List;

import com.sample.application.cloudcalc.domain.Expression;

public interface CalculatorService {
	
	List<Expression> findAllLabels();
	
	List<Expression> findAll();
	
	void removeExpression(Expression eq) throws Exception;

	Expression evaluate(Expression eq) throws Exception;

	Expression update(Expression domain) throws Exception;
	
}
