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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import managers.enums.SessionStatus;
import stubbing.StubbedSystemManager;

/**
 * This tests that the functions that control state can only move between
 * certain states.
 */

public class TestTransitionStates {

	private StubbedSystemManager sm;

	@Before
	public void setup() {
		sm = new StubbedSystemManager();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCannotSetANullState() {
		sm.setState(null);
	}


	@Test
	public void testBlockSession() {
		sm.setState(SessionStatus.NORMAL);

		sm.blockSession();

		assertEquals(sm.getState(), SessionStatus.BLOCKED);
	}

	@Test
	public void testUnblockSession() {
		sm.setState(SessionStatus.BLOCKED);

		sm.unblockSession();

		assertEquals(sm.getState(), SessionStatus.NORMAL);
	}

	@Test(expected = RuntimeException.class)
	public void testCannotUnblockSessionFromPaid() {
		sm.setState(SessionStatus.PAID);

		sm.unblockSession();
	}

	@Test
	public void testNotifyPaid() {
		sm.setState(SessionStatus.NORMAL);

		sm.notifyPaid();

		assertEquals(sm.getState(), SessionStatus.PAID);
	}

	@Test(expected = RuntimeException.class)
	public void testNotifyPaidFromBlocked() {
		sm.setState(SessionStatus.BLOCKED);

		sm.notifyPaid();
	}
}
