package com.sample.application.cloudcalc.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sample.application.cloudcalc.domain.Expression;
import com.sample.application.cloudcalc.model.ExpressionModel;
import com.sample.application.cloudcalc.service.CalculatorService;

@RestController
public class ApiRestController {

	private CalculatorService calculatorService;
	
	@Autowired
	public ApiRestController(  CalculatorService calculatorService) {
		this.calculatorService = calculatorService;
	}
	
	@GetMapping("/api/expressions")
	List<ExpressionModel> getAll() {
		List<Expression> domainList = calculatorService.findAll();
		List<ExpressionModel> modelList = convertToModelList(domainList);
		return modelList;
	}

	@PostMapping("/expressions/solve")
	ExpressionModel newEmployee(@RequestBody ExpressionModel expressionModel) {
		Expression domain = convertToDomain(expressionModel);
		try {
			domain = calculatorService.evaluate(domain);
			expressionModel = convertToModel(domain);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return expressionModel;
	}

	private List<ExpressionModel> convertToModelList(List<Expression> domainList) {
		List<ExpressionModel> list = new ArrayList<ExpressionModel>();
		for (Expression expression: domainList) {
			list.add(convertToModel(expression));
		}
		return list;
	}
	
	private List<Expression> convertToDomainList(List<ExpressionModel> modelList) {
		List<Expression> list = new ArrayList<Expression>();
		for (ExpressionModel model: modelList) {
			list.add(convertToDomain(model));
		}
		return list;
	}
	
	private ExpressionModel convertToModel(Expression domain) {
		ExpressionModel model = new ExpressionModel();
		model.setId(domain.getId());
		model.setExpression(domain.getExpression());
		model.setResult(domain.getResult());
		model.setCreated(domain.getCreated());
		model.setLabel(domain.getLabel());
		return model;
	}

	private Expression convertToDomain(ExpressionModel model) {
		Expression domain = new Expression();
		domain.setId(model.getId());
		domain.setExpression(model.getExpression());
		domain.setResult(model.getResult());
		domain.setCreated(model.getCreated());
		domain.setLabel(model.getLabel());
		return domain;
	}
}