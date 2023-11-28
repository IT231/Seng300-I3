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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import javax.naming.OperationNotSupportedException;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.NoCashAvailableException;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.IBanknoteDispenser;
import com.tdc.coin.Coin;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

import managers.enums.ScanType;
import observers.payment.BanknoteCollector;
import observers.payment.CoinCollector;
import powerutility.PowerGrid;
import stubbing.StubbedBarcodedProduct;
import stubbing.StubbedOrderManager;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestTenderChange {
	public AbstractSelfCheckoutStation machine;
	public StubbedSystemManager sm;
	public StubbedPaymentManager pm;
	public StubbedOrderManager om;
	public CardIssuer issuer;
	public CoinCollector cc;
	public BanknoteCollector bc;
	public ICoinDispenser coinDispenser;
	public IBanknoteDispenser banknoteDispenser;

	public Coin fiveCent;
	public Coin oneDollar;
	public Coin twoDollar;
	public Banknote fiveNote;
	public Banknote twentyNote;
	public Banknote fiftyNote;

	public BarcodedProduct prod;
	public BarcodedItem item;

	@Before
	public void setup() throws OperationNotSupportedException {
		// configuring the hardware
		StubbedStation.configure();
		// creating the hardware
		machine = new StubbedStation().machine;
		machine.plugIn(PowerGrid.instance());
		machine.turnOn();
		// creating the stubs
		sm = new StubbedSystemManager(BigDecimal.ZERO);
		om = sm.omStub;
		pm = sm.pmStub;
		// configuring the machine
		sm.configure(machine);

		// Create coins and banknotes
		Coin.DEFAULT_CURRENCY = Currency.getInstance(Locale.CANADA);
		fiveCent = new Coin(new BigDecimal(0.05));
		oneDollar = new Coin(new BigDecimal(1.00));
		twoDollar = new Coin(new BigDecimal(2.00));
		fiveNote = new Banknote(Currency.getInstance(Locale.CANADA), new BigDecimal(5.00));
		twentyNote = new Banknote(Currency.getInstance(Locale.CANADA), new BigDecimal(20.00));
		fiftyNote = new Banknote(Currency.getInstance(Locale.CANADA), new BigDecimal(50.00));

		// Add item to order to get total price
		// Price of item is $10
		prod = new StubbedBarcodedProduct();
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(prod.getBarcode(), prod);
		item = new BarcodedItem(prod.getBarcode(), new Mass(prod.getExpectedWeight()));
		om.addItemToOrder(item, ScanType.MAIN);

	}

	// No Coins in machine
	@Test(expected = NoCashAvailableException.class)
	public void testEmptyMachine() throws RuntimeException, NoCashAvailableException {
		this.pm.tenderChange();
	}

	@Test(expected = NoCashAvailableException.class)
	public void testNotEnoughChangeInMachine()
			throws CashOverloadException, DisabledException, RuntimeException, NoCashAvailableException {
		// Load machine with 2 dollar coin
		BigDecimal twoDenom = this.machine.getCoinDenominations().get(5);
		coinDispenser = this.machine.getCoinDispensers().get(twoDenom);
		coinDispenser.load(twoDollar);

		this.machine.getBanknoteInput().receive(fiftyNote);

		this.pm.tenderChange();

	}

	@Test
	public void testPaymentEqualsTotal() throws CashOverloadException, OperationNotSupportedException,
			DisabledException, RuntimeException, NoCashAvailableException {

		// Load machine with 2 dollar coins
		BigDecimal denomination = this.machine.getCoinDenominations().get(5);
		coinDispenser = this.machine.getCoinDispensers().get(denomination);
		for (int i = 0; i < 10; i++) {
			coinDispenser.load(twoDollar);
		}
		// Receives $10 from customer
		for (int i = 0; i < 5; i++) {
			this.machine.getCoinSlot().receive(twoDollar);
		}

		// No Change to give back, should return true
		assertTrue("No change needed", this.pm.tenderChange());

	}

	// tests if tenderChnage returns true when
	// change is returned
	@Test
	public void testReturnChange() throws OperationNotSupportedException, CashOverloadException, DisabledException,
			RuntimeException, NoCashAvailableException {

		// Load machine with 2 dollar coins
		BigDecimal denomination = this.machine.getCoinDenominations().get(5);
		coinDispenser = this.machine.getCoinDispensers().get(denomination);
		for (int i = 0; i < 10; i++) {
			coinDispenser.load(twoDollar);
		}
		this.machine.getBanknoteInput().receive(twentyNote);

		// Change should be dispensed
		assertTrue("Change was dispensed", this.pm.tenderChange());
	}

	@Test(expected = RuntimeException.class)
	public void testPaymentLessThanPrice()
			throws CashOverloadException, DisabledException, RuntimeException, NoCashAvailableException {
		// Load machine with 2 dollar coins
		BigDecimal denomination = this.machine.getCoinDenominations().get(5);
		coinDispenser = this.machine.getCoinDispensers().get(denomination);
		for (int i = 0; i < 10; i++) {
			coinDispenser.load(twoDollar);
		}
		this.machine.getBanknoteInput().receive(fiveNote);
		this.pm.tenderChange();
	}

	@Test
	public void testEmitMultipleBills()
			throws CashOverloadException, DisabledException, RuntimeException, NoCashAvailableException {

		// load machine with five dollar bills
		BigDecimal fiveDenom = this.machine.getBanknoteDenominations()[0];
		banknoteDispenser = this.machine.getBanknoteDispensers().get(fiveDenom);
		for (int i = 0; i < 10; i++) {
			banknoteDispenser.load(fiveNote);
		}
		// load machine with twenty dollar bills
		BigDecimal twentyDenom = this.machine.getBanknoteDenominations()[3];
		banknoteDispenser = this.machine.getBanknoteDispensers().get(twentyDenom);

		banknoteDispenser.load(twentyNote);

		this.machine.getBanknoteInput().receive(fiftyNote);
		assertTrue(this.pm.tenderChange());

	}

	@Test
	public void testEmitMultipleCoins()
			throws CashOverloadException, DisabledException, RuntimeException, NoCashAvailableException {

		// Load machine with 2 dollar coin
		coinDispenser = this.machine.getCoinDispensers().get(new BigDecimal(2));
		coinDispenser.load(twoDollar);

		// Load machine with 1 dollar coins
		coinDispenser = this.machine.getCoinDispensers().get(new BigDecimal(1));
		for (int i = 0; i < 10; i++) {
			coinDispenser.load(oneDollar);
		}

		// inputting a $20 bill
		this.machine.getBanknoteInput().receive(twentyNote);

		// need to update the payment so we don't trigger the wrong error
		pm.setPayment(2 + (1 * 10));

		assertTrue(this.pm.tenderChange());
	}

	@Test
	public void disabledCoinDispenser()
			throws CashOverloadException, DisabledException, RuntimeException, NoCashAvailableException {

		// Load machine with 2 dollar coins
		BigDecimal denomination = this.machine.getCoinDenominations().get(5);
		coinDispenser = this.machine.getCoinDispensers().get(denomination);
		for (int i = 0; i < 10; i++) {
			coinDispenser.load(twoDollar);
		}

		// Receives $10 from customer
		for (int i = 0; i < 6; i++) {
			this.machine.getCoinSlot().receive(twoDollar);
		}
		this.coinDispenser.disable();

		// Change should not be emitted
		assertFalse(this.pm.tenderChange());
		assertTrue("attendant was called", this.sm.notifyAttendantCalled);
		assertTrue("Session was blocked", this.sm.blockSessionCalled);

	}

	@Test
	public void disabledBanknoteDispsenser()
			throws CashOverloadException, DisabledException, RuntimeException, NoCashAvailableException {

		// load machine with five dollar bills
		BigDecimal fiveDenom = this.machine.getBanknoteDenominations()[0];
		banknoteDispenser = this.machine.getBanknoteDispensers().get(fiveDenom);
		for (int i = 0; i < 10; i++) {
			banknoteDispenser.load(fiveNote);
		}
		this.machine.getBanknoteInput().receive(twentyNote);

		this.banknoteDispenser.disable();
		// Change should not be emitted
		assertFalse(this.pm.tenderChange());
		assertTrue("attendant was called", this.sm.notifyAttendantCalled);
		assertTrue("Session was blocked", this.sm.blockSessionCalled);

	}

}
