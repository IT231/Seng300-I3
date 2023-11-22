package com.thelocalmarketplace.software.exceptions;

/**
 * An exception that can be raised when the behaviour within the software is
 * not able to be used. For example, this can occur when an item is attempted to be
 * added when the session is frozen. Thus, this is an invalidAction to occur.
 * 
 * 
 */
@SuppressWarnings("serial")
public class InvalidActionException extends RuntimeException{
	/**
	 * Basic constructor
	 * 
	 * @param message
	 * 			An explanatory message of the problem
	 */
	public InvalidActionException(String message) {
		super(message);
	}
}
