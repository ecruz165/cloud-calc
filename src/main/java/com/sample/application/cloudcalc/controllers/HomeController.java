package com.sample.application.cloudcalc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sample.application.cloudcalc.service.CalculatorService;

@Controller
public class HomeController {

	private static final String[] VIEW_NAME = null;
	private CalculatorService calculatorService;
	
	@Autowired
	public HomeController(  CalculatorService calculatorService) {
		this.calculatorService = calculatorService;
	}
	
	@RequestMapping("/")
	public ModelAndView show() {
		
        return new ModelAndView("home");
	}
	
}
