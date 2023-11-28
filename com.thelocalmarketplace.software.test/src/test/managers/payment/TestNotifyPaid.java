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

package test.managers.payment;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import managers.enums.SessionStatus;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedSystemManager;

/**
 * This test class is just for methods that don't fit into the other files.
 */
public class TestNotifyPaid {
	// vars
	private StubbedPaymentManager pm;
	private StubbedSystemManager sm;

	@Before
	public void setup() {
		// creating the stubs
		sm = new StubbedSystemManager();
		pm = sm.pmStub;
	}

	@Test
	public void testNotifyPaymentAdded() {
		pm.notifyBalanceAdded(BigDecimal.ONE);
		
		assertEquals(BigDecimal.ONE, pm.getCustomerPayment());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNotifyPaymentNull() {
		pm.notifyBalanceAdded(null);
	}
	
	@Test
	public void testNotifyPaidSetsState() {
		sm.setState(SessionStatus.NORMAL);
		
		pm.notifyPaid();
		
		assertEquals(SessionStatus.PAID, sm.getState());
	}
}
