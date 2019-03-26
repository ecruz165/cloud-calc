package com.sample.application.cloudcalc.service;

import static com.sample.application.cloudcalc.model.Operators.DIVIDE;
import static com.sample.application.cloudcalc.model.Operators.MINUS;
import static com.sample.application.cloudcalc.model.Operators.MULTIPLY;
import static com.sample.application.cloudcalc.model.Operators.PLUS;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

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
	public List<Expression> findAllLabels() {
		return calculatorRepository.findByLabelOrderByCreatedDesc();
	}

	
	@Override
	public List<Expression> findAll() {
		return calculatorRepository.findAllByOrderByCreatedDesc();
	}

	@Override
	public Expression evaluate(Expression eq) throws Exception {
		// An expression can be a constant expression or arithmetic expression 
		log.info("Expression: " +  eq.getExpression() + "******" );
		String expression = eq.getExpression();
		expression = expression.replaceAll(" ","");
		String value = solve(eq.getExpression());
		eq.setResult(new BigDecimal(value));
		calculatorRepository.save(eq);
		return eq;
	}
	
	private String solve(String expression) throws Exception {
		log.info("Expression: " +  expression);
		expression = expression.replace("-+", MINUS);
		expression = expression.replace("+-", MINUS);
		expression = expression.replace("--", PLUS);
		expression = expression.replace(" ", "");
		
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
			String resolution = expression.replace(val, newVal);
			return solve(resolution);		
		} else if (expression.contains(MULTIPLY)) {
			String[] operand = findPartialExpression(MULTIPLY, expression, operands);
			String val = operand[0];
			String newVal =  operand[1];
			String resolution = expression.replace(val, newVal);	
			return solve(resolution);
		} else {
			String[] operand = findPartialExpression(null, expression, operands);
			String val = operand[0];
			String newVal =  operand[1];
			String resolution = expression.replace(val, newVal);	
			return solve(resolution);
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
			String operatorFoundInExpression = expression.substring(index,index+1);
			if( operator.equals(operatorFoundInExpression)) {
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
		} else 	if (DIVIDE.equals(operator)) {
			BigDecimal result = aBD.divide(bBD,32, RoundingMode.HALF_UP);
			return result.toString();
		} else 
			throw new Exception("Invalid expression provided");
	}

	@Override
	public Expression update(Expression updated) throws Exception {
		Expression domain = calculatorRepository.getOne(updated.getId());
		domain.setLabel(updated.getLabel());
		updated = calculatorRepository.save(domain);
		return updated;
	}


}