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

package managers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

import managers.enums.ScanType;
import managers.enums.SessionStatus;
import managers.interfaces.IOrderManager;
import managers.interfaces.IOrderManagerNotify;
import observers.order.BarcodeScannerObserver;
import observers.order.ScaleObserver;

public class OrderManager implements IOrderManager, IOrderManagerNotify {

	// hardware references
	protected AbstractSelfCheckoutStation machine;

	// object references
	protected SystemManager sm;

	// object ownership
	protected BarcodeScannerObserver main_bso;
	protected BarcodeScannerObserver handheld_bso;
	protected ScaleObserver baggingarea_so;

	// vars
	protected BigDecimal leniency;
	protected BigDecimal adjustment = BigDecimal.ZERO;
	protected BigDecimal actualWeight = BigDecimal.ZERO;
	protected boolean noBaggingRequested = false;
	protected List<Product> products = new ArrayList<Product>();
	// contains the total mass of products that require their specific mass for price/expected weight calculations
	protected HashMap<Product, Mass> productsPricedByMass = new HashMap<Product, Mass>(); 
	protected List<IOrderManagerNotify> listeners = new ArrayList<IOrderManagerNotify>();
	protected List<IElectronicScale> overloadedScales = new ArrayList<IElectronicScale>();

	/**
	 * This controls everything relating to adding and removing items from a
	 * customer's order.
	 *
	 * @param sm       a reference to the parent {@link SystemManager} object
	 * @param leniency the leniency (tolerance) of the {@link ScaleObserver}s
	 * @throws IllegalArgumentException when either argument is null
	 */
	public OrderManager(SystemManager sm, BigDecimal leniency) {
		// checking arguments
		if (sm == null) {
			throw new IllegalArgumentException("the system manager cannot be null");
		}

		if (leniency == null) {
			throw new IllegalArgumentException("the leniency cannot be null");
		}

		// copying arguments
		this.sm = sm;
		this.leniency = leniency;
	}

	@Override
	public void configure(AbstractSelfCheckoutStation machine) {
		// saving reference
		this.machine = machine;

		// passing references, because nothing actually notifies the observers of the
		// machine itself EVER
		baggingarea_so = new ScaleObserver(this, machine.getBaggingArea());
		main_bso = new BarcodeScannerObserver(this, machine.getMainScanner());
		handheld_bso = new BarcodeScannerObserver(this, machine.getHandheldScanner());
	}

	@Override
	public boolean ready() {
		if (!baggingarea_so.canUse()) {
			return false;
		}
		if (!main_bso.canUse()) {
			return false;
		}
		if (!handheld_bso.canUse()) {
			return false;
		}

		// all the observers are ready, return true
		return true;
	}

	@Override
	public void notifyScaleOverload(IElectronicScale scale, boolean state) {
		if (state) {
			// the scale was overloaded
			overloadedScales.add(scale);
			blockSession();
			notifyAttendant("a scale was overloaded");
		} else {
			// the scale is out of overload
			overloadedScales.remove(scale);
			unblockSession();
			notifyAttendant("a scale is out of overload");
		}
	}

	@Override
	public void notifyBarcodeScanned(IBarcodeScanner scanner, Barcode barcode) {
		// checking for null
		if (barcode == null)
			throw new IllegalArgumentException("invalid barcode was scanned");

		// getting the item
		BarcodedProduct prod = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(barcode);

		// checking for null
		if (prod == null)
			throw new IllegalArgumentException("barcode doesn't match any known item");

		// adding the item to the order
		products.add(prod);
	}

