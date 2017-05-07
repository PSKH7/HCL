package com.shoptracker;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
/**
 * This is  main class or root of Spring boot
 */
@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application extends SpringBootServletInitializer {
	private static final Logger logger = LoggerFactory
			.getLogger(Application.class);
	
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    	logger.info("Application Started......");
      
        
    }

   
    
    /**
	 * Create a method to  initialize the session factory
	 * @return  Session Factory: An instance of Session Factory
	 */
    @Bean  
    public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf){  
        return hemf.getSessionFactory();  
    }  

}
