package com.thelocalmarketplace.software.funds;

/**
 * Listens for if payment has been completed
 * 
 * 
 */
public interface FundsListener {

	/**
	 * Signals an event in which the customer has paid for the complete amount of the order
	 */
	void notifyPaid();
}
