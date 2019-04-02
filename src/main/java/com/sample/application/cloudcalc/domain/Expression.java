package com.sample.application.cloudcalc.domain;

import javax.persistence.Entity;

import com.sample.application.cloudcalc.service.ExpressionParser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor 
public class Expression extends AbstractDomainClass {
	
	String label;
	
	String expression; // number or arithmetic expression
	
	String result; // only set result when label is defined
	
	public Expression(String expression) {
		this.expression = expression;
	}
	public Expression(String expression, String result) {
		this.expression = expression;
		this.result = result;
	}
	public static Expression valueOf(String expression) throws Exception {
		String result = ExpressionParser.evaluate(expression);
		return new Expression(expression,result);
	}
}