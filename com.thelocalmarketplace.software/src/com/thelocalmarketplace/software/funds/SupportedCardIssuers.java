package com.thelocalmarketplace.software.funds;

/**
 * <p> Allows us to store a set of supported card issuers. In practice this is something that could be installed/updated outside of
 * a session but cannot be done within a session or the software specific to the session</p> 
 *  
 * 
 */
public enum SupportedCardIssuers {
    ONE("DisasterCard"),
    TWO("Viva"),
    THREE("Canadian Depress"),
    FOUR("Detrac Debit");
    
    // This is to simplify checking if the state is a pay state
    private final String financialInstitution;
	private SupportedCardIssuers(final String financialInstitution) {
		this.financialInstitution = financialInstitution;
	}
	
	public String getIssuer() {
		return this.financialInstitution;
	}
}
