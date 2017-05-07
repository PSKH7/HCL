package com.shoptracker;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create a config class to register bean to configure h2 Database console
 */
	@Configuration
	public class BeanConfig {

		@Bean
	    ServletRegistrationBean h2servletRegistration(){
	        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
	        registrationBean.addUrlMappings("/console/*");
	        return registrationBean;
	    }

	}



