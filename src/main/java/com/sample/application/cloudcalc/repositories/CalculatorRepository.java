package com.sample.application.cloudcalc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sample.application.cloudcalc.domain.Expression;

public interface CalculatorRepository extends JpaRepository<Expression, Long>{

	Expression findExpressionByLabel(String label);
	
	List<Expression> findAllByOrderByCreatedDesc();

	@Query("SELECT e FROM Expression e where e.label IS NOT NULL ORDER BY e.created DESC") 
	List<Expression> findLabelsOrderByCreatedDesc();

	
}
