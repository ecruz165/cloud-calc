package com.sample.application.cloudcalc.service;

import static com.sample.application.cloudcalc.model.Operators.DIVIDE;
import static com.sample.application.cloudcalc.model.Operators.MINUS;
import static com.sample.application.cloudcalc.model.Operators.MULTIPLY;
import static com.sample.application.cloudcalc.model.Operators.PLUS;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpressionParser {

	private static final Logger log = LoggerFactory.getLogger(ExpressionParser.class);

	static Stack<String> stack = new Stack<String>();

	
	public static String evaluate(String expression) throws Exception {
		
		String currentExpression = "";
		for (int i = 0; i < expression.length(); i++) {

			String current = expression.substring(i, i + 1);
			if (isOpen(current) && currentExpression.length() > 0) {
				currentExpression = currentExpression + "####";
				stack.push(currentExpression);
				currentExpression = current;
			} else if (isClose(current)) {
				if (matches(currentExpression.substring(0, 1), current)) {
					currentExpression = currentExpression + current;
					currentExpression = removeOutterParathesis(currentExpression);
					currentExpression = solve(currentExpression);
					if (stack.size() > 0) {
						currentExpression = stack.pop().replaceAll("####", currentExpression);
					}
				} else {
					throw new Exception("invalid Expression");
				}
			} else {
				currentExpression = currentExpression + current;
			}
		}
		currentExpression = solve(currentExpression);
		return currentExpression;
	}

	static String solve(String expression) throws Exception {
		log.info("// EXPRESSION IS READY TO BE SOLVED ");

		expression = solveSignOperators(expression);

		String[] operands = extractOperands(expression);

		if (operands.length == 1) {
			return operands[0];
		} else if (expression.contains(DIVIDE)) {
			String[] operand = findPartialExpression(DIVIDE, expression, operands);
			String val = operand[0];
			String newVal = operand[1];
			String resolution = expression.replace(val, newVal);
			return solve(resolution);
		} else if (expression.contains(MULTIPLY)) {
			String[] operand = findPartialExpression(MULTIPLY, expression, operands);
			String val = operand[0];
			String newVal = operand[1];
			String resolution = expression.replace(val, newVal);
			return solve(resolution);
		} else {
			String[] operand = findPartialExpression(null, expression, operands);
			String val = operand[0];
			String newVal = operand[1];
			String resolution = expression.replace(val, newVal);
			return solve(resolution);
		}
	}

	static String[] findPartialExpression(String operator, String expression, String[] operands) throws Exception {
		String[] result = new String[2];
		int index = 0;
		for (int j = 0; j < operands.length - 1; j++) {
			index = index + (operands[j].length());
			String nextOperator = expression.substring(index, index + 1);
			operator = operator == null ? nextOperator : operator;
			String operatorFoundInExpression = expression.substring(index, index + 1);
			if (operator.equals(operatorFoundInExpression)) {
				String found = expression.substring(index, index + 1);
				String returnExpression = operands[j] + found + operands[j + 1];
				String returnResult = solve(operands[j], operator, operands[j + 1]);
				result[0] = returnExpression;
				result[1] = returnResult;
				break;
			}
			;
			index++;
		}
		return result;
	}

	/**
	 * Returns a string representing the value of expression provided between two
	 * operands.
	 *
	 * @param a: an operand that is decimal number
	 * @param operator: the operation that which executed for evaluating expression
	 * @param b: an operand that is decimal number
	 * @return string that represents the result of expression
	 */
	static String solve(String a, String operator, String b) throws Exception {
		log.info("// EVALUATE: " + a + " " + operator + " " + b);
		BigDecimal aBD = new BigDecimal(a);
		BigDecimal bBD = new BigDecimal(b);
		if (PLUS.equals(operator)) {
			return aBD.add(bBD).toString();
		} else if (MINUS.equals(operator)) {
			return aBD.subtract(bBD).toString();
		} else if (MULTIPLY.equals(operator)) {
			return aBD.multiply(bBD).toString();
		} else if (DIVIDE.equals(operator)) {
			BigDecimal result = aBD.divide(bBD, RoundingMode.HALF_UP);
			return result.toString();
		} else
			throw new Exception("Invalid expression provided");
	}

	static String solveSignOperators(String expression) {
		log.info("// SOLVE SIGNED OPERATORS");
		expression = expression.replace("-+", MINUS);
		expression = expression.replace("+-", MINUS);
		expression = expression.replace("--", PLUS);
		log.info("expression: " + expression);
		return expression;
	}

	static String[] extractOperands(String expression) {
		String[] operands = expression.split("\\+|\\-|\\*|\\/");

		if (operands.length == 1) {
			return operands;
		}

		List<String> cleansed = new ArrayList<String>();
		for (int i = 0; i < operands.length; i++) {
			if ("".equals(operands[i])) {
				cleansed.add(MINUS + operands[++i]);
			} else {
				cleansed.add(operands[i]);
			}
		}
		return cleansed.toString().replaceAll("\\[|\\]| ", "").split(",");
	}

	// remove parenthesis wrapping expression
	// i.e. (3+5+(89))
	// i.e. 3+5+(89)
	static String removeOutterParathesis(String expression) {
		if ("(".equals(expression.substring(0, 1))
				&& ")".equals(expression.substring(expression.length() - 1, expression.length()))) {
			expression = expression.substring(1, expression.length() - 1);
		} else if ("{".equals(expression.substring(0, 1))
				&& "}".equals(expression.substring(expression.length() - 1, expression.length()))) {
			expression = expression.substring(1, expression.length() - 1);
		} else if ("[".equals(expression.substring(0, 1))
				&& "]".equals(expression.substring(expression.length() - 1, expression.length()))) {
			expression = expression.substring(1, expression.length() - 1);
		}
		return expression;
	}

	static boolean matches(String poped, String current) {
		if ("{".equals(poped) && "}".equals(current)) {
			return true;
		} else if ("(".equals(poped) && ")".equals(current)) {
			return true;
		} else if ("[".equals(poped) && "]".equals(current)) {
			return true;
		}
		return false;
	}

	static boolean isOpen(String current) {
		if ("(".equals(current) || "[".equals(current) || "{".equals(current)) {
			return true;
		}
		return false;
	}

	static boolean isClose(String current) {
		if (")".equals(current) || "]".equals(current) || "}".equals(current)) {
			return true;
		}
		return false;
	}
}
