// Aleksandr Sokolov (30191754)
// Azariah Francisco (30085863)
// Brandon Smith (30141515)
// Carlos Serrouya (30192761)
// Diego de Jaraiz (30176017)
// Emily Willams (30122865)
// Evan Ficzere (30192404)
// Jaden Taylor (30113034)
// Joshua Bourchier (30194364)
// Justine Mangaliman (30164741)
// Kaelin Good (30092239)
// Laura Yang（30156356)
// Myra Latif (30171760)
// Noelle Thundathil (30115430)
// Raj Rawat (30173990)
// Roshan Patel (30184010)
// Sam Fasakin (30161903)
// Simon Bondad (30163401)
// Simon Oseen (30144175)
// Sohaib Zia (30160114)
// Sunny Hoang (30170708)
// Yasemin Khanmoradi (30066537)

package test.managers.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Item;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import managers.enums.SessionStatus;
import stubbing.StubbedGrid;
import stubbing.StubbedItem;
import stubbing.StubbedOrderManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestDoNotBag {
	// machine
	private AbstractSelfCheckoutStation machine;

	// vars
	private StubbedOrderManager om;
	private StubbedSystemManager sm;

	@Before
	public void setup() {
		// configuring the hardware
		StubbedStation.configure();

		// creating the hardware
		machine = new StubbedStation().machine;
		machine.plugIn(StubbedGrid.instance());
		machine.turnOn();

		// creating the stubs
		sm = new StubbedSystemManager(BigDecimal.ZERO);
		om = sm.omStub;

		// configuring the machine
		sm.configure(machine);
	}

	@Test
	public void testDoNotBagRequestSetsFlag() {
		Item item = new StubbedItem(2);
		om.onDoNotBagRequest(item);
		
		// check if the flag for no bagging request is set to true
		assertTrue(om.getNoBaggingRequest());
	}
	
    @Test(expected = IllegalArgumentException.class)
    public void testDoNotBagNullItem() {
        Item item = null;
        
        // call with a null item, expecting an exception
    	om.onDoNotBagRequest(item);
    }
    
    @Test
    public void testWeightAdjustmentInNormalState() {
        Item item = new StubbedItem(2);

        om.setWeightAdjustment(BigDecimal.valueOf(5));
        om.onDoNotBagRequest(item);

        // check if the weight adjustment has been updated correctly (5 + 2 = 7)
        assertEquals(BigDecimal.valueOf(7), om.getWeightAdjustment());
    }
    
    @Test
    public void testWeightAdjustmentInBlockedState() {
        Item item = new StubbedItem(2);

        // set state to a state other than NORMAL
        sm.setState(SessionStatus.BLOCKED);
        om.onDoNotBagRequest(item);

        // check if the weight adjustment remains unchanged
        assertEquals(BigDecimal.ZERO, om.getWeightAdjustment());
    }
	
    @Test
    public void testDoNotBagRequestNotifiesAttendant() {
        Item item = new StubbedItem(4);
        
        sm.setState(SessionStatus.NORMAL);
        om.onDoNotBagRequest(item);

        // check if the attendant is notified with the correct reason
        assertEquals("do not bag request was received", sm.getAttendantNotification());
    }
    
}
