package com.sample.application.cloudcalc.model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExpressionModel {
	Long id;
	boolean isLabeled = false;
	String expression; // number or arithmetic expression
	BigDecimal result; // only set result when label is defined
	String created;
	String label;		

	public ExpressionModel(String expression) {
		this.expression = expression;
	}
	
}
