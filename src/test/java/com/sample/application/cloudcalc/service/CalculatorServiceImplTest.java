package com.sample.application.cloudcalc.service;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.application.cloudcalc.Application;
import com.sample.application.cloudcalc.domain.Expression;

@RunWith(SpringRunner.class)
@SpringBootTest( classes = { Application.class } )
public class CalculatorServiceImplTest {

	@Autowired
	private CalculatorService calculatorService;
	
	@Test
	public void test_add() throws Exception {
		Expression eq = new Expression("5+6");
		eq = calculatorService.evaluate(eq);
		assertEquals( new BigDecimal(11), eq.getResult() );	
	}
	
	@Test
	public void test_add_add() throws Exception {
		Expression eq = new Expression("5+6+8");
		eq = calculatorService.evaluate(eq);
		assertEquals( new BigDecimal(19), eq.getResult() );	
	}
	
	@Test
	public void test_add_subtract() throws Exception {
		Expression eq = new Expression("5+6-8");
		eq = calculatorService.evaluate(eq);
		assertEquals( new BigDecimal(3), eq.getResult() );	
	}
	
	@Test
	public void test_subtract_add()throws Exception {
		Expression eq = new Expression("5-6+8");
		eq = calculatorService.evaluate(eq);
		assertEquals( new BigDecimal(7), eq.getResult() );	
	}
	
	@Test
	public void test_subtract_subtract()throws Exception {
		Expression eq = new Expression("8-6-2");
		eq = calculatorService.evaluate(eq);
		assertEquals( new BigDecimal(0), eq.getResult() );	
	}	
	
	@Test
	public void test_neg_subtract_subtract() throws Exception {
		Expression eq = new Expression("-8-6-2");
		eq = calculatorService.evaluate(eq);
		assertEquals( new BigDecimal(-16), eq.getResult() );	
	}
	
	@Test
	public void test_multiply_add()throws Exception {
		Expression eq = new Expression("8*6+2");
		eq = calculatorService.evaluate(eq);
		assertEquals( new BigDecimal(50), eq.getResult() );	
	}
	
	@Test
	public void test_complex_scenario()throws Exception {
		Expression eq = new Expression("-8*6+2*40");
		eq = calculatorService.evaluate(eq);
		assertEquals( new BigDecimal(32), eq.getResult() );	
	}

	@Test
	public void test_complex_scenario1()throws Exception {
		Expression eq = new Expression("8*6");
		eq = calculatorService.evaluate(eq);
		assertEquals( new BigDecimal(48), eq.getResult() );	
	}
	
	@Test
	public void test_complex_scenario2()throws Exception {
		Expression eq = new Expression("-8*-6");
		eq = calculatorService.evaluate(eq);
		assertEquals( new BigDecimal(48), eq.getResult() );	
	}

	@Test
	public void test_complex_scenario3()throws Exception {
		Expression eq = new Expression("-8/-4");
		eq = calculatorService.evaluate(eq);
		assertTrue(eq.getResult().equals(new BigDecimal(2)));
		assertEquals( new BigDecimal(2), eq.getResult() );			
	}

	@Test
	public void test_complex_scenario6()throws Exception {
		Expression eq = new Expression("6/9999");
		eq = calculatorService.evaluate(eq);
		assertTrue(eq.getResult().equals(new BigDecimal(2)));
		assertEquals( new BigDecimal(2), eq.getResult() );			
	}
	
	
}