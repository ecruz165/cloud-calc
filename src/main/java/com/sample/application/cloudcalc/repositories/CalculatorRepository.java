package com.sample.application.cloudcalc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.application.cloudcalc.domain.Equation;

public interface CalculatorRepository extends JpaRepository<Equation, Long>{

	@Override
	List<Equation> findAll();
}