	/**
	 * Returns the total mass of the order.
	 *
	 * @return the total mass in grams.
	 */
	public BigDecimal getExpectedMass() {
		BigDecimal total = BigDecimal.ZERO;
		List<Product> totalMassAdded = new ArrayList<Product>(); // may check the product multiple times but only need to add mass once

		for (Product i : this.products) {
			// checking for null
			if (i == null) {
				throw new IllegalArgumentException("tried to calculate mass of a null Product");
			}

			// if its a barcodedproduct, we can calculate mass directly
			if (i instanceof BarcodedProduct) {
				BarcodedProduct temp = (BarcodedProduct) i;
				total = total.add(new BigDecimal(temp.getExpectedWeight()));
				continue;
			}
			
			// if its a PLUCodedProduct, we get the mass from productsPricedByMass hashmap
			if (i instanceof PLUCodedProduct && !totalMassAdded.contains(i)) {
				total = total.add(productsPricedByMass.get(i).inGrams());
				totalMassAdded.add(i);
				continue;
			}

			throw new UnsupportedOperationException("cannot calculate mass of a product without a mass");
		}

		return total;
	}

	/**
	 * Gets the current weight adjustment.
	 *
	 * @return the weight adjustment
	 */
	protected BigDecimal getWeightAdjustment() {
		return this.adjustment;
	}

	/**
	 * Sets the weight adjustment.
	 *
	 * @param a the weight adjustment
	 */
	protected void setWeightAdjustment(BigDecimal a) {
		this.adjustment = a;
	}

	/**
	 * Gets the total price of the order.
	 * Operates under the following assumptions:
	 * 	1. BarcodedItems are always sold per unit
	 *  2. PLUCodedItems are always sold by mass
	 * If either of the assumptions are untrue in the future, this method will need to be updated.
	 */
	@Override
	public BigDecimal getTotalPrice() throws IllegalArgumentException {
		BigDecimal total = BigDecimal.ZERO;
		List<Product> totalPriceAdded = new ArrayList<Product>(); // may check the product multiple times but only need to add price once
		
		for (Product i : this.products) {
			// checking for null
			if (i == null) {
				throw new IllegalArgumentException("tried to calculate mass of a null Product");
			}

			// calculating the price for a barcodeditem
			if (i instanceof BarcodedProduct && i.isPerUnit()) {
				// barcodeditems are always sold per unit, therefore we can just add the price
				// directly
				total = total.add(new BigDecimal(i.getPrice()));
				continue;
			}
			
			// calculating the price of items which are priced by mass
			if (i instanceof PLUCodedProduct && !i.isPerUnit() && !totalPriceAdded.contains(i)) {
				if (!productsPricedByMass.containsKey(i)) {
					throw new IllegalArgumentException("An item priced per mass is added to products but not added to productsPriceByMass.");
				}
				
				Mass totalItemMass = productsPricedByMass.get(i);
				BigDecimal pricePerKilogram = new BigDecimal(i.getPrice());
				// totalItemPrice = (totalMassInGrams / 1000) * pricePerKilogram
				BigDecimal totalItemPrice = totalItemMass.inGrams().divide(new BigDecimal(1000)).multiply(pricePerKilogram);
				total = total.add(totalItemPrice);
				totalPriceAdded.add(i);
				continue;
			}

			// Temporary exception, while item types other than Barcode are unsupported.
			throw new UnsupportedOperationException();
		}

		// returning the price
		return total;
	}

	@Override
	public void addItemToOrder(Item item, ScanType method) {
		// checking for null
		if (item == null) {
			throw new IllegalArgumentException("tried to scan a null item");
		}
		if (method == null) {
			throw new IllegalArgumentException("a null scanning type was passed");
		}

		// figuring out how to scan the item
		if (item instanceof BarcodedItem) {
			this.addItemToOrder((BarcodedItem) item, method);
		}

		if (item instanceof PLUCodedItem) {
			this.addItemToOrder((PLUCodedItem) item);
		}

		// check if customer wants to bag item (bulky item handler extension)
		if (!noBaggingRequested) {
			this.machine.getBaggingArea().addAnItem(item);
		}

		// reset bagging request tracker for the next item
		noBaggingRequested = false;
	}

	/**
	 * Simulates adding an {@link BarcodedItem} to the order.
	 *
	 * @param item   the item to add
	 * @param method the method of scanning
	 */
	protected void addItemToOrder(BarcodedItem item, ScanType method) {
		switch (method) {
		case MAIN:
			this.machine.getMainScanner().scan(item);
			break;
		case HANDHELD:
			this.machine.getHandheldScanner().scan(item);
			break;
		}

	}

