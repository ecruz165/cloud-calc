package com.sample.application.cloudcalc.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ExpressionParserFalseBranchUnitTest {
	
	//NEGATIVE TESTS
	
	@Test
	public void test_execption_evaluate() {
	    String test = null;
	    assertThrows(Exception.class, () -> {
	    	ExpressionParser.evaluate("2%3)");
	    });
	}
	
	@Test
	public void test_execption_solve() {
	    String test = null;
	    assertThrows(Exception.class, () -> {
	    	ExpressionParser.solve("2","%","3");
	    });
	}

	@Test
	void test_solve() throws Exception{
		assertTrue("5".equals(ExpressionParser.solve("2+3")));
	}

	@Test
	void test_matches() throws Exception{
		assertTrue(! ExpressionParser.matches("{", ")"));
	}

	@Test
	void test_removeOutterParathesis() throws Exception{
		assertTrue("2+3".equals(ExpressionParser.removeOutterParathesis("2+3")));
	}

	@Test 
	void test_findPartialExpression() throws Exception{
	//findPartialExpression(String operator, String expression, String[] operands) 
		String[] arr = {"2","3"};
		String[] d= ExpressionParser.findPartialExpression( "%", "2+3", arr );
		assertTrue(d[0]==null);
		assertTrue(d[1]==null);
	}
	
}