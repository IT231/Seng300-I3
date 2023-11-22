package com.thelocalmarketplace.software.exceptions;
/**
 * Exception that occurs when there is not enough change in the machine to dispense to the customer
 * 
 * 
 */
@SuppressWarnings("serial")
public class NotEnoughChangeException extends RuntimeException{
	
	public NotEnoughChangeException(String message) {
		super(message);
	}

}
