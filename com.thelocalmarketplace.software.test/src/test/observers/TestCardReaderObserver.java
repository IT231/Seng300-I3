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

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import observers.payment.CardReaderObserver;
import stubbing.StubbedCardData;
import stubbing.StubbedGrid;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestCardReaderObserver {
	// vars
	private StubbedPaymentManager pm;
	private StubbedSystemManager sm;
	private CardReaderObserver cro;
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

		// creating the observer
		cro = pm.getCardReaderObserver();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullPaymentManager() {
		new CardReaderObserver(null, machine.cardReader);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullDevice() {
		new CardReaderObserver(pm, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNotifyCardSwipeNullData() {
		cro.theDataFromACardHasBeenRead(null);
	}

	@Test
	public void testNotifyCardSwipeValidData() {
		// this test should not throw
		cro.theDataFromACardHasBeenRead(new StubbedCardData());
	}

	@Test
	public void testCannotUseWhenTurnedOffAndDisabled() {
		// asserting
		assertFalse(cro.canUse());
	}

	@Test
	public void testCannotUseWhenTurnedOff() {
		// this can never actually happen
		// the machine needs to be turned on before I can call enable
	}

	@Test
	public void testCannotUseWhenDisabled() {
		machine.cardReader.turnOn();
		machine.cardReader.disable();

		// asserting
		assertFalse(cro.canUse());
	}

	@Test
	public void testCanUseWhenTurnedOnAndEnabled() {
		machine.cardReader.turnOn();
		machine.cardReader.enable();

		// asserting
		assertTrue(cro.canUse());
	}
}
