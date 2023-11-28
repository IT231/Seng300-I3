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

import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

import observers.order.BarcodeScannerObserver;
import stubbing.StubbedGrid;
import stubbing.StubbedOrderManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;
import utils.DatabaseHelper;

public class TestBarcodeScannerObserver {

	// vars
	private StubbedOrderManager om;
	private StubbedSystemManager sm;
	private BarcodeScannerObserver bso;
	private BarcodedItem item;
	private AbstractSelfCheckoutStation machine;

	@Before
	public void setup() {
		// creating a random item and putting in the database
		this.item = DatabaseHelper.createRandomBarcodedItem();

		// creating the stubs
		sm = new StubbedSystemManager();
		om = sm.omStub;

		// configuring the hardware
		StubbedStation.configure();

		// creating the hardware
		machine = new StubbedStation().machine;
		machine.plugIn(StubbedGrid.instance());

		// configuring the machine
		sm.configure(machine);

		bso = om.getMainBarcodeObserver();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullOrderManager() {
		new BarcodeScannerObserver(null, machine.handheldScanner);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullDevice() {
		new BarcodeScannerObserver(om, null);
	}

	/***
	 * Tests if the OrderManager actually received the item the hardware scanned.
	 * 
	 * This disregards most of the implementation of the method in OrderManager, the
	 * method `notifyBarcodeScanned` is tested in TestOrderManager.java
	 */
	@Test
	public void testNotifyOrderManagerBarcodeScanned() {
		// calling the function to notify that some barcode was scanned
		bso.aBarcodeHasBeenScanned(null, item.getBarcode());

		// getting the barcoded product
		BarcodedProduct prod = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(item.getBarcode());

		// ensuring that the item is present in the OrderManager
		assertTrue(om.getProducts().contains(prod));
	}

	@Test
	public void testCannotUseWhenTurnedOffAndDisabled() {
		// asserting
		assertFalse(bso.canUse());
	}

	@Test
	public void testCannotUseWhenTurnedOff() {
		// this can never actually happen
		// the machine needs to be turned on before I can call enable
	}

	@Test
	public void testCannotUseWhenDisabled() {
		machine.mainScanner.turnOn();
		machine.mainScanner.disable();

		// asserting
		assertFalse(bso.canUse());
	}

	@Test
	public void testCanUseWhenTurnedOnAndEnabled() {
		machine.mainScanner.turnOn();
		machine.mainScanner.enable();

		// asserting
		assertTrue(bso.canUse());
	}
}
