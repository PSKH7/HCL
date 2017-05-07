package com.shoptracker.Exception;


/* This class takes care of producing, when possible, a meaningful output in 
 * case of error. It handles all the exceptions related to shop operations*/
public class ShopException extends Exception {

	private static final long serialVersionUID = 100L;

	public ShopException(String message) {
        super(message);
    }
}
