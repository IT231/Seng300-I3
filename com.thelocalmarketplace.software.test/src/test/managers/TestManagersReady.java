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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.naming.OperationNotSupportedException;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import managers.enums.SessionStatus;
import stubbing.StubbedGrid;
import stubbing.StubbedOrderManager;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestManagersReady {
	// vars
	private StubbedPaymentManager pm;
	private StubbedSystemManager sm;
	private StubbedOrderManager om;
	private AbstractSelfCheckoutStation machine;

	@Before
	public void setup() throws OperationNotSupportedException {
		// configuring the hardware
		StubbedStation.configure();

		// creating the hardware
		machine = new StubbedStation().machine;
		machine.plugIn(StubbedGrid.instance());

		// creating the stubs
		sm = new StubbedSystemManager();
		pm = sm.pmStub;
		om = sm.omStub;

		// configuring the machine
		sm.configure(machine);

		// setting the state of the machine
		sm.setState(SessionStatus.NORMAL);
	}

	@Test
	public void testPaymentNotReady() {
		assertFalse("payment manager not ready", pm.ready());
	}

	@Test
	public void testOrderNotReady() {
		assertFalse("order manager not ready", om.ready());
	}

	@Test
	public void testSystemNotReady() {
		assertFalse("payment manager not ready", pm.ready());
		assertFalse("order manager not ready", om.ready());
		assertFalse("system manager not ready", sm.ready());
	}

	@Test
	public void testPaymentReadyWhenMachineTurnedOn() {
		machine.turnOn();

		assertTrue("payment manager ready", pm.ready());
	}

	@Test
	public void testOrderReadyWhenMachineTurnedOn() {
		machine.turnOn();

		assertTrue("order manager ready", om.ready());
	}

	@Test
	public void testSystemReadyWhenMachineTurnedOn() {
		machine.turnOn();

		assertTrue("payment manager ready", pm.ready());
		assertTrue("order manager ready", om.ready());
		assertTrue("system manager ready", sm.ready());
	}
}
