
// Simon Bondad, 30164301
// Liam Major 30223023
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
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.math.BigDecimal;

import javax.naming.OperationNotSupportedException;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardSwipeData;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.external.CardIssuer;

import managers.enums.SessionStatus;
import stubbing.StubbedBarcodedProduct;
import stubbing.StubbedGrid;
import stubbing.StubbedOrderManager;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;
import utils.CardHelper;

public class TestSwipeCard {

	// vars
	private StubbedPaymentManager pm;
	private StubbedSystemManager sm;
	private StubbedOrderManager om;
	private AbstractSelfCheckoutStation machine;
	private CardIssuer issuer;

	@Before
	public void setup() throws OperationNotSupportedException {
		// configuring the hardware
		StubbedStation.configure();

		// creating the hardware
		machine = new StubbedStation().machine;
		machine.plugIn(StubbedGrid.instance());
		machine.turnOn();

		// creating the stubs
		sm = new StubbedSystemManager(BigDecimal.ZERO);
		pm = sm.pmStub;
		om = sm.omStub;
		BarcodedProduct prod = new StubbedBarcodedProduct();
		issuer = CardHelper.createCardIssuer();
		sm.setIssuer(issuer);

		// configuring the machine
		om.addProduct(prod);
		sm.configure(machine);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPaymentSwipeNullCard() throws IOException {
		pm.swipeCard(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSystemSwipeNullCard() throws IOException {
		sm.swipeCard(null);
	}

	@Test
	public void testSuccessfulNotifyCardSwipe() throws IOException {
		Card card = CardHelper.createCard(issuer, sm.getTotalPrice().doubleValue());
		pm.notifyCardSwipe(card.swipe());
		assertEquals(SessionStatus.PAID, sm.getState());
		assertEquals(sm.getTotalPrice(), sm.getCustomerPayment());
	}

	@Test(timeout = 5000)
	public void testUnsuccessfulNotifyCardSwipe() throws IOException {
		Card card = CardHelper.createCard(issuer, 2);
		CardSwipeData data = card.swipe();
		pm.notifyCardSwipe(data);
		assertEquals(SessionStatus.NORMAL, sm.getState());
		assertNotEquals(sm.getTotalPrice(), sm.getCustomerPayment());
	}

	@Test
	public void swipingInvalidCard() throws IOException {
		Card card = CardHelper.createNonIssuedCard();
		pm.swipeCard(card);
		assertEquals(SessionStatus.NORMAL, sm.getState());
		assertNotEquals(sm.getTotalPrice(), sm.getCustomerPayment());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNotifyNullCard() throws IOException {
		pm.notifyCardSwipe(null);
	}

}