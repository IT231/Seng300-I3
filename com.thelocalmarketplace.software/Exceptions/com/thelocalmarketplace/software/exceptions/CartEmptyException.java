package com.thelocalmarketplace.software.exceptions;

/**
 * Exception that occurs when an action in unable to occur due to 
 * the shopping cart being empty.
 * 
 * 
 */
@SuppressWarnings("serial")
public class CartEmptyException extends InvalidActionException{
	/**
	 * Basic constructor
	 * 
	 * @param message
	 * 			An explanatory message of the problem
	 */
	public CartEmptyException(String message) {
		super(message);
	}
}
