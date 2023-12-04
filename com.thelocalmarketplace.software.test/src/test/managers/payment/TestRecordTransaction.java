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

package test.managers.payment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.card.Card.CardData;

import stubbing.StubbedCardData;
import stubbing.StubbedSystemManager;
import utils.Pair;

public class TestRecordTransaction {
	// vars
	private StubbedSystemManager sm;

	private CardData card;
	private Long hn;
	private Double a;

	@Before
	public void setup() {
		// creating the stubs
		sm = new StubbedSystemManager();

		card = new StubbedCardData();
		hn = (long) 1;
		a = (double) 1;
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRecordTransactionNullCard() {
		sm.recordTransaction(null, hn, a);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRecordTransactionNullHoldNumber() {
		sm.recordTransaction(card, null, a);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRecordTransactionNullAmount() {
		sm.recordTransaction(card, hn, null);
	}

	@Test
	public void tesTransactionPresent() {
		sm.recordTransaction(card, hn, a);

		// getting the records
		Map<String, List<Pair<Long, Double>>> records = sm.getRecords();

		// asserting
		assertTrue(records.size() == 1);
		
		List<Pair<Long, Double>> transactions = records.get(card.getNumber());
		
		assertNotNull(transactions);
		assertTrue(transactions.size() == 1);
		
		// getting pair of values
		Pair<Long, Double> pair = transactions.get(0);
		
		// asserting the pair
		assertNotNull(pair);
		
		// asserting values
		Long key = pair.getKey();
		Double val = pair.getValue();
		
		assertNotNull(key);
		assertNotNull(val);
		assertEquals(key, hn);
		assertEquals(val, a);
	}
}
