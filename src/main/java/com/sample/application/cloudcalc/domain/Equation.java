package com.sample.application.cloudcalc.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor 
public class Equation extends AbstractDomainClass {

	@NotNull
	String label;
	@NotNull
	double a;
	@NotNull
	double b;
	@NotNull
	String operator;
	Double result;
	
	public Equation(String label, double a, String operation, double b, Double result) {
		this.label = label;
		this.a = a;
		this.operator = operation;
		this.b = b;
		this.result = result;
	}



}