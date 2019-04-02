package com.sample.application.cloudcalc.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sample.application.cloudcalc.domain.Expression;
import com.sample.application.cloudcalc.model.ExpressionModel;
import com.sample.application.cloudcalc.service.CalculatorService;

@RestController
public class ApiRestController {

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm a");

	private CalculatorService calculatorService;
	
	@Autowired
	public ApiRestController(  CalculatorService calculatorService) {
		this.calculatorService = calculatorService;
	}
		
	@GetMapping(RestfulServicePaths.GET_EXPRESSIONS)
	List<ExpressionModel> findAllExpressionHistory() {
		List<Expression> domainList = calculatorService.findAllHistoryOfEntered();
		List<ExpressionModel> modelList = convertToModelList(domainList);
		return modelList;
	}
	
	@GetMapping(RestfulServicePaths.GET_EXPRESSIONS_LABELS)
	List<ExpressionModel> findAllExpressionsLabled() {
		List<Expression> domainList = calculatorService.findAllLabelsOfEntered();
		List<ExpressionModel> modelList = convertToModelList(domainList);
		return modelList;
	}
	
	@PostMapping(RestfulServicePaths.POST_EXPRESSION_CREATE)
	ExpressionModel newEmployee(@RequestBody ExpressionModel expressionModel) {
		Expression domain = convertToDomain(expressionModel);
		try {
			domain = calculatorService.evaluate(domain);
			expressionModel = convertToModel(domain);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return expressionModel;
	}
	
	@PutMapping(RestfulServicePaths.PUT_EXPRESSION_UPDATE)
	ExpressionModel saveExpression(@RequestBody ExpressionModel model) {
		Expression domain = convertToDomain(model);
		try {
			domain = calculatorService.update(domain);
			model = convertToModel(domain);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	private List<ExpressionModel> convertToModelList(List<Expression> domainList) {
		List<ExpressionModel> list = new ArrayList<ExpressionModel>();
		for (Expression expression: domainList) {
			list.add(convertToModel(expression));
		}
		return list;
	}

	
	private ExpressionModel convertToModel(Expression domain) {
		ExpressionModel model = new ExpressionModel();
		model.setId(domain.getId());
		model.setExpression(domain.getExpression());
		model.setResult(domain.getResult());
		model.setCreated(format(domain.getCreated()));
		model.setLabeled( domain.getLabel()!=null ? true:false );
		model.setLabel(domain.getLabel());
		
		return model;
	}

	private Expression convertToDomain(ExpressionModel model) {
		Expression domain = new Expression();
		domain.setId(model.getId());
		domain.setExpression(model.getExpression());
		domain.setResult(model.getResult());
		
		domain.setCreated(parse(model.getCreated()));
		domain.setLabel(model.getLabel());
		return domain;
	}
	
	private synchronized static String format(LocalDateTime datetime) {
            String date = formatter.format(datetime);
            return date;
    }
	
	private synchronized static LocalDateTime parse(String datetime) {
		if (datetime ==null)
			return null;
		LocalDateTime date = LocalDateTime.parse(datetime, formatter);
        return date;
	}

}