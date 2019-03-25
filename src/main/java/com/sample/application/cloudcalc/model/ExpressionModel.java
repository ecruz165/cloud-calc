package com.sample.application.cloudcalc.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExpressionModel {
	Long id;
	String expression; // number or arithmetic expression
	BigDecimal result; // only set result when label is defined
	LocalDateTime created;
	String label;		

	public ExpressionModel(String expression) {
		this.expression = expression;
	}
	
}
