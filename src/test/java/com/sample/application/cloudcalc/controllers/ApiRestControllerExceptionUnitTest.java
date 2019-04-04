package com.sample.application.cloudcalc.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

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
import com.sample.application.cloudcalc.exceptions.ExpressionNotFoundException;
import com.sample.application.cloudcalc.exceptions.InvalidExpressionException;
import com.sample.application.cloudcalc.service.CalculatorService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ApiRestController.class)
class ApiRestControllerExceptionUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CalculatorService service;

	@Test
	void testExpectedException_update() throws Exception {

		Expression before = new Expression("2+3");
		before.setId(1L);
		before.setLabel("TEST");
		when(service.update(before)).thenThrow(ExpressionNotFoundException.class);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(before);
		String url = RestfulServicePaths.PUT_EXPRESSION_UPDATE.replace("{id}", before.getId()+"");
		mockMvc.perform(put(url)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(json))
		.andExpect(status()
				.isBadRequest());
		verify(this.service, times(1)).update(before);
		verifyNoMoreInteractions(this.service);

	}
	
	@Test
	void testExpectedException_evaluate() throws Exception {

		Expression before = new Expression("2%3");
		
		when(service.evaluate(before)).thenThrow(InvalidExpressionException.class);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(before);

		mockMvc.perform(post(RestfulServicePaths.POST_EXPRESSION_CREATE)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(json))
		.andExpect(status()
				.isBadRequest());
		verify(this.service, times(1)).evaluate(before);
		verifyNoMoreInteractions(this.service);

	}


}