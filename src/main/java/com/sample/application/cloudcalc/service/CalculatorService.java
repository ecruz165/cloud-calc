package com.sample.application.cloudcalc.service;

import java.util.List;

import com.sample.application.cloudcalc.domain.Expression;

public interface CalculatorService {
	
	Expression create(Expression daomin) throws Exception;
	
	Expression update(Expression domain) throws Exception;
	
	void delete(Expression eq) throws Exception;

	Expression findById(long id) throws Exception;
	
	Expression findByLabel(String name) throws Exception;
	
	List<Expression> findAllLabelsOfEntered();
	
	List<Expression> findAllHistoryOfEntered();
	
	Expression evaluate(Expression eq) throws Exception;


	
}