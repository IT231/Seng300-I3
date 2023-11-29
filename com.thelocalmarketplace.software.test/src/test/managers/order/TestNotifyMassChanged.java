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

import managers.enums.SessionStatus;
import stubbing.StubbedBarcodedProduct;
import stubbing.StubbedOrderManager;
import stubbing.StubbedSystemManager;

public class TestNotifyMassChanged {
	// vars
	private StubbedOrderManager om;
	private StubbedSystemManager sm;

	@Before
	public void setup() {
		// creating the stubs
		sm = new StubbedSystemManager(BigDecimal.ZERO);
		om = sm.omStub;
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNotifyMassChangeThrowsOnNull() {
		om.notifyMassChanged(null, null);
	}

	@Test
	public void testNotifyMassChangeThrowsBlocks() {
		om.notifyMassChanged(null, BigDecimal.ONE);

		// station should be blocked now
		assertEquals(om.getState(), SessionStatus.BLOCKED);
	}

	@Test
	public void testNotifyMassChangeThrowsWithAdjustmentDoesntBlock() {
		om.addProduct(new StubbedBarcodedProduct());
		om.setWeightAdjustment(new BigDecimal(StubbedBarcodedProduct.WEIGHT));

		om.notifyMassChanged(null, BigDecimal.ZERO);

		// station should still be normal
		assertEquals(om.getState(), SessionStatus.NORMAL);
	}

	@Test
	public void testCheckWeightDifferenceTriggersOnNormal() {
		om.setState(SessionStatus.NORMAL);

		om.checkWeightDifference(BigDecimal.ONE);

		// the OrderManager should be blocked now
		assertEquals(om.getState(), SessionStatus.BLOCKED);
	}

	@Test
	public void testCheckWeightDifferenceDoesntTriggerOnNormal() {
		om.setState(SessionStatus.NORMAL);

		om.checkWeightDifference(BigDecimal.ZERO);

		// the OrderManager should still be normal
		assertEquals(om.getState(), SessionStatus.NORMAL);
	}

	@Test
	public void testCheckWeightDifferenceUnblocks() {
		om.setState(SessionStatus.BLOCKED);

		om.checkWeightDifference(BigDecimal.ZERO);

		// the OrderManager should be unblocked now
		assertEquals(om.getState(), SessionStatus.NORMAL);
	}

	@Test
	public void testCheckWeightDifferenceDoesntUnblock() {
		om.setState(SessionStatus.BLOCKED);

		om.checkWeightDifference(BigDecimal.ONE);

		// the OrderManager should still be blocked
		assertEquals(om.getState(), SessionStatus.BLOCKED);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testNotifyMassChangedThrowsWhenPaid() {
		om.setState(SessionStatus.PAID);
		
		om.notifyMassChanged(null, BigDecimal.ONE);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testCheckWeightDifferenceThrowsWhenPaid() {
		om.setState(SessionStatus.PAID);
		
		om.checkWeightDifference(BigDecimal.ONE);
	}
}
