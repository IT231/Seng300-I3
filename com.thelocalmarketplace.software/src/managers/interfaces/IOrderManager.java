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

package managers.interfaces;

import java.math.BigDecimal;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.Product;

import ca.ucalgary.seng300.simulation.NullPointerSimulationException;
import managers.enums.ScanType;

/**
 * A unified interface of what the order manager should and should not do.
 */
public interface IOrderManager extends IManager {

	/**
	 * This returns the total price of the items in the customer's order.
	 * 
	 * @return the total price
	 * @throws NullPointerSimulationException if the {@link BarcodedItem} could not
	 *                                        be found in the product database
	 */
	BigDecimal getTotalPrice() throws NullPointerSimulationException;

	/**
	 * Allows the attendant to override the session and unblock it. This also
	 * updates the weight adjustment.
	 */
	void onAttendantOverride();

	/**
	 * Allows the customer to request the item not be bagged, unblocking the
	 * session. This also updates the weight adjustment.
	 */
	void onDoNotBagRequest(Item item);

	/**
	 * Allows customer to add their own bags. The scale and weight is updated
	 * according to to new weight and adjustment.
	 * 
	 * @param bags, the bags customer wants to add
	 */
	void addCustomerBags(Item bags);

	/**
	 * Simulates adding an {@link Item} to the order.
	 * 
	 * @param item   the item to add
	 * @param method the method of scanning
	 */
	void addItemToOrder(Item item, ScanType method) throws OperationNotSupportedException;

	/**
	 * This removes an {@link Item} from the order and the bagging area.
	 * 
	 * @param item the {@link Item} to remove
	 */
	void removeItemFromOrder(Item item) throws OperationNotSupportedException;

	/**
	 * Returns the expected mass of the items the customer has scanned.
	 * 
	 * @return the sum of masses of the {@link BarcodedItem}s
	 */
	BigDecimal getExpectedMass();

	/**
	 * This returns the list of equivalent products retrieved from the product
	 * database.
	 * 
	 * @return a list of {@link BarcodedProduct}
	 * @throws NullPointerSimulationException if the {@link Barcode} could not be
	 *                                        found in the product database
	 */
	List<Product> getProducts() throws NullPointerSimulationException;

	/**
	 * This function returns an {@link IElectronicScale} object if any of the scales
	 * are overloaded.
	 * 
	 * @return an {@link IElectronicScale} if a scale is overloaded, {@code null}
	 *         otherwise
	 */
	boolean isScaleOverloaded();
}
