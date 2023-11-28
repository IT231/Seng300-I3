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
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import utils.Pair;

public class TestPair {

	Pair<Integer, String> p;
	Integer key = 10;
	String val = "fortnite";

	@Before
	public void setup() {
		p = new Pair<Integer, String>(key, val);
	}

	@Test
	public void testGetKeyDoesntReturnValue() {
		assertNotEquals(p.getKey(), val);
	}

	@Test
	public void testGetValueDoesntReturnKey() {
		assertNotEquals(p.getValue(), key);
	}

	@Test
	public void testCanGetKey() {
		assertEquals(p.getKey(), key);
	}

	@Test
	public void testCanGetValue() {
		assertEquals(p.getValue(), val);
	}

}