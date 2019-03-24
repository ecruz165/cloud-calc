package com.sample.application.cloudcalc.repositories;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sample.application.cloudcalc.Application;

@RunWith(SpringRunner.class)
@SpringBootTest( classes = { Application.class } )
public class CalculatorRepositoryTest {

	@Autowired
	private CalculatorRepository calculatorRepository;
	
	@Transactional
	@Test
	public void when_save_then_IdAndCreatedAreSetOnInsert() {
//		Expression eq = new Expression("3+5" ,3 ,"+" ,5 , new Double(8));
//		eq= calculatorRepository.save(eq);
//		assertTrue(eq.getId()!=null);
//		assertTrue(eq.getCreated()!=null);
	}
	@Transactional
	@Test
	public void when_findById_then_ObjectNotNull() {
//		Expression eq = new Expression("3+5" ,3 ,"+" ,5 , new Double(8));
//		eq= calculatorRepository.save(eq);
//		Optional<Expression> eq2 = calculatorRepository.findById(eq.getId());
//		assertNotNull(eq2.get());
	}
	
}