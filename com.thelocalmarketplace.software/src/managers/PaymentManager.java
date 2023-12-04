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

import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.jjjwelectronics.card.Card.CardInsertData;
import com.jjjwelectronics.card.Card.CardSwipeData;
import com.jjjwelectronics.card.Card.CardTapData;
import com.jjjwelectronics.card.InvalidPINException;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.NoCashAvailableException;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.IBanknoteDispenser;
import com.tdc.coin.Coin;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.external.CardIssuer;

import managers.enums.PaymentType;
import managers.enums.SessionStatus;
import managers.interfaces.IPaymentManager;
import managers.interfaces.IPaymentManagerNotify;
import observers.payment.BanknoteCollector;
import observers.payment.CardReaderObserver;
import observers.payment.CoinCollector;
import observers.payment.ReceiptPrinterObserver;

public class PaymentManager implements IPaymentManager, IPaymentManagerNotify {

	// hardware references
	protected AbstractSelfCheckoutStation machine;

	// object references
	protected SystemManager sm;
	protected CardIssuer issuer;

	// object ownership
	protected CoinCollector cc;
	protected BanknoteCollector bc;
	protected CardReaderObserver cro;
	protected ReceiptPrinterObserver rpls;

	// vars
	protected BigDecimal payment = BigDecimal.ZERO;
	protected String signature;
	protected String pin;
	protected boolean hasPaper = true;
	protected boolean hasInk = true;
	protected boolean lowPaper = false;
	protected boolean lowInk = false;
	private boolean canPrint = true;
	private String membershipNum = null;


	/**
	 * This controls everything relating to customer payment.
	 * 
	 * @param sm     a reference to the parent {@link SystemManager} object
	 * @param issuer a card issuer
	 * @throws IllegalArgumentException when either argument is null
	 */
	public PaymentManager(SystemManager sm, CardIssuer issuer) {
		// checking arguments
		if (sm == null) {
			throw new IllegalArgumentException("the system manager cannot be null");
		}

		if (issuer == null) {
			throw new IllegalArgumentException("the card issuer cannot be null");
		}

		// copying references
		this.sm = sm;
		this.issuer = issuer;
	}

	@Override
	public void configure(AbstractSelfCheckoutStation machine) {
		// saving reference
		this.machine = machine;

		// passing references, because nothing actually notifies the observers of the
		// machine itself EVER
		cc = new CoinCollector(this, machine.getCoinValidator());
		bc = new BanknoteCollector(this, machine.getBanknoteValidator());
		cro = new CardReaderObserver(this, machine.getCardReader());
		rpls = new ReceiptPrinterObserver(this, machine.getPrinter());
	}

	@Override
	public boolean ready() {
		if (!cc.canUse()) {
			return false;
		}
		if (!bc.canUse()) {
			return false;
		}
		if (!cro.canUse()) {
			return false;
		}
		if (!rpls.canUse()) {
			return false;
		}

		// all the observers are ready, return true
		return true;
	}

	/**
	 * This is how you should tell the payment manager that there was payment added
	 * to the system.
	 * 
	 * @param value the value
	 */
	@Override
	public void notifyBalanceAdded(BigDecimal value) {
		if (value == null)
			throw new IllegalArgumentException("the value added cannot be null");

		this.payment = this.payment.add(value);
	}

	@Override
	public BigDecimal getCustomerPayment() {
		return this.payment;
	}

	@Override
	public void swipeCard(Card card) throws IOException {
		if (card == null) {
			throw new IllegalArgumentException("cannot swipe a null card");
		}

		this.machine.getCardReader().swipe(card);
	}

	public void notifyCardSwipe(CardSwipeData cardData) {
		if (cardData == null) {
			throw new IllegalArgumentException("received null card data from the observer");
		}

		// vars
		double amountDouble = sm.getTotalPrice().doubleValue();
		long holdNumber = issuer.authorizeHold(cardData.getNumber(), amountDouble);

		// testing the hold number
		if (holdNumber == -1) {
			return;
		} else {
			payment = sm.getTotalPrice();
			recordTransaction(cardData, holdNumber, amountDouble);
			sm.notifyPaid();
		}
	}

	@Override
	public void insertCard(Card card, String pin) throws IOException {
		if (card == null) {
			throw new IllegalArgumentException("cannot swipe a null card");
		}
		try {
			this.machine.getCardReader().insert(card, pin);
		} catch (InvalidPINException e) {
			return;
		}

	}

