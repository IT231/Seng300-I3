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

import java.io.IOException;

import javax.naming.OperationNotSupportedException;

import org.junit.Before;
import org.junit.Test;

import com.tdc.NoCashAvailableException;

import managers.enums.SessionStatus;
import stubbing.StubbedSystemManager;

/**
 * This tests that certain functions cannot execute from certain states.
 */

public class TestBlockedFunctions {
	// TODO implement tests for swiping cards

	private StubbedSystemManager sm;

	@Before
	public void setup() {
		sm = new StubbedSystemManager();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testSwipeCardWhenBlocked() throws IOException {
		sm.setState(SessionStatus.BLOCKED);

		sm.swipeCard(null);
	}

	@Test(expected = IllegalStateException.class)
	public void testSwipeCardWhenPaid() throws IOException {
		sm.setState(SessionStatus.PAID);

		sm.swipeCard(null);
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotInsertCoinWhenBlocked() {
		sm.setState(SessionStatus.BLOCKED);

		sm.insertCoin(null);
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotInsertCoinWhenPaid() {
		sm.setState(SessionStatus.PAID);

		sm.insertCoin(null);
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotInsertBanknoteWhenBlocked() {
		sm.setState(SessionStatus.BLOCKED);

		sm.insertBanknote(null);
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotInsertBanknoteWhenPaid() {
		sm.setState(SessionStatus.PAID);

		sm.insertBanknote(null);
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotTenderChangeWhenBlocked() throws RuntimeException, NoCashAvailableException {
		sm.setState(SessionStatus.BLOCKED);

		sm.tenderChange();
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotTenderChangeWhenPaid() throws RuntimeException, NoCashAvailableException {
		sm.setState(SessionStatus.PAID);

		sm.tenderChange();
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotAddItemWhenBlocked() throws OperationNotSupportedException {
		sm.setState(SessionStatus.BLOCKED);

		sm.addItemToOrder(null, null);
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotAddItemWhenPaid() throws OperationNotSupportedException {
		sm.setState(SessionStatus.PAID);

		sm.addItemToOrder(null, null);
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotRecordTransactionFromBlocked() {
		sm.setState(SessionStatus.BLOCKED);

		sm.recordTransaction(null, null, null);
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotRecordTransactionFromPaid() {
		sm.setState(SessionStatus.PAID);

		sm.recordTransaction(null, null, null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testCannotAddBagsWhenBlocked() {
		sm.setState(SessionStatus.BLOCKED);

		sm.addCustomerBags(null);
	}

	@Test(expected = IllegalStateException.class)
	public void testCannotAddBagsWhenPaid() {
		sm.setState(SessionStatus.PAID);

		sm.addCustomerBags(null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testAttendantCannotOverrideWhenPaid() {
		sm.setState(SessionStatus.PAID);

		sm.onAttendantOverride();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testCannotRemoveItemWhenPaid() throws OperationNotSupportedException {
		sm.setState(SessionStatus.PAID);

		sm.removeItemFromOrder(null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testCannotPrintReceiptWhenBlocked() {
		sm.setState(SessionStatus.BLOCKED);

		sm.printReceipt(null, null);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testCannotPrintReceiptWhenNormal() {
		sm.setState(SessionStatus.NORMAL);

		sm.printReceipt(null, null);
	}

}
