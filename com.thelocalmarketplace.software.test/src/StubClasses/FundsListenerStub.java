package StubClasses;

import java.util.ArrayList;

import com.thelocalmarketplace.software.funds.FundsListener;

/**
 * <p> A Stub class for the funds test class that is used to listen for payments done with both cash and card </p> 
 *  
 * 
 */

public class FundsListenerStub implements FundsListener {
	ArrayList<String> events;

	public FundsListenerStub() {
		events = new ArrayList<String>();
	}

	@Override
	public void notifyPaid() {
		events.add("Paid");

	}

	public ArrayList<String> getEvents() {
		return events;
	}
}
