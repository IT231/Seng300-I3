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

package test.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;
import utils.DatabaseHelper;

public class TestDatabaseHelper {

	private int length = 10;

	/**
	 * I really only care that the random mass and price are greater than zero.
	 */

	@Test
	public void testRandomMassGreaterThanZero() {
		assertTrue(DatabaseHelper.createRandomMass() > 0);
	}

	@Test
	public void testRandomPriceGreaterThanZero() {
		assertTrue(DatabaseHelper.createRandomPrice() > 0);
	}

	@Test
	public void testRandomBarcodeLength() {
		Barcode barcode = DatabaseHelper.createRandomBarcode(length);

		assertEquals(barcode.digitCount(), length);
	}

	@Test
	public void testRandomBarcodeNotNull() {
		Barcode barcode = DatabaseHelper.createRandomBarcode(length);

		assertNotNull(barcode);
	}

	@Test
	public void testRandomBarcodeValidNumerals() {
		Barcode barcode = DatabaseHelper.createRandomBarcode(length);

		// basically testing for not null
		for (int i = 0; i < barcode.digitCount(); ++i) {
			assertTrue(barcode.getDigitAt(i) != null);
		}
	}

	@Test(expected = InvalidArgumentSimulationException.class)
	public void testRandomBarcodeZeroLength() {
		DatabaseHelper.createRandomBarcode(0);
	}

	@Test(expected = InvalidArgumentSimulationException.class)
	public void testRandomBarcodeLengthTooBig() {
		DatabaseHelper.createRandomBarcode(100);
	}

	@Test
	public void testRandomDescriptionNotNull() {
		String s = DatabaseHelper.createRandomDescription();

		assertNotNull(s);
	}

	@Test
	public void testRandomDescriptionLengthNotZero() {
		String s = DatabaseHelper.createRandomDescription();

		assertTrue(s.length() > 0);
	}

	@Test
	public void testRandomBarcodedItemNotNull() {
		assertNotNull(DatabaseHelper.createRandomBarcodedItem());
	}

	@Test
	public void testRandomBarcodedItemDefaultBarcodeLength() {
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();

		assertEquals(item.getBarcode().digitCount(), DatabaseHelper.DEFAULT_BARCODE_LENGTH);
	}

	@Test
	public void testRandomBarcodedItemFieldsNotNull() {
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();

		// checking for null item
		assertNotNull(DatabaseHelper.createRandomBarcodedItem());

		// checking for null fields
		assertNotNull(item.getBarcode());
		assertNotNull(item.getMass());

		// ensuring that the mass is greater than zero
		assertTrue(item.getMass().inGrams().compareTo(BigDecimal.ZERO) > 0);
	}

	@Test
	public void testRandomBarcodedItemHasProductInDatabase() {
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();

		assertTrue(ProductDatabases.BARCODED_PRODUCT_DATABASE.containsKey(item.getBarcode()));
		assertNotNull(ProductDatabases.BARCODED_PRODUCT_DATABASE.keySet().size() > 0);
	}

	@Test
	public void testRandomBarcodedItemAndProductHaveSameBarcode() {
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();
		BarcodedProduct prod = ProductDatabases.BARCODED_PRODUCT_DATABASE.get(item.getBarcode());

		assertEquals(item.getBarcode(), prod.getBarcode());
	}

}
