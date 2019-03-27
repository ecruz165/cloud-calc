package com.sample.application.cloudcalc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sample.application.cloudcalc.domain.Expression;

public interface CalculatorRepository extends JpaRepository<Expression, Long>{

	@Override
	List<Expression> findAll();
	
	List<Expression> findAllByOrderByCreatedDesc();

	@Query("SELECT e FROM Expression e where e.label IS NOT NULL") 
	List<Expression> findByLabelOrderByCreatedDesc();

	Expression findExpressionByLabel(String operand);
}
