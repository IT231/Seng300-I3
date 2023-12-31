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

package utils;

import java.util.Random;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;

/**
 * all this does is populate the database with randomly generated products
 */
public class DatabaseHelper {

	private static String[] names = { "Milk", "Bread", "Eggs", "Cheese", "Sugar", "Cereal", "Beef", "Abacus", };

	private static String[] adjectives = { "Inedible", "Tasty", "Pasty", "Wooden", "Super", "Proud", "Big", "Small",
			"Puny", "Dandriff-infused", "Feline", "Canine" };

	private static Random random = new Random();

	public static final int DEFAULT_BARCODE_LENGTH = 10;

	/**
	 * Creates a randomized mass between 0.1 (inclusive) and 1.1 (exclusive), scaled
	 * by a factor of 25.
	 * 
	 * @return the randomized mass
	 */
	public static double createRandomMass() {
		return DatabaseHelper.random.nextDouble(0.1, 1.1) * 25;
	}

	/**
	 * Creates a randomized price between 1 (inclusive) and 100 (exclusive).
	 * 
	 * @return the randomized price
	 */
	public static long createRandomPrice() {
		return DatabaseHelper.random.nextLong(1, 100);
	}

	/**
	 * This creates a barcode of length {@code digits} for anything that needs a
	 * {@link Barcode}.
	 * 
	 * @param digits the number of digits for the {@link Barcode}
	 * @return a randomized {@link Barcode}
	 */
	public static Barcode createRandomBarcode(int digits) {
		// testing length
		if (digits < 1 || digits > 48) {
			throw new InvalidArgumentSimulationException("invalid number of digits, between 1 and 48 inclusive");
		}

		// creating a list
		Numeral[] code = new Numeral[digits];

		// creating as many digits that are put in
		for (int i = 0; i < digits; ++i) {
			byte num = (byte) (DatabaseHelper.random.nextInt(10));

			code[i] = Numeral.valueOf(num);
		}

		return new Barcode(code);
	}

	/**
	 * Creates a randomized description for a {@link Product}.
	 * 
	 * @return a description
	 */
	public static String createRandomDescription() {
		String name;

		name = adjectives[DatabaseHelper.random.nextInt(DatabaseHelper.adjectives.length)];
		name += " ";
		name += names[DatabaseHelper.random.nextInt(DatabaseHelper.names.length)];

		return name;
	}

	/**
	 * <p>
	 * Creates a {@link BarcodedItem} with a randomized mass and barcode. This
	 * method also creates a {@link BarcodedProduct} with a randomized barcode,
	 * description, price and mass.
	 * </p>
	 * <p>
	 * This method guarantees that the {@link Barcode}s of both item and product are
	 * the same and that they are put into respective databases in {@link Database}.
	 * </p>
	 * 
	 * @param length how many digits to generate for the {@link Barcode}
	 * @return the {@link BarcodedItem}
	 */
	public static BarcodedItem createRandomBarcodedItem(int length) {
		// creating the barcode
		Barcode barcode = DatabaseHelper.createRandomBarcode(length);
		double mass = DatabaseHelper.createRandomMass();

		// need to create the item
		BarcodedItem item = new BarcodedItem(barcode, new Mass(mass));

		// need to create the corresponding product
		BarcodedProduct prod = new BarcodedProduct(barcode, DatabaseHelper.createRandomDescription(),
				DatabaseHelper.createRandomPrice(), mass);

		// add both to database
		ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, prod);

		// return item to caller
		return item;
	}

	/**
	 * <p>
	 * Creates a {@link BarcodedItem} with a randomized mass and barcode. This
	 * method also creates a {@link BarcodedProduct} with a randomized barcode,
	 * description, price and mass.
	 * </p>
	 * <p>
	 * This method guarantees that the {@link Barcode}s of both item and product are
	 * the same and that they are put into respective databases in {@link Database}.
	 * </p>
	 * 
	 * @return the {@link BarcodedItem}
	 */
	public static BarcodedItem createRandomBarcodedItem() {
		return DatabaseHelper.createRandomBarcodedItem(DEFAULT_BARCODE_LENGTH);
	}

}
