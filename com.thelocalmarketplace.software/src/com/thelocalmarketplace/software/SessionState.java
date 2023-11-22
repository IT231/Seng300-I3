package com.thelocalmarketplace.software;

/*
 * All possible states that a Session can be in
 * 
 * 
 */

public enum SessionState {
    PRE_SESSION(false),// Session is not currently running
    IN_SESSION(false),// Session is currently running
    BLOCKED(false),// The Session has been blocked and cannot progress
    ADDING_BAGS(false), // User signaled they want to add bags to the bagging area
    PAY_BY_CASH(true), // User signaled they want to pay using cash (one of the pay states)
    PAY_BY_CARD(true);// User signaled they want to pay by card (one of the pay states)
    
	
	
    // This is to simplify checking if the state is a pay state
    private final boolean payState;
	private SessionState(final boolean payState) {
		this.payState = payState;
	}
	
	/*
	 * Returns true if the SessionState is a pay state (eg: pay by card, pay by cash)
	 */
	public boolean inPay() {
		return this.payState;
	}
}
