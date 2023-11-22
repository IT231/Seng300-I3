package StubClasses;

import com.thelocalmarketplace.software.Session;
import com.thelocalmarketplace.software.SessionState;

/**
 * <p> A Stub class for the funds class test that allows for the sessionState to be controlled with ease without having to 
 * create a full session full of unnecessary code and requirements</p> 
 *  
 * 
 */

public class SessionFundsSimulationStub extends Session {
	public void setPayByCash() {
		sessionState = SessionState.PAY_BY_CASH;
	}
	
	public void setPayByCard() {
		sessionState = SessionState.PAY_BY_CARD;
	}
		
	public void block() {
		sessionState = SessionState.BLOCKED;
	}
}
