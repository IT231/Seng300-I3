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

package test.managers;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import stubbing.StubbedOrderManager;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedSystemManager;

public class TestChildManagersCallParent {
	// vars
	private StubbedPaymentManager pm;
	private StubbedOrderManager om;
	private StubbedSystemManager sm;

	@Before
	public void setup() {
		// creating the stubs
		sm = new StubbedSystemManager(BigDecimal.ZERO);
		pm = sm.pmStub;
		om = sm.omStub;
	}

	@Test
	public void testPaymentGetStateCallsSystem() {
		try {
			pm.getState();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.getStateCalled);
	}

	@Test
	public void testPaymentBlockSessionCallsSystem() {
		try {
			pm.blockSession();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.blockSessionCalled);
	}

	@Test
	public void testPaymentUnblockSessionCallsSystem() {
		try {
			pm.unblockSession();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.unblockSessionCalled);
	}

	@Test
	public void testPaymentNotifyAttendantCallsSystem() {
		try {
			pm.notifyAttendant("null");
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.notifyAttendantCalled);
	}

	@Test
	public void testOrderGetStateCallsSystem() {
		try {
			om.getState();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.getStateCalled);
	}

	@Test
	public void testOrderBlockSessionCallsSystem() {
		try {
			om.blockSession();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.blockSessionCalled);
	}

	@Test
	public void testOrderUnblockSessionCallsSystem() {
		try {
			om.unblockSession();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.unblockSessionCalled);
	}

	@Test
	public void testOrderNotifyAttendantCallsSystem() {
		try {
			om.notifyAttendant("null");
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.notifyAttendantCalled);
	}

	@Test
	public void testPaymentRecordTransactionCallsSystem() {
		try {
			pm.recordTransaction(null, null, null);
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.recordTransactionCalled);
	}
}
