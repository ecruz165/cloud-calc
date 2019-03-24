package com.sample.application.cloudcalc.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static com.sample.application.cloudcalc.model.Operators.PLUS;
import static com.sample.application.cloudcalc.model.Operators.MINUS;
import static com.sample.application.cloudcalc.model.Operators.MULTIPLY;
import static com.sample.application.cloudcalc.model.Operators.DIVIDE;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.application.cloudcalc.domain.Expression;
import com.sample.application.cloudcalc.repositories.CalculatorRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("calculatorService")
public class CalculatorServiceImpl implements CalculatorService {

	private CalculatorRepository calculatorRepository;

	@Autowired
	public CalculatorServiceImpl(CalculatorRepository calculatorRepository) {
		this.calculatorRepository  = calculatorRepository;
	}

	@Override
	public void removeExpression(Expression eq) {
		calculatorRepository.delete(eq);
	}

	@Override
	public List<Expression> findAll() {
		return calculatorRepository.findAll();
	}

	@Override
	public Expression evaluate(Expression eq) throws Exception {
		// An expression can be a constant expression or arithmetic expression 
		log.info("Expression: " +  eq.getExpressionString() + "******" );
		String expression = eq.getExpressionString();
		expression = expression.replaceAll(" ","");
		String value = solve(eq.getExpressionString());
		eq.setResult(new BigDecimal(value));
		return eq;
	}
	
	private String solve(String expression) throws Exception {
		log.info("Expression: " +  expression);
		expression = expression.replace("-+", MINUS);
		expression = expression.replace("+-", MINUS);
		expression = expression.replace("--", PLUS);
		String[] operands = extractOperands(expression);
		
		if (operands.length==1) {
			/*TODO 
			 * if not a number 
			 * 		look up LABELS-
			 * 		if label does not exists throw LABEL NOT FOUND EXCEPTION */
			// constant expression 
			return operands[0];
		} else if (expression.contains(DIVIDE)) {
			String[] operand = findPartialExpression(DIVIDE, expression, operands);
			String val = operand[0];
			String newVal =  operand[1];
			String reolution = expression.replace(val, newVal);	
			return solve(reolution);		
		} else if (expression.contains(MULTIPLY)) {
			String[] operand = findPartialExpression(MULTIPLY, expression, operands);
			String val = operand[0];
			String newVal =  operand[1];
			String reolution = expression.replace(val, newVal);	
			return solve(reolution);
		} else {
			String[] operand = findPartialExpression(null, expression, operands);
			String val = operand[0];
			String newVal =  operand[1];
			String reolution = expression.replace(val, newVal);	
			return solve(reolution);
		} 
	}

	private String[] extractOperands(String expression) {
			String[] operands = expression.split("\\+|\\-|\\*|\\/");

			if (operands.length==1) {
				return operands ;
			}
			
			List<String> cleansed = new ArrayList<String>();
			for (int i=0; i< operands.length;i++) {
				if ("".equals(operands[i])){
					cleansed.add(MINUS+operands[++i]);
				} else {
					cleansed.add(operands[i]);
				}
			}
			return cleansed.toString().replaceAll("\\[|\\]| ","").split(",");
	}

	private String[] findPartialExpression(String operator, String expression, String[] operands) throws Exception {
		int index=0;
		for(int j=0; j< operands.length-1; j++) {
			index= index + (operands[j].length());
			String nextOperator = expression.substring(index,index+1);
			operator= operator==null? nextOperator:operator;
			if( operator.equals(expression.substring(index,index+1))) {
				String found = expression.substring(index,index+1);
				String returnExpression = operands[j]+found+operands[j+1];
				String returnResult = solve(operands[j],operator, operands[j+1]);
				String[] result = {returnExpression, returnResult};
				return result;
			};
			index++;
		}
		return null;
	}

	private String solve(String a, String operator, String b) throws Exception {
		BigDecimal aBD = new BigDecimal(a);
		BigDecimal bBD = new BigDecimal(b);
		if (PLUS.equals(operator)) {
			return aBD.add(bBD).toString();
		} else 	if (MINUS.equals(operator)) {
			return aBD.subtract(bBD).toString();
		} else 	if (MULTIPLY.equals(operator)) {
			return aBD.multiply(bBD).toString();
		}else 	if (DIVIDE.equals(operator)) {
			return aBD.divide(bBD).toString();
		} else 
			throw new Exception("Invalid expression provided");
	}

}