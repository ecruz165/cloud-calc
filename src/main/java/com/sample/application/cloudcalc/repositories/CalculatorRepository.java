package com.sample.application.cloudcalc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.application.cloudcalc.domain.Expression;

public interface CalculatorRepository extends JpaRepository<Expression, Long>{

	@Override
	List<Expression> findAll();
}
