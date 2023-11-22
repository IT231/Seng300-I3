package com.thelocalmarketplace.software.weight;

/**
 * Listens for Weight Discrepancies and changes in the Weight class
 * 
 * 
 */

public interface WeightListener {
	
	/**
	 * Signals an event in which a Discrepancy has occurred
	 * 
	 * May be modified in the future to check if the discrepancy was caused by adding/removing an item
	 * to the cart, or adding/removing an item to the scale
	 */
	void notifyDiscrepancy();
	
	/**
	 * Signals an event in which a previous Discrepancy has been resolved
	 */
	void notifyDiscrepancyFixed();
	
}
