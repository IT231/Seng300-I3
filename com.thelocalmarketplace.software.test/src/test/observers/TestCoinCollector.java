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

package test.observers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import observers.payment.CoinCollector;
import stubbing.StubbedGrid;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestCoinCollector {

	// vars
	private StubbedPaymentManager pm;
	private StubbedSystemManager sm;
	private CoinCollector cc;
	private AbstractSelfCheckoutStation machine;

	@Before
	public void setup() {
		// creating the stubs
		sm = new StubbedSystemManager();
		pm = sm.pmStub;

		// configuring the hardware
		StubbedStation.configure();

		// creating the hardware
		machine = new StubbedStation().machine;
		machine.plugIn(StubbedGrid.instance());

		// configuring the machine
		sm.configure(machine);

		cc = pm.getCoinCollector();
		machine.getCoinValidator().disable(); // the component is enabled by default, OK
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullPaymentManager() {
		new CoinCollector(null, machine.getCoinValidator());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullDevice() {
		new CoinCollector(pm, null);
	}

	/***
	 * Testing when the system detects a new valid coin has been added
	 */
	@Test
	public void validCoinDetectedTest() {
		// We don't care who the validator is, so it may be null.
		cc.validCoinDetected(null, new BigDecimal(2.0f));

		// We expect the PaymentManager to know about an coin of value 3
		assertTrue("The Payment Manager did not recieve the correct coin value",
				pm.notifyBalanceAddedValue.floatValue() == 2.0f);
	}

	@Test
	public void testCannotUseWhenTurnedOffAndDisabled() {
		// asserting
		assertFalse(cc.canUse());
	}

	@Test
	public void testCannotUseWhenTurnedOff() {
		// this can never actually happen
		// the machine needs to be turned on before I can call enable
	}

	@Test
	public void testCannotUseWhenDisabled() {
		machine.getCoinValidator().activate();

		// asserting
		assertFalse(cc.canUse());
	}

	@Test
	public void testCanUseWhenTurnedOnAndEnabled() {
		machine.getCoinValidator().activate();
		machine.getCoinValidator().enable();

		// asserting
		assertTrue(cc.canUse());
	}
}
