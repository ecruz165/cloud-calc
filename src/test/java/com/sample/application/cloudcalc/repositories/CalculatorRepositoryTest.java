package com.sample.application.cloudcalc.repositories;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.application.cloudcalc.Application;
import com.sample.application.cloudcalc.domain.Equation;

@RunWith(SpringRunner.class)
@SpringBootTest( classes = { Application.class } )
public class CalculatorRepositoryTest {

	@Autowired
	private CalculatorRepository calculatorRepository;
	
	@Transactional
	@Test
	public void when_save_then_IdAndCreatedAreSetOnInsert() {
		Equation eq = new Equation("3+5" ,3 ,"+" ,5 , new Double(8));
		eq= calculatorRepository.save(eq);
		assertTrue(eq.getId()!=null);
		assertTrue(eq.getCreated()!=null);
	}
	@Transactional
	@Test
	public void when_findById_then_ObjectNotNull() {
		Equation eq = new Equation("3+5" ,3 ,"+" ,5 , new Double(8));
		eq= calculatorRepository.save(eq);
		Optional<Equation> eq2 = calculatorRepository.findById(eq.getId());
		assertNotNull(eq2.get());
	}
	
}