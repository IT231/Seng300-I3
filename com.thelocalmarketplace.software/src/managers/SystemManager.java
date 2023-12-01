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

package managers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.tdc.NoCashAvailableException;
import com.tdc.banknote.Banknote;
import com.tdc.coin.Coin;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.external.CardIssuer;

import ca.ucalgary.seng300.simulation.NullPointerSimulationException;
import managers.enums.PaymentType;
import managers.enums.ScanType;
import managers.enums.SessionStatus;
import managers.enums.StationStatus;
import managers.interfaces.IOrderManager;
import managers.interfaces.IPaymentManager;
import managers.interfaces.ISystemManager;
import utils.Pair;

/**
 * This class is meant to contain everything that is hardware related, this acts
 * as the main interface to the hardware and the underlying order management and
 * payment management systems.
 *
 * This delegates all functionality (with some exceptions) to the other manager
 * classes.
 */
public class SystemManager implements ISystemManager, IPaymentManager, IOrderManager {

	// hardware references
	protected AbstractSelfCheckoutStation machine;

	// object references

	// object ownership
	protected PaymentManager pm;
	protected OrderManager om;

	// vars
	protected SessionStatus sessionState;
	protected StationStatus stationState;
	protected CardIssuer issuer;
	protected Map<String, List<Pair<Long, Double>>> records;

	/**
	 * This object is responsible for the needs of the customer. This is how the
	 * customer is supposed to interact with the system.
	 *
	 * @param issuer   a card issuer
	 * @param leniency the scale leniency
	 * @throws IllegalArgumentException when either argument is null
	 */
	public SystemManager(CardIssuer issuer, BigDecimal leniency) {
		// checking arguments
		if (issuer == null) {
			throw new IllegalArgumentException("the card issuer cannot be null");
		}

		if (leniency == null) {
			throw new IllegalArgumentException("the leniency cannot be null");
		}

		// init vars
		setSessionState(SessionStatus.NOT_STARTED);
		enableStation();
		this.issuer = issuer; // a reference to the bank
		this.records = new HashMap<>();

		// creating the managers
		this.pm = new PaymentManager(this, issuer);
		this.om = new OrderManager(this, leniency);
	}
	
	@Override
	public void configure(AbstractSelfCheckoutStation machine) {
		// saving a reference
		this.machine = machine;

		// configuring the managers
		this.pm.configure(this.machine);
		this.om.configure(this.machine);
	}

	@Override
	public boolean ready() {
		if (!om.ready()) {
			return false;
		}
		if (!pm.ready()) {
			return false;
		}

		// all the managers are ready, returning true
		return true;
	}
	
	/**
	 * Starts the session if the station is enabled and the session is in NOT_STARTED state
	 */
	public void startSession() {
		if (getSessionState() != SessionStatus.NOT_STARTED) {
			throw new IllegalStateException("Cannot start a session that is already active.");
		}
		
		if (getStationState() == StationStatus.ENABLED) {
			setSessionState(SessionStatus.NORMAL);
		}
		// Need to update GUI to say OUT OF ORDER instead of start session if station is disabled
	}
	
	/**
	 * Sets the stationState to enabled, allowing the session to be started
	 */
	public void enableStation() {
		setStationState(StationStatus.ENABLED);
	}
	
	/**
	 * Sets the stationState to disable, disallowing the session to be started.
	 * If called during an active session the current session will be allowed to finish.
	 */
	public void disableStation() {
		setStationState(StationStatus.DISABLED);
	}

	/**
	 * This calculates the outstanding balance the customer must pay for their
	 * order.
	 *
	 * @return the amount of money still owed by the customer
	 */
	public BigDecimal getRemainingBalance() {
		return this.getTotalPrice().subtract(this.getCustomerPayment());
	}

	@Override
	public void removeItemFromOrder(Item item) throws OperationNotSupportedException {
		if (getSessionState() == SessionStatus.PAID) {
			throw new IllegalStateException("cannot remove item from state PAID");
		}

		// not restricting this function, this is used to resolve discrepancies
		this.om.removeItemFromOrder(item);
	}

	@Override
	public void insertCoin(Coin coin) {
		// not performing action if session is blocked
		if (getSessionState() != SessionStatus.NORMAL)
			throw new IllegalStateException("cannot insert coin when in a non-normal state");

		this.pm.insertCoin(coin);
	}

	@Override
	public void insertBanknote(Banknote banknote) {
		// not performing action if session is blocked
		if (getSessionState() != SessionStatus.NORMAL)
			throw new IllegalStateException("cannot insert banknote when in a non-normal state");

		this.pm.insertBanknote(banknote);
	}

	@Override
	public BigDecimal getTotalPrice() throws NullPointerSimulationException {
		return this.om.getTotalPrice();
	}

	@Override
	public BigDecimal getCustomerPayment() {
		return this.pm.getCustomerPayment();
	}

	@Override
	public void swipeCard(Card card) throws IOException {
		// not performing action if session is blocked
		if (getSessionState() != SessionStatus.NORMAL)
			throw new IllegalStateException("cannot swipe card when PAID");

		this.pm.swipeCard(card);
	}

