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

package GUIcomponents.GuiForI3;

import java.math.BigDecimal;
import java.util.ArrayList;



import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.PriceLookUpCode;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.hardware.external.CardIssuer;
import managers.OrderManager;
import managers.PaymentManager;
import managers.SystemManager;
import powerutility.PowerGrid;
import utils.CardHelper;

public class Simulation {
	public static AbstractSelfCheckoutStation station;
	public static OrderManager orderManager;
	public static SystemManager systemManager;
	public static CardIssuer cardIssuer;
	public static PaymentManager payman;
	public static ArrayList<SimulationItem> itemsToAdd = new ArrayList<SimulationItem>();
	//public static Card card;
	
	static void start() {
		station = new SelfCheckoutStationGold();
		cardIssuer = CardHelper.createCardIssuer();
		systemManager = new SystemManager(cardIssuer, BigDecimal.ONE);
		systemManager.configure(station);
		payman = new PaymentManager(systemManager, cardIssuer);
		payman.configure(station);
		orderManager = new OrderManager(systemManager, BigDecimal.ONE);
		orderManager.configure(station);
	//	card = new Card("credit", "111111111", "Bob Bob", "123", "1234", true, true);
		PowerGrid.engageUninterruptiblePowerSource();

		// plug in and turn on the machine
		station.plugIn(PowerGrid.instance());

		// turning off then on because some observers won't get their references because
		// of Walker
		station.turnOff();
		station.turnOn();
		createSimulationItems();
	}
	
	public static void createSimulationItems() {
		
		// Add Apple Juice a barcoded item
		Numeral[] appleJuicebBrcodeDigits = {Numeral.one, Numeral.five, Numeral.seven, Numeral.three};
		Barcode appleJuiceBarcode = new Barcode(appleJuicebBrcodeDigits);
		BarcodedItem appleJuiceItem = new BarcodedItem(appleJuiceBarcode, new Mass(BigDecimal.valueOf(255)));
		BarcodedProduct appleJuiceProduct = new BarcodedProduct(appleJuiceBarcode, "Apple Juice.", 3, 255);
		itemsToAdd.add(new SimulationItem(appleJuiceItem, appleJuiceProduct));
		
		// Add oranges a PLU coded item
		// since plu is not added making it a parcoded item so changing this code to barcode
		//PriceLookUpCode orangesPLUCode = new PriceLookUpCode("1324");
		//PLUCodedItem orangesItem = new PLUCodedItem(orangesPLUCode, new Mass(BigDecimal.valueOf(1000)));
		//PLUCodedProduct orangesProduct = new PLUCodedProduct(orangesPLUCode, "One or more oranges.", 1);
		//itemsToAdd.add(new SimulationItem(orangesItem, orangesProduct));
		//
		Numeral[] orangeJuicebBrcodeDigits = {Numeral.one, Numeral.three, Numeral.two, Numeral.four};
		Barcode orangeJuiceBarcode = new Barcode(orangeJuicebBrcodeDigits);
		BarcodedItem orangeJuiceItem = new BarcodedItem(orangeJuiceBarcode, new Mass(BigDecimal.valueOf(255)));
		BarcodedProduct orangeJuiceProduct = new BarcodedProduct(orangeJuiceBarcode, "orange Juice.", 3, 255);
		itemsToAdd.add(new SimulationItem(orangeJuiceItem, orangeJuiceProduct));
	}
	
	//public Card cardgetter() {
	//	return card;
		
	//}
}