	/**
	 * Simulates adding an {@link PLUCodedItem} to the order.
	 *
	 * @param item   the item to add
	 * @param method the method of scanning
	 */
	protected void addItemToOrder(PLUCodedItem item) {
		// checking for null
		if (item == null)
			throw new IllegalArgumentException("Invalid PLU Coded Item.");

		// getting the item
		PLUCodedProduct prod = ProductDatabases.PLU_PRODUCT_DATABASE.get(item.getPLUCode());

		// checking for null
		if (prod == null)
			throw new IllegalArgumentException("PLU code doesn't match any known item.");

		blockSession();
		// adding the item to the order
		products.add(prod);
		
		// adding the product as priced by mass if specified in the product database
		if (!prod.isPerUnit()) {
			if (productsPricedByMass.containsKey(prod)) {
				Mass currentTotalMass = productsPricedByMass.get(prod);
				productsPricedByMass.put(prod, currentTotalMass.sum(item.getMass()));
			} else {
				productsPricedByMass.put(prod, item.getMass());
			}
		}
	}

	/**
	 * Removes the specified item from the order, purging it from the list. Informs
	 * all listeners of this event. If the item cannot be found, or is null, then
	 * customer is displayed an error message.
	 *
	 * @throws OperationNotSupportedException
	 */
	@Override
	public void removeItemFromOrder(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("tried to remove a null item from the bagging area");
		}

		if (item instanceof BarcodedItem) {
			this.removeItemFromOrder((BarcodedItem) item);
			return;
		}

		if (item instanceof PLUCodedItem) {
			this.removeItemFromOrder((PLUCodedItem) item);
			return;
		}

