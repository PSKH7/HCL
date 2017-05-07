package com.shoptracker.Exception;


import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.shoptracker.models.Error;

import org.springframework.http.HttpStatus;

@ControllerAdvice
public class RestServicesException {
	    @ExceptionHandler(MissingServletRequestParameterException.class)
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    @ResponseBody
	    public Error handleRequestParameterNotFoundException(final MissingServletRequestParameterException ex) {
		  Error err = null;
		   if(ex.getMessage().contains("latitude")){
			   err = new Error("Missing Parameter", "Latitude is missing of type double");
		   }
		   if(ex.getMessage().contains("longitude")){
			   err = new Error("Missing Parameter", "Longitude is missing of type double");
		   }
		  
	        return err;
	   }

	   

}
