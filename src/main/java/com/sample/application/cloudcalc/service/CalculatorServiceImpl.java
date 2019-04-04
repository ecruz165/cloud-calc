package com.sample.application.cloudcalc.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.application.cloudcalc.domain.Expression;
import com.sample.application.cloudcalc.exceptions.ExpressionNotFoundException;
import com.sample.application.cloudcalc.exceptions.InvalidExpressionException;
import com.sample.application.cloudcalc.repositories.CalculatorRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("calculatorService")
public class CalculatorServiceImpl implements CalculatorService {

	private CalculatorRepository calculatorRepository;

	@Autowired
	public CalculatorServiceImpl(CalculatorRepository calculatorRepository) {
		this.calculatorRepository = calculatorRepository;
	}

	@Override
	public Expression create(Expression expression) {
		expression = calculatorRepository.save(expression);
		return expression;
	}

	@Override
	public Expression update(Expression updated) throws Exception {
		Expression existing = findById(updated.getId());
		updated = calculatorRepository.saveAndFlush(updated);
		return updated;
	}

	@Override
	public void delete(Expression eq) throws Exception {
		Expression existing = findById(eq.getId());
		calculatorRepository.delete(eq);
	}

	@Override
	public Expression findById(long id) throws Exception {
		Optional<Expression> obj = calculatorRepository.findById(id);
		if (obj.isPresent()) {
			Expression eq = obj.get();
			return eq;
		}
		throw new ExpressionNotFoundException("Expression is not found.");
	}

	@Override
	public Expression findByLabel(String label) throws Exception {
		return calculatorRepository.findExpressionByLabel(label);
	}

	@Override
	public List<Expression> findAllHistoryOfEntered() {
		return calculatorRepository.findAllByOrderByCreatedDesc();
	}

	@Override
	public List<Expression> findAllLabelsOfEntered() {
		return calculatorRepository.findLabelsOrderByCreatedDesc();
	}

	@Override
	public Expression evaluate(Expression eq) throws Exception {
		try {
			// An expression can be a constant expression or arithmetic expression
			log.info("Expression: " + eq.getExpression() + "******");
			String expression = eq.getExpression();
			String value;
			value = evaluate(eq.getExpression());
			eq.setResult(value);
			log.info("Expression: " + eq.getExpression() + "=" + eq.getResult());
			calculatorRepository.save(eq);
		} catch (Exception e) {
			throw new InvalidExpressionException("Could not process expression: " + eq.toString() );
		}
		return eq;
	}

	String evaluate(String expression) throws Exception {
		log.info("// ORIGINAL");
		log.info("expression: " + expression);
		expression = removeSpaces(expression);
		expression = convertLabelsToResults(expression);
		String answer = ExpressionParser.evaluate(expression);
		return answer;
	}

	// FIND LABEL AND CONVERT WITH SERVICE
	String removeSpaces(String expression) {
		log.info("// REMOVE SPACES");
		expression = expression.replaceAll(" ", "");
		log.info("expression: " + expression);
		return expression;
	}

	// FIND LABEL AND CONVERT WITH SERVICE
	String convertLabelsToResults(String expression) {
		log.info("// CONVERT LABELS");
		String LABEL = "[a-zA-Z_]+";
		Matcher m = Pattern.compile(LABEL).matcher(expression);
		while (m.find()) {
			String current = m.group().intern();
			Expression eq = calculatorRepository.findExpressionByLabel(current);
			expression = expression.replace(current, eq.getResult());
			log.info("expression: " + expression);
		}
		return expression;
	}

}