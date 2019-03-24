package com.sample.application.cloudcalc.config;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;


@EnableWebMvc
@Configuration
public class WebConfig implements  WebMvcConfigurer, ApplicationContextAware {

	@Autowired
	Environment environment;
	
	private final String[] jsResolvablePatterns = {"/**/*.js","/**/*.js.map"};
	private final String[] cssResolvablePatterns = {"/**/*.css","/**/*.css.map"};
	private final String[] icoResolvablePatterns = {"/**/*.ico"};
	private final String[] imgResolvablePatterns = {"/**/*.png","/**/*.jpg","/**/*.svg"};
	private final Set<String> htmlResolvablePatterns = Stream.of("*.html").collect(Collectors.toSet());
	private final String resourcePath = "classpath:/META-INF/resources/";
	private final String[] staticResourcePaths = {
			"classpath:/META-INF/resources/", 
			"classpath:/resources/",
			"classpath:/static/", 
			"classpath:/public/" 
	};
	
	private ApplicationContext applicationContext;

	@Override
	@Autowired
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Bean
	public ClassLoaderTemplateResolver pageTemplateResolver() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("/templates/");// you don't need to prefix with classpath:
		templateResolver.setSuffix(".html");
		templateResolver.setResolvablePatterns(htmlResolvablePatterns);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setOrder(0);
		templateResolver.setCheckExistence(true);
		return templateResolver;
	}

	@Bean
	public ClassLoaderTemplateResolver angularTemplateResolver() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("/META-INF/resources/");// you don't need to prefix with classpath:
		templateResolver.setSuffix(".html");
		templateResolver.setResolvablePatterns(htmlResolvablePatterns);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setOrder(1);
		templateResolver.setCheckExistence(true);
		return templateResolver;
	}

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler(jsResolvablePatterns).addResourceLocations(staticResourcePaths).setCachePeriod(5).resourceChain(true).addResolver(new PathResourceResolver());
		registry
		.addResourceHandler(cssResolvablePatterns).addResourceLocations(staticResourcePaths).setCachePeriod(5).resourceChain(true).addResolver(new PathResourceResolver());
		registry
		.addResourceHandler(icoResolvablePatterns).addResourceLocations(resourcePath).setCachePeriod(5).resourceChain(true).addResolver(new PathResourceResolver());	
		registry
		.addResourceHandler(imgResolvablePatterns).addResourceLocations(staticResourcePaths).setCachePeriod(5).resourceChain(true).addResolver(new PathResourceResolver());	
	}

}