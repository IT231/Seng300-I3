package GUIcomponents.GuiForI3;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

public class SimulationItem {
	public Item item;
	public Product product;
	
	public SimulationItem(BarcodedItem item, BarcodedProduct prod) {
		this.item = item;
		this.product = prod;
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(item.getBarcode(), prod);
	}
	
	public SimulationItem(PLUCodedItem item, PLUCodedProduct prod) {
		this.item = item;
		this.product = prod;
		ProductDatabases.PLU_PRODUCT_DATABASE.put(item.getPLUCode(), prod);
	}
}
