package com.sample.application.cloudcalc.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.application.cloudcalc.domain.Expression;
import com.sample.application.cloudcalc.model.ExpressionModel;
import com.sample.application.cloudcalc.service.CalculatorService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ApiRestController.class)
class ApiRestControllerUnitTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CalculatorService service;
	
	@Test
	public void givenHistory_whenGetExpressions_thenReturnJsonArray()	 
			throws Exception {
		Expression expression = new Expression("2+3");
		expression.setId(1L);
		expression.setCreated(LocalDateTime.now());
		expression.setResult("6");
	    List<Expression> allExpressions = Arrays.asList(expression);
	    when(service.findAllHistoryOfEntered()).thenReturn(allExpressions);

	    mockMvc.perform(
	    		get(RestfulServicePaths.GET_EXPRESSIONS)
					.contentType("application/json"))
			    .andExpect(status().isOk());
	    
	    verify(service, times(1)).findAllHistoryOfEntered();
	    verifyNoMoreInteractions(service);	
	}
	

	@Test
	public void givenLabels_whenGetExpressionsLabled_thenReturnJsonArray()	 
			throws Exception {
		
		Expression expression = new Expression("2+3");   
		expression.setId(1L);
		expression.setCreated(LocalDateTime.now());
		expression.setLabel("TEST");
	    List<Expression> allExpressions = Arrays.asList(expression);
	    when(service.findAllHistoryOfEntered()).thenReturn(allExpressions);
		
		mockMvc.perform(
				get(RestfulServicePaths.GET_EXPRESSIONS_LABELS)
					.contentType("application/json"))
			    .andExpect(status().isOk());
	    verify(service, times(1)).findAllLabelsOfEntered();		
	    verifyNoMoreInteractions(this.service);	
	}
	
	@Test
	public void givenCreated_whenCreateExpressions_thenReturnJson()	
			throws Exception {
		
		Expression before = new Expression("2+3"); 
		Expression after = new Expression("2+3");
		after.setId(1L);
		after.setCreated(LocalDateTime.now());
		after.setResult("6");
	    when(service.evaluate(before)).thenReturn(after);
	    
	    ExpressionModel model = new ExpressionModel("2+3");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(model);

		mockMvc.perform(
			post(RestfulServicePaths.POST_EXPRESSION_CREATE)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(json) )
	    .andExpect(status().isOk())			   
	    .andExpect(jsonPath("$.expression").value(after.getExpression()));
		
	    verify(this.service, times(1)).evaluate(before);
	    verifyNoMoreInteractions(this.service);		
	}
	
	@Test
	public void givenUpdated_whenUpdateExpression_thenReturnJson()	 
			throws Exception { 
		
		Expression before = new Expression("2+3"); 
		before.setId(1L);
		before.setCreated(parse("2019 Apr 01 16:22 PM"));
		before.setResult("6");
		before.setLabel("TEST");
		Expression after = new Expression("2+3");
		after.setId(1L);
		after.setCreated(parse("2019 Apr 01 16:22 PM"));
		after.setResult("6");
		after.setLabel("TEST");
	    when(service.update(before)).thenReturn(after);
	    
	    ExpressionModel model = new ExpressionModel(1L,"TEST");
	    model.setCreated("2019 Apr 01 16:22 PM");
	    model.setResult("6");
	    model.setLabel("TEST");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(model);

		mockMvc.perform(
					put(RestfulServicePaths.PUT_EXPRESSION_UPDATE.replaceAll("\\/\\{id\\}", "/1"))
						.contentType("application/json")
						.content(json) )
		    .andExpect(status().isOk())
		    .andExpect(jsonPath("$.expression").value("2+3"));
	    verify(this.service, times(1)).update(before);
	    verifyNoMoreInteractions(this.service);		
	}
	
	private synchronized static LocalDateTime parse(String datetime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm a");
		LocalDateTime date = LocalDateTime.parse(datetime, formatter);
        return date;
	}
}