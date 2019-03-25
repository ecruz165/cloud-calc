package com.sample.application.cloudcalc.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor 
public class Expression extends AbstractDomainClass {
	
	String label;
	
	String expression; // number or arithmetic expression
	
	BigDecimal result; // only set result when label is defined
	
	public Expression(String expression) {
		this.expression = expression;
	}
	public Expression(String expression, BigDecimal result) {
		this.expression = expression;
		this.result = result;
	}
}