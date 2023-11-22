package com.thelocalmarketplace.software.exceptions;

/**
 * Exception that occurs when an action in unable to occur due to 
 * the product no being found in the list.
 * 
 * 
 */
@SuppressWarnings("serial")
public class ProductNotFoundException extends InvalidActionException {
	/**
	 * Basic constructor
	 * 
	 * @param message
	 * 			An explanatory message of the problem
	 */
	public ProductNotFoundException(String message) {
		super(message);
	}
}