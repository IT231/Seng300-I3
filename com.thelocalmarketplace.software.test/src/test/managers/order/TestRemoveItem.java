// André Beaulieu - 30174544
// Liam Major 30223023

package test.managers.order;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import javax.naming.OperationNotSupportedException;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scale.AbstractElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

import managers.enums.ScanType;
import stubbing.StubbedGrid;
import stubbing.StubbedOrderManager;
import stubbing.StubbedOrderManagerNotify;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestRemoveItem {
	private StubbedOrderManager om;
	private StubbedSystemManager sm;
	private AbstractSelfCheckoutStation machine;

	private Barcode sampleBarcode;
	private BarcodedProduct sampleProduct;

	/**
	 * Sets up a selfcheckout station, and an ordermanager to test Establishes some
	 * go-to objects we'll use, such as a barcode and an item. Populates the
	 * database for item-checking reasons.
	 */
	@Before
	public void setUp() {
		// configuring the hardware
		StubbedStation.configure();

		// creating the hardware
		machine = new StubbedStation().machine;

		sm = new StubbedSystemManager(BigDecimal.ZERO);
		om = sm.omStub;

		// configuring the machine
		sm.configure(machine);

		machine.plugIn(StubbedGrid.instance());
		machine.turnOn();
		
		// creating objects for testing
		sampleBarcode = new Barcode(new Numeral[] { Numeral.one, Numeral.two, Numeral.three });
		// Create a sample product and put it in the database.
		sampleProduct = new BarcodedProduct(sampleBarcode, "Sample Product", 2, 0.0001);
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(sampleBarcode, sampleProduct);
	}

	/**
	 * When an Item isn't PLU-Coded, or Barcoded, then this happens. A null item is
	 * a good example of this type.
	 * 
	 * @throws OperationNotSupportedException This is expected to happen
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWhenItemTypeNotRecognized() throws OperationNotSupportedException {
		Item item = null;
		om.removeItemFromOrder(item);
	}

	/**
	 * PLU Coded items are unimplemented, so we expect an exception for the time
	 * being.
	 * 
	 * @throws OperationNotSupportedException This is expected to happen.
	 */
	@Test(expected = OperationNotSupportedException.class)
	public void testWhenItemIsPLUCoded() throws OperationNotSupportedException {
		// Currently, PLU codes are not accepted so we expect this exception.
		PLUCodedItem item = new PLUCodedItem(new PriceLookUpCode("1234"), new Mass(1));
		om.removeItemFromOrder((Item) item);
	}

	/**
	 * Trying to add an item not currently in the order has this case happen. This
	 * sets up a new item not in the current order and tries to remove it.
	 * 
	 * @throws OperationNotSupportedException This is expected to happen.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWhenItemNotInOrder() throws OperationNotSupportedException {
		Barcode otherBarcode = new Barcode(new Numeral[] { Numeral.nine, Numeral.nine, Numeral.nine, });
		BarcodedItem item = new BarcodedItem(otherBarcode, new Mass(2));
		om.removeItemFromOrder((Item) item);
	}

	/**
	 * If the item is a Barcoded type and is in the current order, we expect that it
	 * will be removed, all OrderManager's listeners will be informed, and that the
	 * item will be removed from the scale.
	 * 
	 * @throws OperationNotSupportedException
	 * @throws OverloadedDevice
	 */
	@Test
	public void testWhenItemInOrder() throws OperationNotSupportedException, OverloadedDevice {
		AbstractElectronicScale scale = (AbstractElectronicScale) machine.baggingArea;
		StubbedOrderManagerNotify omnStub = new StubbedOrderManagerNotify();
		om.registerListener(omnStub);

		BarcodedItem item = new BarcodedItem(sampleBarcode, new Mass(100));
		// Make it so an item of this product already exists in the list.
		//om.addProduct(sampleProduct);
		om.addItemToOrder(item, ScanType.MAIN);
		//scale.addAnItem(item);
		om.removeItemFromOrder((Item) item);

		// We expect our listeners to hear about this.
		assertTrue(omnStub.gotOnItemRemovedFromOrderMessage == true);
		assertTrue(omnStub.itemRemovedFromOrder == item);

		// We expect the item to be removed from the bagging area.
		// Because this is the only item, removing it will make the current mass == 0
		assertTrue(scale.getCurrentMassOnTheScale().inGrams().floatValue() == 0);
	}
	
	/**
	 * When an Item isn't PLU-Coded, or Barcoded, then this happens. A null item is
	 * a good example of this type.
	 * 
	 * @throws OperationNotSupportedException This is expected to happen
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSystemManagerItemTypeNotRecognized() throws OperationNotSupportedException {
		Item item = null;
		sm.removeItemFromOrder(item);
	}

	/**
	 * PLU Coded items are unimplemented, so we expect an exception for the time
	 * being.
	 * 
	 * @throws OperationNotSupportedException This is expected to happen.
	 */
	@Test(expected = OperationNotSupportedException.class)
	public void testSystemManagerItemIsPLUCoded() throws OperationNotSupportedException {
		// Currently, PLU codes are not accepted so we expect this exception.
		PLUCodedItem item = new PLUCodedItem(new PriceLookUpCode("1234"), new Mass(1));
		sm.removeItemFromOrder((Item) item);
	}

	/**
	 * Trying to add an item not currently in the order has this case happen. This
	 * sets up a new item not in the current order and tries to remove it.
	 * 
	 * @throws OperationNotSupportedException This is expected to happen.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSystemManagerItemNotInOrder() throws OperationNotSupportedException {
		Barcode otherBarcode = new Barcode(new Numeral[] { Numeral.nine, Numeral.nine, Numeral.nine, });
		BarcodedItem item = new BarcodedItem(otherBarcode, new Mass(2));
		sm.removeItemFromOrder((Item) item);
	}

	/**
	 * If the item is a Barcoded type and is in the current order, we expect that it
	 * will be removed, all OrderManager's listeners will be informed, and that the
	 * item will be removed from the scale.
	 * 
	 * @throws OperationNotSupportedException
	 * @throws OverloadedDevice
	 */
	@Test
	public void testSystemManagerItemInOrder() throws OperationNotSupportedException, OverloadedDevice {
		AbstractElectronicScale scale = (AbstractElectronicScale) machine.baggingArea;
		StubbedOrderManagerNotify omnStub = new StubbedOrderManagerNotify();
		sm.omStub.registerListener(omnStub);

		BarcodedItem item = new BarcodedItem(sampleBarcode, new Mass(100));
		// Make it so an item of this product already exists in the list.
		//sm.addProduct(sampleProduct);
		sm.addItemToOrder(item, ScanType.MAIN);
		//scale.addAnItem(item);
		sm.removeItemFromOrder((Item) item);

		// We expect our listeners to hear about this.
		assertTrue(omnStub.gotOnItemRemovedFromOrderMessage == true);
		assertTrue(omnStub.itemRemovedFromOrder == item);

		// We expect the item to be removed from the bagging area.
		// Because this is the only item, removing it will make the current mass == 0
		assertTrue(scale.getCurrentMassOnTheScale().inGrams().floatValue() == 0);
	}
}