	@Override
	public void notifyCardInsert(CardInsertData cardData) {
		if (cardData == null) {
			throw new IllegalArgumentException("received null card data from the observer");
		}

		// vars
		double amountDouble = sm.getTotalPrice().doubleValue();
		long holdNumber = issuer.authorizeHold(cardData.getNumber(), amountDouble);

		// testing the hold number
		if (holdNumber == -1) {
			return;
		} else {
			payment = sm.getTotalPrice();
			recordTransaction(cardData, holdNumber, amountDouble);
			sm.notifyPaid();
		}

	}

	@Override
	public void tapCard(Card card) throws IOException {
		if (card == null) {
			throw new IllegalArgumentException("cannot swipe a null card");
		}

		this.machine.getCardReader().tap(card);
	}

	@Override
	public void notifyCardTap(CardTapData cardData) {
		if (cardData == null) {
			throw new IllegalArgumentException("received null card data from the observer");
		}

		// vars
		double amountDouble = sm.getTotalPrice().doubleValue();
		long holdNumber = issuer.authorizeHold(cardData.getNumber(), amountDouble);

		// testing the hold number
		if (holdNumber == -1) {
			return;
		} else {
			payment = sm.getTotalPrice();
			recordTransaction(cardData, holdNumber, amountDouble);
			sm.notifyPaid();
		}
	}

	@Override
	public void insertCoin(Coin coin) {
		try {
			this.machine.getCoinSlot().receive(coin);
		} catch (DisabledException e) {
			this.sm.blockSession();
			this.sm.notifyAttendant("Device Powered Off");

			// Should never happen
		} catch (CashOverloadException e) {
			this.sm.blockSession();
			this.sm.notifyAttendant("Machine cannot accept coins");

		}
	}

	@Override
	public void insertBanknote(Banknote banknote) {
		try {
			this.machine.getBanknoteInput().receive(banknote);
		} catch (DisabledException e) {
			this.sm.blockSession();
			this.sm.notifyAttendant("Device Powered Off");
		} catch (CashOverloadException e) {
			// Should never happen
			this.sm.blockSession();
			this.sm.notifyAttendant("Machine cannot accept bank notes");
		}
	}

	// Determines if the machine can provide
	// change to customer
	protected boolean canTenderChange(BigDecimal change) {

		ICoinDispenser coinDispenser;
		IBanknoteDispenser banknoteDispenser;
		BigDecimal denomination = BigDecimal.ZERO;
		int cashCount = 0;
		BigDecimal totalMachineCash = BigDecimal.ZERO;
		boolean canGiveChange = true;

		// Loop through all coin dispensers in the machine to check if empty
		// and find total machine cash balance
		for (int i = 0; i < this.machine.getCoinDenominations().size(); i++) {
			denomination = this.machine.getCoinDenominations().get(i);
			coinDispenser = this.machine.getCoinDispensers().get(denomination);
			cashCount = cashCount + coinDispenser.size();
			totalMachineCash = totalMachineCash.add(denomination.multiply(BigDecimal.valueOf(coinDispenser.size())));
		}

		// Loop through all banknotes in machine to check if empty
		for (int i = 0; i < this.machine.getBanknoteDenominations().length; i++) {
			denomination = this.machine.getBanknoteDenominations()[i];
			banknoteDispenser = this.machine.getBanknoteDispensers().get(denomination);
			cashCount = cashCount + banknoteDispenser.size();
			totalMachineCash = totalMachineCash
					.add(denomination.multiply(BigDecimal.valueOf(banknoteDispenser.size())));
		}

		// if cashCount is 0, no coins or banknotes in machine
		// block session and notify
		if (cashCount == 0) {
			canGiveChange = false;
		}

		// Machine does not have enough cash
		// to return change
		if (totalMachineCash.compareTo(change) < 0) {
			canGiveChange = false;
		}

		return canGiveChange;

	}

