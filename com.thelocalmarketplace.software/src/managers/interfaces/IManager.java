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

package managers.interfaces;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import managers.enums.SessionStatus;
import observers.IObserverUseable;

public interface IManager extends IManagerNotify {
	/**
	 * This function uses any implementors {@link AbstractSelfCheckoutStation} and
	 * uses their references to attach observers.
	 * 
	 * @param machine the desired machine to observe
	 */
	void configure(AbstractSelfCheckoutStation machine);

	/**
	 * Gets the state of the manager.
	 * 
	 * @return the state
	 */
	SessionStatus getSessionState();

	/**
	 * This method simply blocks the session, should only be used internally.
	 */
	void blockSession();

	/**
	 * This method unblocks the session.
	 */
	void unblockSession();

	/**
	 * This function tests whether or not the system can be used based on the states
	 * of the components of the machine.
	 * 
	 * @see IObserverUseable#canUse()
	 * @return returns true if all the observers can be used, false otherwise
	 */
	boolean ready();
}
