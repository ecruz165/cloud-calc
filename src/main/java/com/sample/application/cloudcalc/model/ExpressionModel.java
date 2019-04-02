package com.sample.application.cloudcalc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExpressionModel {
	Long id;
	boolean isLabeled = false;
	String expression; // number or arithmetic expression
	String result; // only set result when label is defined
	Boolean resultNaN = false;
	String created;
	String label;		

	public ExpressionModel(String expression) {
		this.expression = expression;
	}
	public ExpressionModel(Long id, String label) {
		this.id = id;
		this.label = label;
	}
	
}
