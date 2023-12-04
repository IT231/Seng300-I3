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

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.tdc.CashOverloadException;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinStorageUnit;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import powerutility.PowerGrid;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestInsertCoin {

	public AbstractSelfCheckoutStation machine;

	// vars
	private StubbedPaymentManager pm;
	private StubbedSystemManager sm;

	public Coin fiveCent;

	@Before
	public void setup() {
		// configuring the hardware
		StubbedStation.configure();

		// creating the hardware
		this.machine = new StubbedStation().machine;
		this.machine.plugIn(PowerGrid.instance());
		this.machine.turnOn();

		// creating the stubs
		sm = new StubbedSystemManager(BigDecimal.ZERO);
		pm = sm.pmStub;

		// configuring the machine
		sm.configure(machine);

		Coin.DEFAULT_CURRENCY = Currency.getInstance(Locale.CANADA);
		fiveCent = new Coin(new BigDecimal(0.05));

	}

	// Expect no errors
	@Test
	public void testValidInsert() {
		this.pm.insertCoin(fiveCent);
	}

	@Test
	public void testDisabledInsert() {
		this.machine.getCoinSlot().disable();
		this.pm.insertCoin(fiveCent);
		assertTrue("attendant was called", this.sm.notifyAttendantCalled);
		assertTrue("Session was blocked", this.sm.blockSessionCalled);
	}

	/**
	 * trying to trigger a cash overload exception
	 * 
	 * @throws CashOverloadException
	 */

	@Test
	public void testOverloadedInsert() throws CashOverloadException {
		CoinStorageUnit csu = this.machine.getCoinStorage();

		// loading the dispensers to the max
		for (int i = 0; i < this.machine.getCoinDenominations().size(); i++) {
			BigDecimal denomination = this.machine.getCoinDenominations().get(i);
			ICoinDispenser coinDispenser = this.machine.getCoinDispensers().get(denomination);
			for (int j = 0; j < coinDispenser.getCapacity(); j++) {
				coinDispenser.load(fiveCent);
			}
		}

		// loading the storage unit to the max
		for (int i = 0; i < csu.getCapacity(); i++) {
			csu.load(fiveCent);
		}

		// inserting a coin, this should trigger a cash overload exception
		this.pm.insertCoin(fiveCent);

		// asserting
		assertTrue("attendant was called", this.sm.notifyAttendantCalled);
		assertTrue("Session was blocked", this.sm.blockSessionCalled);

	}

}