		// removing the item from the bagging area
		// this function needs to be here to work with the bags too heavy use case
		this.machine.getBaggingArea().removeAnItem(item);
	}

	/**
	 * This removes a {@link BarcodedItem} from the order and the bagging area.
	 *
	 * @param item the {@link BarcodedItem} to remove
	 */
	protected void removeItemFromOrder(BarcodedItem item) {
		// getting the product
		BarcodedProduct prod = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(item.getBarcode());

		// checking if the item is actually in the order
		if (!this.products.contains(prod)) {
			throw new IllegalArgumentException("tried to remove item not in the order");
		}

		blockSession();
		
		// removing
		if (this.products.remove(prod)) {
			// TODO: nothing listens for this event (yet)
			for (IOrderManagerNotify listener : listeners) {
				listener.onItemRemovedFromOrder(item);
			}

		}
	}

	/**
	 * This removes a {@link PLUCodedItem} from the order and the bagging area.
	 *
	 * @param item the {@link PLUCodedItem} to remove
	 */
	protected void removeItemFromOrder(PLUCodedItem item) {
		// getting the product
		PLUCodedProduct prod = ProductDatabases.PLU_PRODUCT_DATABASE.get(item.getPLUCode());

		// checking if the item is actually in the order
		if (!this.products.contains(prod)) {
			throw new IllegalArgumentException("tried to remove item not in the order");
		}
		
		blockSession();
		
		// Reduce the total mass of the product in the productsPircedByMass hashmap if
		// the product is priced by mass
		if (!prod.isPerUnit()) {
			if (productsPricedByMass.containsKey(prod)) {
				// check if we need to reduce the mass or remove the key
				if (item.getMass().compareTo(productsPricedByMass.get(prod)) == -1) {
					// mass being removed is less than the total mass of the hashmap value
					Mass massBeingRemoved = item.getMass();
					productsPricedByMass.put(prod, productsPricedByMass.get(prod).difference(massBeingRemoved).abs());
				} else {
					// mass being removed is greater than or equal to the total mass of the hashmap value
					productsPricedByMass.remove(prod);
				}
			} else {
				throw new IllegalArgumentException("tried to remove item which is not in the productPricedByMass hashmap.");
			}
		}
		
		// removing
		if (this.products.remove(prod)) {
			// TODO: nothing listens for this event (yet)
			for (IOrderManagerNotify listener : listeners) {
				listener.onItemRemovedFromOrder(item);
			}

		}
	}

	/**
	 * This method handles a customer's request to add their own bags. The system
	 * gets the mass of the bags and updates the system adjustment and weight
	 * accordingly.
	 *
	 * @param bags the bag that is going to be added
	 * @throws IllegalArgumentException
	 */
	@Override
	public void addCustomerBags(Item bags) throws IllegalArgumentException {
		// Get the weight of just the bags
		BigDecimal bagWeight = bags.getMass().inGrams();

		// Check if the weight of the bags are valid (greater than 0)
		if (bagWeight.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("No valid weight is detected for bags.");
		}

		// Update adjustment for weight of bag
		this.adjustment = this.adjustment.add(bagWeight);

		// Placing the bags in the bagging area
		this.machine.getBaggingArea().addAnItem(bags);
	}

	@Override
	public List<Product> getProducts() throws IllegalArgumentException {
		return products;
	}

	@Override
	public void onItemRemovedFromOrder(Item item) {
		// Note: Do Not Use! OrderManager calls this for others!
	}

	@Override
	public SessionStatus getState() {
		return sm.getState();
	}

	@Override
	public void onAttendantOverride() {
		// updating the mass
		adjustment = this.getExpectedMass().subtract(actualWeight);

		// unblocking the session
		unblockSession();
	}

	@Override
	public void notifyAttendant(String reason) {
		sm.notifyAttendant(reason);
	}

	/**
	 * This method handles a customer's request to skip bagging for a specific item.
	 * It adjusts the expected weight and updates the weight adjustment, blocks the
	 * session, and notifies the attendant.
	 *
	 * @param item the item for which bagging is skipped
	 */
	@Override
	public void onDoNotBagRequest(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("a null item was requested not to be bagged");
		}

		// Set the flag for no bagging
		this.noBaggingRequested = true;

		switch (getState()) {
		case NORMAL:
			// Adjust the overall weight adjustment
			adjustment = this.adjustment.add(item.getMass().inGrams());

			// Notify Attendant about the request
			notifyAttendant("do not bag request was received");
			break;
		default:
			// Do nothing in other states
			break;
		}
	}

	@Override
	public void notifyMassChanged(ElectronicScaleListener scale, BigDecimal mass) {
		// checking for null
		if (mass == null)
			throw new IllegalArgumentException("null mass was sent to OrderManager");

		// saving the mass
		this.actualWeight = mass;

		/**
		 * calculating the magnitude of the difference, the expected weight should be
		 * greater than the expected weight.
		 */
		BigDecimal expected = getExpectedMass().subtract(getWeightAdjustment());
		BigDecimal actual = actualWeight;
		BigDecimal difference = expected.subtract(actual).abs();

		// checking the weight difference
		checkWeightDifference(difference);
	}

	/**
	 * This separation is just to test resolving weight discrepancies.
	 */
	protected void checkWeightDifference(BigDecimal difference) {
		// testing whether or not to block or unblock the session
		switch (getState()) {
		case NORMAL:
			// blocking the session due to a discrepancy
			if (difference.compareTo(leniency) > 0) {
				blockSession();
			}
			break;
		case BLOCKED:
			// unblocking the session
			if (difference.compareTo(leniency) <= 0) {
				unblockSession();
			}
			break;
		case PAID:
			throw new IllegalStateException("cannot check weight difference when in state PAID");
		}
	}

	@Override
	public void blockSession() {
		sm.blockSession();
	}

	public void registerListener(IOrderManagerNotify listener) {
		this.listeners.add(listener);
	}

	@Override
	public void unblockSession() {
		sm.unblockSession();
	}

	@Override
	public boolean isScaleOverloaded() {
		return overloadedScales.size() > 0;
	}

}
