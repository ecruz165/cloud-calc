package com.sample.application.cloudcalc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sample.application.cloudcalc.model.ExpressionModel;
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
	
	
	@RequestMapping( path ="/calculate", method=RequestMethod.POST )
	public ModelAndView showParticipantForm(
			ModelMap modelMap, 
			final ExpressionModel model, 
			final BindingResult result,
			RedirectAttributes redirectAttributes) {
		return null;
	}
	
}
