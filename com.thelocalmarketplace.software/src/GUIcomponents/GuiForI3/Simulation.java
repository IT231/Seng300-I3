package GUIcomponents.GuiForI3;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
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
import managers.SystemManager;
import powerutility.PowerGrid;
import utils.CardHelper;

public class Simulation {
	public static AbstractSelfCheckoutStation station;
	public static OrderManager orderManager;
	public static SystemManager systemManager;
	public static CardIssuer cardIssuer;
	public static ArrayList<SimulationItem> itemsToAdd = new ArrayList<SimulationItem>();
	
	static void start() {
		station = new SelfCheckoutStationGold();
		cardIssuer = CardHelper.createCardIssuer();
		systemManager = new SystemManager(cardIssuer, BigDecimal.ONE);
		systemManager.configure(station);
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
		BarcodedProduct appleJuiceProduct = new BarcodedProduct(appleJuiceBarcode, "Box of Apple Juice.", 3, 255);
		itemsToAdd.add(new SimulationItem(appleJuiceItem, appleJuiceProduct));
		
		// Add oranges a PLU coded item
		PriceLookUpCode orangesPLUCode = new PriceLookUpCode("1324");
		PLUCodedItem orangesItem = new PLUCodedItem(orangesPLUCode, new Mass(BigDecimal.valueOf(1000)));
		PLUCodedProduct orangesProduct = new PLUCodedProduct(orangesPLUCode, "One or more oranges.", 1);
		itemsToAdd.add(new SimulationItem(orangesItem, orangesProduct));
	}
}
