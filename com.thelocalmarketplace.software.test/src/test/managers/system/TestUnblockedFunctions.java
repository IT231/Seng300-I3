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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import managers.enums.SessionStatus;
import stubbing.StubbedSystemManager;

/**
 * This tests the functions that aren't bound to state can still be called
 * without issue.
 * 
 * Basically, all the getter functions should never be blocked, with the exception of notify attendant.
 * These tests rely on the functions returning non-null values upon creation.
 */

public class TestUnblockedFunctions {
	private StubbedSystemManager sm;

	@Before
	public void setup() {
		sm = new StubbedSystemManager();
	}
	
	@Test
	public void testGetRemainingBalanceFunctionsWithoutState() {
		for (SessionStatus status : SessionStatus.values()) {
			sm.setSessionState(status);
			assertNotNull(sm.getRemainingBalance());
		}
	}
	
	@Test
	public void testGetTotalPriceFunctionsWithoutState() {
		for (SessionStatus status : SessionStatus.values()) {
			sm.setSessionState(status);
			assertNotNull(sm.getTotalPrice());
		}
	}
	
	@Test
	public void testGetCustomerPaymentFunctionsWithoutState() {
		for (SessionStatus status : SessionStatus.values()) {
			sm.setSessionState(status);
			assertNotNull(sm.getCustomerPayment());
		}
	}
	
	@Test
	public void testGetExpectedMassFunctionsWithoutState() {
		for (SessionStatus status : SessionStatus.values()) {
			sm.setSessionState(status);
			assertNotNull(sm.getExpectedMass());
		}
	}
	
	@Test
	public void testGetProductsFunctionsWithoutState() {
		for (SessionStatus status : SessionStatus.values()) {
			sm.setSessionState(status);
			assertNotNull(sm.getProducts());
		}
	}
	
	@Test
	public void testGetStateFunctionsWithoutState() {
		for (SessionStatus status : SessionStatus.values()) {
			sm.setSessionState(status);
			assertNotNull(sm.getRemainingBalance());
		}
	}
	
	@Test
	public void testIsScaleOverloadedWithoutState() {
		for (SessionStatus status : SessionStatus.values()) {
			sm.setSessionState(status);
			assertNotNull(sm.isScaleOverloaded());
		}
	}
	
	@Test
	public void testPostTransactionsWithoutState() {
		for (SessionStatus status : SessionStatus.values()) {
			sm.setSessionState(status);
			assertTrue(sm.postTransactions());
		}
	}
}
