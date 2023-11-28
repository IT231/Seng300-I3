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

package stubbing;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.jjjwelectronics.card.Card.CardData;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.external.CardIssuer;

import managers.SystemManager;
import managers.enums.SessionStatus;
import utils.Pair;

public class StubbedSystemManager extends SystemManager {

	public static final CardIssuer issuer = new CardIssuer("testing", 10);
	public static final BigDecimal leniency = BigDecimal.ZERO;

	public StubbedPaymentManager pmStub;
	public StubbedOrderManager omStub;
	private String attendantNotification;

	public boolean notifyAttendantCalled;
	public boolean blockSessionCalled;
	public boolean unblockSessionCalled;
	public boolean getStateCalled;
	public boolean recordTransactionCalled;

	public StubbedSystemManager() {
		this(issuer, leniency);
	}

	public StubbedSystemManager(CardIssuer i) {
		this(i, leniency);
	}

	public StubbedSystemManager(BigDecimal l) {
		this(issuer, l);
	}

	public StubbedSystemManager(CardIssuer i, BigDecimal l) {
		super(i, l);

		// creating stubbed managers
		omStub = new StubbedOrderManager(this, l);
		pmStub = new StubbedPaymentManager(this, i);

		// injecting stubbed managers for testing purposes
		this.om = omStub;
		this.pm = pmStub;

		// variables to track if a function was called or not
		notifyAttendantCalled = false;
		blockSessionCalled = false;
		unblockSessionCalled = false;
		getStateCalled = false;
		recordTransactionCalled = false;
	}

	public void setIssuer(CardIssuer i) {
		super.issuer = i;
		pmStub.setIssuer(i);
	}

	public CardIssuer getIssuer() {
		return super.issuer;
	}

	public AbstractSelfCheckoutStation getMachine() {
		return machine;
	}

	@Override
	public void notifyAttendant(String reason) {
		notifyAttendantCalled = true;
		this.attendantNotification = reason;
		super.notifyAttendant(reason);
	}

	@Override
	public void blockSession() {
		blockSessionCalled = true;
		super.blockSession();
	}

	@Override
	public void unblockSession() {
		unblockSessionCalled = true;
		super.unblockSession();
	}

	@Override
	public SessionStatus getState() {
		getStateCalled = true;
		return super.getState();
	}

	public String getAttendantNotification() {
		return this.attendantNotification;
	}

	@Override
	public void setState(SessionStatus s) {
		super.setState(s);
	}

	public Map<String, List<Pair<Long, Double>>> getRecords() {
		return super.records;
	}

	@Override
	public void recordTransaction(CardData card, Long holdnumber, Double amount) {
		recordTransactionCalled = true;
		super.recordTransaction(card, holdnumber, amount);
	}
}
