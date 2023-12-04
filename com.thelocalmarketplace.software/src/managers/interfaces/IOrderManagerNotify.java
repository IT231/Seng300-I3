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

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.IBarcodeScanner;

/**
 * This interface is used by any object that {@link IOrderManager} owns, this
 * exists for the child objects to communicate with their parent object without
 * the super class SystemManager being involved does it have to implement these
 * methods
 */
public interface IOrderManagerNotify {

	/**
	 * This method notifies the {@link IOrderManager} that a child object has
	 * scanned a barcode.
	 * 
	 * @param barcode the scanned barcode
	 */
	void notifyBarcodeScanned(IBarcodeScanner scanner, Barcode barcode);

	/**
	 * This method notifies the {@link IOrderManager} that a child object has
	 * changed status, indicating a potential weight discrepancy.
	 * 
	 * @param scale  the scale that announced the event
	 * @param status the status of the scale
	 */
	void notifyMassChanged(ElectronicScaleListener scale, BigDecimal mass);

	/**
	 * An event announcing that an item has been removed from the OrderManager.
	 * 
	 * @param item The item in question.
	 */
	void onItemRemovedFromOrder(Item item);

	/**
	 * Notifies the manager of a scale overload of a scale.
	 * 
	 * @param scale the scale that's overloaded
	 * @param state the state of the overload, true if overloaded, false otherwise
	 */
	void notifyScaleOverload(IElectronicScale scale, boolean state);

}