	@Override
	public boolean tenderChange() throws RuntimeException, NoCashAvailableException {
		// Determines amount of change needed
		BigDecimal change = this.payment.subtract(sm.getTotalPrice());

		// machine does not have enough cash
		// to give change
		if (!canTenderChange(change)) {
			this.sm.blockSession();
			this.sm.notifyAttendant("Not enough cash in machine to return change");
			throw new NoCashAvailableException();
		}

		switch (change.compareTo(BigDecimal.ZERO)) {
		case 1:
			// Payment greater then total, need to dispense change
			break;
		case 0:
			// Payment == total, no change needed
			this.sm.notifyPaid();
			return true;
		case -1:
			// The payment was less than the total price
			throw new RuntimeException("The total price is greater than payment");
		}

		// Iterate through all the banknote denominations in descending order
		// This assumes largest denominations are last in list
		for (int i = this.machine.getBanknoteDenominations().length - 1; i >= 0; i--) {
			BigDecimal denomination = this.machine.getBanknoteDenominations()[i];
			IBanknoteDispenser banknoteDispenser = this.machine.getBanknoteDispensers().get(denomination);

			// check if current dispensers is empty.
			// If empty, switch to next denomination
			if (banknoteDispenser.size() == 0) {
				continue;
			}

			// checks if current change balance is less then
			// current denomination
			if (change.compareTo(denomination) < 0) {
				continue;
			}

			// emits current banknote denomination until balance is less
			// than current denomination
			while (change.compareTo(denomination) >= 0) {
				// Emit current banknote denomination
				// and update current change
				try {
					banknoteDispenser.emit();
				} catch (NoCashAvailableException | DisabledException | CashOverloadException e) {
					this.sm.blockSession();
					this.sm.notifyAttendant("Banknote was not emitted");
					return false;

				}
				change = change.subtract(denomination);
			}
		}

		// Iterate through all the cash denominations now
		for (int i = 0; i <= this.machine.getCoinDenominations().size() - 1; i++) {
			BigDecimal denomination = this.machine.getCoinDenominations().get(i);
			ICoinDispenser coinDispenser = this.machine.getCoinDispensers().get(denomination);

			// checks if current dispenser are empty
			// If empty switch to next denomination
			if (coinDispenser.size() == 0) {
				continue;
			}

			// checks if current balance is less than
			// the current denomination
			if (change.compareTo(denomination) < 0) {
				continue;
			}

			// emits current coin until
			// change is less then coin value
			while (change.compareTo(denomination) >= 0) {
				// emits current coin denomination
				// and updates change balance
				try {
					coinDispenser.emit();
				} catch (CashOverloadException | NoCashAvailableException | DisabledException e) {
					this.sm.blockSession();
					this.sm.notifyAttendant("Coin was not emitted");
					return false;

				}
				change = change.subtract(denomination);
			}
		}

		// Checks to insure proper change was provided
		return switch (change.compareTo(BigDecimal.ZERO)) {
		case 1:
			// We did not dispense enough change
			this.sm.notifyAttendant("Not enough change was dispensed");
			yield false;
		case 0:
			// Dispensed enough change
			this.machine.getBanknoteOutput().dispense();
			this.sm.notifyPaid();
			yield true;
		case -1:
			// this should never actually happen
			this.sm.notifyAttendant("To much change was dispensed");
			this.machine.getBanknoteOutput().dispense();
			this.sm.notifyPaid();
			yield false;
		default:
			// I can't imagine this ever happening, I don't know why Java forces you to
			// include it.
			throw new IllegalArgumentException("Unexpected value: " + change.compareTo(BigDecimal.ZERO));
		};
	}

	@Override
	public SessionStatus getSessionState() {
		return sm.getSessionState();
	}

	@Override
	public void blockSession() {
		sm.blockSession();
	}

	@Override
	public void unblockSession() {
		sm.unblockSession();
	}

	@Override
	public void notifyPaid() {
		sm.notifyPaid();
	}

	@Override
	public void recordTransaction(CardData card, Long holdnumber, Double amount) {
		sm.recordTransaction(card, holdnumber, amount);
	}

