package com.sample.application.cloudcalc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sample.application.cloudcalc.domain.Expression;
import com.sample.application.cloudcalc.exceptions.ExpressionNotFoundException;
import com.sample.application.cloudcalc.exceptions.InvalidExpressionException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CalculatorServiceImplTest {

	@Autowired
	private CalculatorServiceImpl calculatorService;

	static Expression expression = null;
	static Expression labeledExpression = null;

	@BeforeEach
	public void loadDatabase() {
		expression = new Expression("60*24*52");
		expression.setResult("74880");
		calculatorService.create(expression);

		labeledExpression = new Expression("60*24*52");
		labeledExpression.setResult("74880");
		labeledExpression.setLabel("MINUTES_IN_YEAR");
		calculatorService.create(labeledExpression);
	}

	@AfterEach
	public void unLoadDatabase() throws Exception {
		Expression eq = calculatorService.findByLabel("MINUTES_IN_YEAR");
		calculatorService.delete(eq);
	}

	@Test
	public void injectedComponentsAreNotNul5l() {
		assertThat(calculatorService).isNotNull();
	}

	@Test
	public void test_create_expression() {
		Expression expression = new Expression("60*24*52");
		expression = calculatorService.create(expression);
		assertThat(expression.getId()).isNotNull();
	}

	@Test
	public void test_update_expression() throws Exception {
		expression.setLabel("SOME_LABEL");
		Expression updateExpression = calculatorService.update(expression);
		assertThat(updateExpression.getLabel()).isNotNull();
	}

	@Test
	public void test_delete_expression() throws Exception {
		Expression expression = new Expression("60*24*52");
		expression = calculatorService.create(expression);
		long id = expression.getId();
		calculatorService.delete(expression);
	}

	@Test
	public void test_find_by_id() throws Exception {
		assertThat(calculatorService.findById(labeledExpression.getId())).isNotNull();
	}

	@Test
	public void test_find_by_id_exception() throws Exception {
		Expression expression = new Expression();
		expression.setId(100L);
		assertThrows(ExpressionNotFoundException.class, () -> {
			calculatorService.findById(expression.getId());
		});
	}

	@Test
	public void test_find_all_history_of_entered() throws Exception {
		assertThat(calculatorService.findAllHistoryOfEntered()).isNotEmpty();
	}

	@Test
	public void findAllLabelsOfEntered() throws Exception {
		assertThat(calculatorService.findAllLabelsOfEntered()).isNotEmpty();
	}

	@Test
	public void extraxtInnerExpressionsAndSolve() throws Exception {
		String expression = "(3.15+5)*3.15";
		expression = calculatorService.evaluate(expression);
		assertThat(expression).isEqualTo("25.6725");
	}
	
	@Test
	public void test_add_exception() throws Exception {
		Expression eq = new Expression("5L6");
		assertThrows(InvalidExpressionException.class, () -> {
			calculatorService.evaluate(eq);
		});
	}
	
	@Test
	public void test_add() throws Exception {
		Expression eq = new Expression("5+6");
		eq = calculatorService.evaluate(eq);
		assertEquals(new BigDecimal(11).toString(), eq.getResult());
	}

	@Test
	public void test_add_add() throws Exception {
		Expression eq = new Expression("5+6+8");
		eq = calculatorService.evaluate(eq);
		assertEquals(new BigDecimal(19).toString(), eq.getResult());
	}

	@Test
	public void test_add_subtract() throws Exception {
		Expression eq = new Expression("5+6-8");
		eq = calculatorService.evaluate(eq);
		assertEquals(new BigDecimal(3).toString(), eq.getResult());
	}

	@Test
	public void test_subtract_add() throws Exception {
		Expression eq = new Expression("5-6+8");
		eq = calculatorService.evaluate(eq);
		assertEquals(new BigDecimal(7).toString(), eq.getResult());
	}

	@Test
	public void test_subtract_subtract() throws Exception {
		Expression eq = new Expression("8-6-2");
		eq = calculatorService.evaluate(eq);
		assertEquals(new BigDecimal(0).toString(), eq.getResult());
	}

	@Test
	public void test_neg_subtract_subtract() throws Exception {
		Expression eq = new Expression("-8-6-2");
		eq = calculatorService.evaluate(eq);
		assertEquals(new BigDecimal(-16).toString(), eq.getResult());
	}

	@Test
	public void test_multiply_add() throws Exception {
		Expression eq = new Expression("8*6+2");
		eq = calculatorService.evaluate(eq);
		assertEquals(new BigDecimal(50).toString(), eq.getResult());
	}

	@Test
	public void test_complex_scenario() throws Exception {
		Expression eq = new Expression("-8*6+2*40");
		eq = calculatorService.evaluate(eq);
		assertEquals(BigDecimal.valueOf(32).toString(), eq.getResult());
	}

	@Test
	public void test_complex_scenario1() throws Exception {
		Expression eq = new Expression("8*6");
		eq = calculatorService.evaluate(eq);
		assertEquals(new BigDecimal(48).toString(), eq.getResult());
	}

	@Test
	public void test_complex_scenario2() throws Exception {
		Expression eq = new Expression("-8*-6");
		eq = calculatorService.evaluate(eq);
		assertEquals(new BigDecimal(48).toString(), eq.getResult());
	}

	@Test
	public void test_complex_scenario3() throws Exception {
		Expression eq = new Expression("-8/-4");
		eq = calculatorService.evaluate(eq);
		assertEquals(new BigDecimal(2).toString(), eq.getResult());
	}

	@Test
	public void test_complex_nested_paranthesis() throws Exception {
		Expression eq = new Expression("(5+5)*8+(5/5)");
		eq = calculatorService.evaluate(eq);
		assertEquals(new BigDecimal(81).toString(), eq.getResult());
	}

	@Test
	public void test_complex_nested_brackets() throws Exception {
		Expression eq = new Expression("[5+5]*8+{5/5}");
		eq = calculatorService.evaluate(eq);
		assertEquals(new BigDecimal(81).toString(), eq.getResult());
	}

	@Test
	public void complex_using_label() throws Exception {
		Expression eq = new Expression("MINUTES_IN_YEAR+5");
		eq = calculatorService.evaluate(eq);
		assertEquals(new BigDecimal(74885).toString(), eq.getResult());

	}

	@Test
	public void complex_using_label_enclosed_in_param() throws Exception {
		Expression eq = new Expression("(MINUTES_IN_YEAR+5)-MINUTES_IN_YEAR");
		eq = calculatorService.evaluate(eq);
		assertEquals(new BigDecimal(5).toString(), eq.getResult());
	}

}