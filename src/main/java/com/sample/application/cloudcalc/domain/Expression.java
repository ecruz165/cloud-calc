package com.sample.application.cloudcalc.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor 
public class Expression extends AbstractDomainClass {
	
	String label;
	
	String expressionString; // number or arithmetic expression
	
	@ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="next_operand_id")
	Expression nextOperand; // 	
	String nextOperation; // add, subtractBy, multiplyBy, divideBy; 
	
	BigDecimal result; // only set result when label is defined
	
	public Expression(String expression) {
		expressionString = expression;
	}
}