	@Override
	public void printReceipt(PaymentType type, Card card) {
		// asserting arguments
		if (type == null) {
			throw new IllegalArgumentException("Type cannot be null.");
		}
		if (type == PaymentType.CARD && card == null) {
			throw new IllegalArgumentException("Card cannot be null for type CARD.");
		}
		if (sm.getCustomerPayment().compareTo(sm.getTotalPrice()) < 0) {
			throw new RuntimeException("Payment is less than total price.");
		}
		if (sm.getProducts().isEmpty()) {
			throw new RuntimeException("Product list cannot be empty.");
		}

		// ensuring that the printer can print
		if (!getCanPrint()) {
			// Notify the attendant of what the printer needs
			if(!hasInk && !hasPaper) {
				sm.notifyAttendant("Machine could not print receipt in full. Printer requires ink and paper. Duplicate receipt needed.");
			}else if(!hasInk) {
				sm.notifyAttendant("Machine could not print receipt in full. Printer requires ink. Duplicate receipt needed.");
			}else if(!hasPaper) {
				sm.notifyAttendant("Machine could not print receipt in full. Printer requires paper. Duplicate receipt needed.");
			}
			//block the session
			sm.blockSession();
			return;
		}

		if(lowInk) {
			sm.notifyAttendant("The printer is low on ink.");
		} else if(lowPaper) {
			sm.notifyAttendant("The printer is low on paper.");
		}
		
		// printing the receipt
		try {
			printLine("----- Receipt -----\n");
			for (Product product : sm.getProducts()) {
				// printing the item
				if (product instanceof BarcodedProduct) {
					BarcodedProduct p = (BarcodedProduct) product;
					printLine(String.valueOf(p.getPrice()) + "\t" + p.getDescription() + "\n");
					continue;
				}

				// this should never happen because other functions would catch an illegal
				// product
				throw new IllegalArgumentException(
						"cannot print information of product of type " + product.getClass().toString());
			}
			printLine("Payment Type: " + type.toString() + "\n");
			printLine("Price: $" + sm.getTotalPrice() + "\n");
			printLine("Payment: $" + sm.getCustomerPayment() + "\n");

			// adding card details
			if (card != null) {
				printLine("Card Holder: " + card.cardholder + "\n");
				printLine("Card Number: " + card.number + "\n");
				printLine("Kind of Card: " + card.kind + "\n");
			}
			if(membershipNum!=null) {
				printLine(membershipNum);
			}
		} catch (EmptyDevice e) {
			// Notify the attendant and block the session
			sm.notifyAttendant("Machine could not print receipt in full. Printer is empty.");
			sm.blockSession();
		}

		// cutting the paper
		this.machine.getPrinter().cutPaper();
	}

	protected void printLine(String s) throws EmptyDevice {
		for (int i = 0; i < s.length(); i++) {
			try {
				this.machine.getPrinter().print(s.charAt(i));
			} catch (OverloadedDevice e) {
				try {
					this.machine.getPrinter().print('\n');
				} catch (OverloadedDevice e_2) {
					// this should never happen
					throw new RuntimeException("an unexpected error occurred.");
				}
			}
		}
	}

	@Override
	public void notifyAttendant(String reason) {
		sm.notifyAttendant(reason);
	}

	/**
	 * Sets variables: hasPaper and lowPaper
	 * @param hasPaper boolean indicating whether printer has paper.
	 * @param lowPaper boolean indicating whether printer is low on paper.
	 */
	public void modifyPaper(boolean hasPaper, boolean lowPaper) {
		this.hasPaper = hasPaper;
		this.lowPaper = lowPaper;
	}

	/**
	 * Sets variables: hasInk and lowInk
	 * 
	 * @param hasInk boolean indicating whether printer has ink.
	 * @param lowInk boolean indicating whether printer is low on ink.
	 */
	public void modifyInk(boolean hasInk, boolean lowInk) {
		this.hasInk = hasInk;
		this.lowInk = lowInk;
	}

	/**
	 * Determines if receipt can be printed.
	 */
	protected void checkPrint() {
		if(!hasInk || !hasPaper) {
			setCanPrint(false);
		}
	}
	
	/**
	 * Sets the boolean canPrint
	 * @param print boolean to set canPrint to
	 */
	public void setCanPrint(boolean print){
		this.canPrint = print;
	}

	/**
	 * Retrieves the value of canPrint
	 * @return the value of canPrint
	 */
	public boolean getCanPrint(){
		return canPrint;
	}
	
	/**
	 * Updates system to account for ink being added to printer.
	 */
	public void inkAdded() {
		rpls.inkHasBeenAddedToThePrinter();
	}
	
	/**
	 * Updates system to account for paper being added to printer.
	 */
	public void paperAdded() {
		rpls.paperHasBeenAddedToThePrinter();
	}

	/**
	 * gets the membership number for the given customer
	 * @return the membership number of the customer
	 */
	public String getMembershipNum() {
		return membershipNum;
	}

	/**
	 * sets the membership number of the customer
	 * @param membershipNum membership number entered by the customer
	 */
	public void setMembershipNum(String membershipNum) {
		this.membershipNum = membershipNum;
	}

	@Override
	public void notifyPaper(boolean hasPaper) {
		this.hasPaper=hasPaper;
		
	}

	@Override
	public void notifyInk(boolean hasInk) {
		this.hasInk=hasInk;
		
	}

}