	public boolean tenderChange() throws RuntimeException, NoCashAvailableException {
		if (getSessionState() != SessionStatus.NORMAL)
			throw new IllegalStateException("cannot tender change when in a non-normal state");

		return this.pm.tenderChange();
	}

	@Override
	public BigDecimal getExpectedMass() {
		return this.om.getExpectedMass();
	}

	@Override
	public List<Product> getProducts() throws NullPointerSimulationException {
		return this.om.getProducts();
	}

	@Override
	public void onAttendantOverride() {
		if (getSessionState() == SessionStatus.PAID) {
			throw new IllegalStateException("attendant cannot override from state PAID");
		}

		this.om.onAttendantOverride();
	}

	@Override
	public void onDoNotBagRequest(Item item) {
		this.om.onDoNotBagRequest(item);
	}

	@Override
	public void addItemToOrder(Item item, ScanType method) throws OperationNotSupportedException {
		// not performing action if session is blocked
		if (getSessionState() != SessionStatus.NORMAL)
			throw new IllegalStateException("cannot add item when in a non-normal state");

		this.om.addItemToOrder(item, method);
	}

	@Override
	public void blockSession() {
		// should be able to block from any state
		setSessionState(SessionStatus.BLOCKED);

		// notify the attendant
		notifyAttendant("Session was blocked.");
	}

	@Override
	public void unblockSession() {
		if (getSessionState() == SessionStatus.PAID) {
			throw new IllegalStateException("cannot unblock from state PAID");
		}

		setSessionState(SessionStatus.NORMAL);
	}

	protected void setSessionState(SessionStatus state) {
		if (state == null) {
			throw new IllegalArgumentException("cannot set the state of the manager to null");
		}

		this.sessionState = state;
	}

	@Override
	public SessionStatus getSessionState() {
		return sessionState;
	}
	
	protected void setStationState(StationStatus state) {
		if (state == null) {
			throw new IllegalArgumentException("cannot set the state of the manager to null");
		}

		this.stationState = state;
	}

	public StationStatus getStationState() {
		return stationState;
	}

	@Override
	public void notifyPaid() {
		if (getSessionState() == SessionStatus.BLOCKED) {
			throw new IllegalStateException("cannot set state from BLOCKED to PAID");
		}

		setSessionState(SessionStatus.PAID);
	}

	@Override
	public boolean postTransactions() {
		// starting state, the function will return true if all the transactions passed
		boolean flag = true;

		for (String number : records.keySet()) {
			// getting the list of records
			List<Pair<Long, Double>> arr = records.get(number);

			// posting them
			for (Pair<Long, Double> pair : arr) {
				if (!issuer.postTransaction(number, pair.getKey(), pair.getValue())) {
					// a transaction failed to post, returning false
					flag = false;
				}
			}
		}

		// returning the state of the posted transactions
		return flag;
	}

	@Override
	public void recordTransaction(CardData card, Long holdnumber, Double amount) {
		if (getSessionState() != SessionStatus.NORMAL) {
			throw new IllegalStateException("cannot record a transaction from a non-normal state");
		}

		// checking for null
		if (card == null) {
			throw new IllegalArgumentException("card cannot be null");
		}
		if (holdnumber == null) {
			throw new IllegalArgumentException("holdnumber cannot be null");
		}
		if (amount == null) {
			throw new IllegalArgumentException("amount cannot be null");
		}

		/// recording the transaction
		Pair<Long, Double> pair = new Pair<>(holdnumber, amount);

		// creating the list if not present
		List<Pair<Long, Double>> ar = records.getOrDefault(card.getNumber(), new ArrayList<>());

		// adding the transaction
		ar.add(pair);

		// recording the transaction
		records.put(card.getNumber(), ar);
	}

	@Override
	public void notifyAttendant(String reason) {
		System.out.printf("[ATTENDANT NOTIFY]: %s\n", reason);
	}

	@Override
	public void printReceipt(PaymentType type, Card card) {
		if (getSessionState() != SessionStatus.PAID) {
			throw new IllegalStateException("Cannot print receipt. System state is not PAID.");
		}

		this.pm.printReceipt(type, card);
	}

	public void addCustomerBags(Item bags) {
		if (getSessionState() != SessionStatus.NORMAL) {
			throw new IllegalStateException("cannot add customer bags when not in a normal state");
		}

		this.om.addCustomerBags(bags);
	}

	@Override
	public boolean isScaleOverloaded() {
		return om.isScaleOverloaded();
	}

	@Override
	public void modifyPaper(boolean hasPaper, boolean lowPaper) {
		if(lowPaper && !hasPaper) {
			notifyAttendant("Machine could not print receipt in full. Printer requires paper. Duplicate receipt needed.");
			//block the session
			blockSession();
		}else if(lowPaper) {
			notifyAttendant("The printer is low on paper.");
		}	
	}

	@Override
	public void modifyInk(boolean hasInk, boolean lowInk) {
		if(lowInk && !hasInk) {
			notifyAttendant("Machine could not print receipt in full. Printer requires ink. Duplicate receipt needed.");
			//block the session
			blockSession();
		}if(lowInk) {
			notifyAttendant("The printer is low on ink.");
		}
	}

}
