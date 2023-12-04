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

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import managers.enums.SessionStatus;
import stubbing.StubbedBarcodedProduct;
import stubbing.StubbedGrid;
import stubbing.StubbedOrderManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestAttendantOverride {
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
	public void testMethodUnblocks() {
		om.setState(SessionStatus.BLOCKED);

		om.onAttendantOverride();

		assertEquals(om.getSessionState(), SessionStatus.NORMAL);
	}

	@Test
	public void testAttendantOverrideSetsAdjustment() {
		// setup
		StubbedBarcodedProduct p = new StubbedBarcodedProduct();
		om.addProduct(p);
		om.setActualWeight(BigDecimal.ONE);

		// calling
		om.onAttendantOverride();

		// asserting
		assertEquals(om.getWeightAdjustment(), new BigDecimal(StubbedBarcodedProduct.WEIGHT).subtract(BigDecimal.ONE));
	}
}
