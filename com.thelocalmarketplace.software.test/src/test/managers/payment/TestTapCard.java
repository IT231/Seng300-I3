// Simon Bondad, 30164301
// Liam Major 30223023

package test.managers.payment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.math.BigDecimal;

import javax.naming.OperationNotSupportedException;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardTapData;
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

public class TestTapCard {

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
	public void testPaymentTapNullCard() throws IOException {
		pm.tapCard(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSystemTapNullCard() throws IOException {
		sm.tapCard(null);
	}

	@Test
	public void testSuccessfulNotifyCardTap() throws IOException {
		Card card = CardHelper.createCard(issuer, sm.getTotalPrice().doubleValue());
		pm.notifyCardTap(card.tap());
		assertEquals(SessionStatus.PAID, sm.getSessionState());
		assertEquals(sm.getTotalPrice(), sm.getCustomerPayment());
	}

	@Test(timeout = 5000)
	public void testUnsuccessfulNotifyCardTap() throws IOException {
		Card card = CardHelper.createCard(issuer, 2);
		CardTapData data = card.tap();
		pm.notifyCardTap(card.tap());
		assertEquals(SessionStatus.NORMAL, sm.getSessionState());
		assertNotEquals(sm.getTotalPrice(), sm.getCustomerPayment());
	}

	@Test
	public void tappingInvalidCard() throws IOException {
		Card card = CardHelper.createNonIssuedCard();
		pm.tapCard(card);
		assertEquals(SessionStatus.NORMAL, sm.getSessionState());
		assertNotEquals(sm.getTotalPrice(), sm.getCustomerPayment());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNotifyNullCard() throws IOException {
		pm.notifyCardTap(null);
	}

}