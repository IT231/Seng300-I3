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

import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.card.Card;
import com.tdc.NoCashAvailableException;
import com.tdc.banknote.Banknote;
import com.tdc.coin.Coin;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.external.CardIssuer;

import managers.PaymentManager;
import managers.enums.PaymentType;
import observers.payment.BanknoteCollector;
import observers.payment.CardReaderObserver;
import observers.payment.CoinCollector;
import observers.payment.ReceiptPrinterObserver;

public class StubbedPaymentManager extends PaymentManager {

	public StubbedSystemManager smStub;

	public BigDecimal notifyBalanceAddedValue;
	public boolean insertCoinCalled;
	public boolean insertBanknoteCalled;
	public boolean tenderChangeCalled;
	public boolean getCustomerPaymentCalled;
	public boolean printReceiptCalled;

	public StubbedPaymentManager(StubbedSystemManager sm, CardIssuer issuer) {
		super(sm, issuer);

		smStub = sm;

		insertCoinCalled = false;
		insertBanknoteCalled = false;
		tenderChangeCalled = false;
		getCustomerPaymentCalled = false;
		printReceiptCalled = false;
	}
	
	public boolean getHasPaper() {
		return super.hasPaper;
	}
	
	public boolean getHasInk() {
		return super.hasInk;
	}
	
	public void setHasPaper(boolean has) {
		super.hasPaper = has;
	}
	
	public void setHasInk(boolean has) {
		super.hasInk = has;
	}
	
	public CoinCollector getCoinCollector() {
		return super.cc;
	}
	
	public BanknoteCollector getBanknoteCollector() {
		return super.bc;
	}
	
	public CardReaderObserver getCardReaderObserver() {
		return super.cro;
	}
	
	public ReceiptPrinterObserver getReceiptPrinterObserver() {
		return super.rpls;
	}
	
	public BigDecimal getPayment() {
		return super.payment;
	}
	
	public void setPayment(double value) {
		super.payment = new BigDecimal(value);
	}

	@Override
	public void notifyBalanceAdded(BigDecimal value) {
		notifyBalanceAddedValue = value;
		super.notifyBalanceAdded(value);
	}

	public CardIssuer getIssuer() {
		return super.issuer;
	}
	
	public void setIssuer(CardIssuer i) {
		super.issuer = i;
	}

	public AbstractSelfCheckoutStation getMachine() {
		return machine;
	}

	private boolean configured;

	@Override
	public void configure(AbstractSelfCheckoutStation machine) {
		super.configure(machine);

		configured = true;
	}

	public boolean getConfigured() {
		return configured;
	}

	@Override
	public void insertCoin(Coin coin) {
		insertCoinCalled = true;
		super.insertCoin(coin);
	}

	@Override
	public void insertBanknote(Banknote banknote) {
		insertBanknoteCalled = true;
		super.insertBanknote(banknote);
	}

	@Override
	public boolean tenderChange() throws RuntimeException, NoCashAvailableException {
		tenderChangeCalled = true;
		return super.tenderChange();
	}

	@Override
	public BigDecimal getCustomerPayment() {
		getCustomerPaymentCalled = true;
		return super.getCustomerPayment();
	}

  @Override
	public boolean canTenderChange(BigDecimal value) {
		return super.canTenderChange(value);
  }

	@Override
	public void printReceipt(PaymentType type, Card card) {
		printReceiptCalled = true;
		super.printReceipt(type, card);
	}
	
	@Override
	public void printLine(String s) throws EmptyDevice {
		super.printLine(s);
	}
}
