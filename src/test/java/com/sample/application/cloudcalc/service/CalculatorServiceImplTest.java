package com.sample.application.cloudcalc.service;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.application.cloudcalc.Application;
import com.sample.application.cloudcalc.domain.Equation;

@RunWith(SpringRunner.class)
@SpringBootTest( classes = { Application.class } )
public class CalculatorServiceImplTest {

	@Autowired
	private CalculatorService calculatorService;
	
	@Test
	public void test_add() {
		Equation eq = new Equation("5+6",5,"+",6, null);
		eq = calculatorService.add(eq.getLabel(), eq.getA(), eq.getB());
		assertTrue(eq.getResult()==11);
	}
	
	@Test
	public void test_subtract() {
		Equation eq = new Equation("5-6",5,"+",6, null);
		eq = calculatorService.subtract(eq.getLabel(), eq.getA(), eq.getB());
		assertTrue(eq.getResult()==-1);
	}
	
	@Test
	public void test_subtract_negative() {
		Equation eq = new Equation("5--6",5,"+",-6, null);
		eq = calculatorService.subtract(eq.getLabel(), eq.getA(), eq.getB());
		assertTrue(eq.getResult()==11);
	}
	
	@Test
	public void test_multiple() {
		Equation eq = new Equation("6*6",5,"*",30, null);
		eq = calculatorService.multiply(eq.getLabel(), eq.getA(), eq.getB());
		assertTrue(eq.getResult()==36);
	}
	
	@Test
	public void test_divide() {
		Equation eq = new Equation("12/6",12,"/",6, null);
		eq = calculatorService.divide(eq.getLabel(), eq.getA(), eq.getB());
		assertTrue(eq.getResult()==2);
	}

}