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

package test.managers;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.external.CardIssuer;

import managers.OrderManager;
import managers.PaymentManager;
import managers.SystemManager;

/**
 * This class tests how constructors behave relative to their arguments.
 */

public class TestManagerConstructorsNegative {

	private SystemManager sm;
	private CardIssuer issuer = new CardIssuer("testing", 10);
	private BigDecimal leniency = BigDecimal.ONE;
	
	@Before
	public void setup() {
		sm = new SystemManager(issuer, leniency);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPaymentManagerNullIssuer() {
		new PaymentManager(sm, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPaymentManagerNullSystemManager() {
		new PaymentManager(null, issuer);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testOrderManagerNullLeniency() {
		new OrderManager(sm, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testOrderManagerNullSystemManager() {
		new OrderManager(null, leniency);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSystemManagerNullLeniency() {
		new SystemManager(issuer, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSystemManagerNullIssuer() {
		new SystemManager(null, leniency);
	}
	
}
