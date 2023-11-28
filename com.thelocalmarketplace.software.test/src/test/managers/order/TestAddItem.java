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

import javax.naming.OperationNotSupportedException;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

import managers.enums.ScanType;
import stubbing.StubbedBarcodedProduct;
import stubbing.StubbedGrid;
import stubbing.StubbedItem;
import stubbing.StubbedOrderManager;
import stubbing.StubbedPLUCodedItem;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestAddItem {
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

	@Test(expected = IllegalArgumentException.class)
	public void testAddingNullItem() throws OperationNotSupportedException {
		om.addItemToOrder(null, ScanType.MAIN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddingItemWithNullScanner() throws OperationNotSupportedException {
		om.addItemToOrder(new StubbedItem(1), null);
	}

	@Test
	public void addingInstanceOfBarcodedItemViaMainScanner() throws OperationNotSupportedException {
		BarcodedProduct prod = new StubbedBarcodedProduct();
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(prod.getBarcode(), prod);

		BarcodedItem item = new BarcodedItem(prod.getBarcode(), new Mass(prod.getExpectedWeight()));
		om.addItemToOrder(item, ScanType.MAIN);

		assertEquals(om.getProducts().size(), 1);
	}

	@Test
	public void addingInstanceOfBarcodedItemViaHandheldScanner() throws OperationNotSupportedException {
		BarcodedProduct prod = new StubbedBarcodedProduct();
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(prod.getBarcode(), prod);

		BarcodedItem item = new BarcodedItem(prod.getBarcode(), new Mass(prod.getExpectedWeight()));
		om.addItemToOrder(item, ScanType.HANDHELD);

		assertEquals(om.getProducts().size(), 1);
	}

	@Test(expected = OperationNotSupportedException.class)
	public void addingInstanceOfPLUViaMainScanner() throws OperationNotSupportedException {
		PLUCodedItem pluCodedItem = new StubbedPLUCodedItem();
		om.addItemToOrder(pluCodedItem, ScanType.MAIN);
	}

	@Test(expected = OperationNotSupportedException.class)
	public void addingInstanceOfPLUViaHandheldScanner() throws OperationNotSupportedException {
		PLUCodedItem pluCodedItem = new StubbedPLUCodedItem();
		om.addItemToOrder(pluCodedItem, ScanType.HANDHELD);
	}
}
