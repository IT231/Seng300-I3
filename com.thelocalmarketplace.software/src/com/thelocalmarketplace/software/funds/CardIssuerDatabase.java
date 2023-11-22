package com.thelocalmarketplace.software.funds;

import java.util.HashMap;
import java.util.Map;

import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.external.CardIssuer;
/**
 * Database storing CardIssuer(s) that exist
 * 
 * 
 */
public class CardIssuerDatabase {
	private CardIssuerDatabase() {}	
	
	public static final Map<String, CardIssuer> CARD_ISSUER_DATABASE = new HashMap<>();
}
