package com.thelocalmarketplace.software;

import java.util.HashMap;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.software.funds.Funds;
import com.thelocalmarketplace.software.receipt.PrintReceipt;
import com.thelocalmarketplace.software.rules.ItemAddedRule;
import com.thelocalmarketplace.software.weight.Weight;

/**
 * A facade for the logic, supporting its installation on a self checkout
 * station.
 * Allows for a database to be constructed
 * 
 * 
 */

public class SelfCheckoutStationLogic {

	/**
	 * Installs an instance of the logic on the selfCheckoutStation and the session
	 * run on the station
	 * 
	 * @param scs
	 *                The self-checkout station that the logic shall be installed
	 * @param session
	 *                The session that the logic shall be installed on
	 * @return
	 *         returns an instance of the SelfCheckoutStationLogic on a
	 *         SelfChekoutStation and Session
	 */
	public static SelfCheckoutStationLogic installOn(AbstractSelfCheckoutStation scs, Session session) {
		return new SelfCheckoutStationLogic(scs, session);
	}

	/**
	 * Constructors for the instance of logic
	 * 
	 * @param scs
	 *                The self-checkout station that the logic is installed on
	 * @param session
	 *                The session that the logic shall be installed on
	 */
	private SelfCheckoutStationLogic(AbstractSelfCheckoutStation scs, Session session) {
		Funds funds = new Funds(scs);
		Weight weight = new Weight(scs);
		PrintReceipt receiptPrinter = new PrintReceipt(scs);
		HashMap<BarcodedProduct, Integer> barcodedItems = new HashMap<BarcodedProduct, Integer>();
		session.setup(barcodedItems, funds, weight, receiptPrinter);
		new ItemAddedRule(scs, session);
	}
}
