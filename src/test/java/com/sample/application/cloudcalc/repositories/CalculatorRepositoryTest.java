package com.sample.application.cloudcalc.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import com.sample.application.cloudcalc.domain.Expression;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class CalculatorRepositoryTest {

	  @Autowired private DataSource dataSource;
	  @Autowired private JdbcTemplate jdbcTemplate;
	  @Autowired private EntityManager entityManager;
	  @Autowired private CalculatorRepository calculatorRepository;

	  @Test
	  public void injectedComponentsAreNotNull(){
	    assertThat(dataSource).isNotNull();
	    assertThat(jdbcTemplate).isNotNull();
	    assertThat(entityManager).isNotNull();
	    assertThat(calculatorRepository).isNotNull();
	  }
	  
	  @Test
	  public void when_saved_then_findByLabel() {
		  Expression testExpression = new Expression("2*2");
		  testExpression.setLabel("TEST_LABEL");
		  calculatorRepository.save( testExpression );
		  
		  assertThat(calculatorRepository.findExpressionByLabel("TEST_LABEL")).isNotNull();
	  }
	  
	  @Test
	  public void when_multiple_saved_then_findHistory() throws Exception {
		  Expression testExpression1 = new Expression("0+1");
		  calculatorRepository.save( testExpression1 );
		  Expression testExpression2 = new Expression("1+2");
		  Thread.sleep(1000);
		  calculatorRepository.save( testExpression2 );
		  List<Expression> list = calculatorRepository.findAllByOrderByCreatedDesc();
		  
		  assertEquals(true, list.get(0).getId()> list.get(1).getId()) ;
	  }
	  
	  @Test
	  public void when_multiple_saved_then_findLatestLabels() throws Exception {
		  Expression testExpression1 = new Expression("2+3");
		  calculatorRepository.save( testExpression1 );
		  
		  Expression testExpression2 = new Expression("4+3");
		  testExpression2 = calculatorRepository.save( testExpression2 );
		  testExpression2.setLabel("LABEL1");
		  calculatorRepository.save( testExpression2 );
		  Thread.sleep(1000);
		  Expression testExpression3 = new Expression("5+4");
		  testExpression3 = calculatorRepository.save( testExpression3 );
		  testExpression3.setLabel("LABEL2");
		  calculatorRepository.save( testExpression3 );  
		  
		  List<Expression> list = calculatorRepository.findLabelsOrderByCreatedDesc();

		  assertEquals(true, list.size()==2) ;
		  assertEquals(true, list.get(0).getId()> list.get(1).getId()) ;
	  }
	  
}