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

package test.managers.system;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import managers.enums.SessionStatus;
import stubbing.StubbedSystemManager;

/**
 * This file tests that functions that should be delegated to a certain manager
 * actually do exactly that.
 */

public class TestFunctionDelegation {
	private StubbedSystemManager sm;

	@Before
	public void setup() {
		sm = new StubbedSystemManager();
	}

	@Test
	public void testInsertCoinCallsPaymentManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.insertCoin(null);
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.pmStub.insertCoinCalled);
	}

	@Test
	public void testInsertBanknoteCallsPaymentManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.insertBanknote(null);
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.pmStub.insertBanknoteCalled);
	}

	@Test
	public void testTenderChangeCallsPaymentManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.tenderChange();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.pmStub.tenderChangeCalled);
	}

	@Test
	public void testAddItemToOrderCallsOrderManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.addItemToOrder(null, null);
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.omStub.addItemToOrderCalled);
	}

	@Test
	public void testAddCustomerBagsCallsOrderManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.addCustomerBags(null);
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.omStub.addCustomerBagsCalled);
	}

	@Test
	public void testGetTotalPriceCallsOrderManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.getTotalPrice();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.omStub.getTotalPriceCalled);
	}

	@Test
	public void testGetCustomerPaymentCallsPaymentManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.getCustomerPayment();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.pmStub.getCustomerPaymentCalled);
	}

	@Test
	public void testGetProductsCallsOrderManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.getProducts();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.omStub.getProductsCalled);
	}

	@Test
	public void testGetExpectedMassCallsOrderManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.getExpectedMass();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.omStub.getExpectedMassCalled);
	}

	@Test
	public void testOnAttendantOverrideCallsOrderManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.onAttendantOverride();
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.omStub.onAttendantOverrideCalled);
	}

	@Test
	public void testOnDoNotBagRequestCallsOrderManager() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.onDoNotBagRequest(null);
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.omStub.onDoNotBagRequestCalled);
	}
	
	@Test
	public void testRemoveItemFromOrderCallsOrderManagerWhenNormal() {
		sm.setState(SessionStatus.NORMAL);

		try {
			sm.removeItemFromOrder(null);
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.omStub.removeItemFromOrderCalled);
	}
	
	@Test
	public void testRemoveItemFromOrderCallsOrderManagerWhenBlocked() {
		sm.setState(SessionStatus.BLOCKED);

		try {
			sm.removeItemFromOrder(null);
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.omStub.removeItemFromOrderCalled);
	}
	
	@Test
	public void testPrintReceiptCallsPaymentManager() {
		sm.setState(SessionStatus.PAID);

		try {
			sm.printReceipt(null, null);
		} catch (Exception e) {
			// do nothing
		}

		assertTrue(sm.pmStub.printReceiptCalled);
	}
}
