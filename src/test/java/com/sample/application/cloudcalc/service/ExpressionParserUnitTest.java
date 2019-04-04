package com.sample.application.cloudcalc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sample.application.cloudcalc.domain.Expression;

@ExtendWith(SpringExtension.class)
class ExpressionParserUnitTest {

	@Test
	void test() throws Exception{
		assertEquals(ExpressionParser.evaluate("(65)"),"65");
		assertEquals(ExpressionParser.evaluate("{65}"),"65");
		assertEquals(ExpressionParser.evaluate("[65]"),"65");

		assertEquals(new BigDecimal(81).toString(), Expression.valueOf("(5+5)*8+(5/5)").getResult());
		assertEquals(new BigDecimal(90).toString(), Expression.valueOf("(5+5)*10-(50/5)").getResult());
	}

}