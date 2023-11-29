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

package observers.payment;

import java.math.BigDecimal;
import java.util.Currency;

import com.tdc.banknote.BanknoteValidator;
import com.tdc.banknote.BanknoteValidatorObserver;

import managers.PaymentManager;
import observers.AbstractComponentObserver;

public class BanknoteCollector extends AbstractComponentObserver implements BanknoteValidatorObserver {

	// object references
	private PaymentManager ref;

	// Creates and observer to listen to the events emitted by a
	// BanknoteValidator. Cannot be null
	public BanknoteCollector(PaymentManager paymentManager, BanknoteValidator device) {
		super(device);
		
		if (paymentManager == null) {
			throw new IllegalArgumentException("PaymentManager cannot be null.");
		}

		this.ref = paymentManager;
		device.attach(this);
	}

	// Listens for valid Bank note
	@Override
	public void goodBanknote(BanknoteValidator validator, Currency currency, BigDecimal value) {
		this.ref.notifyBalanceAdded(value);
	}

	// listens for invalid bank note
	// Unsure what to implement as its handled in
	// "Hardware"
	@Override
	public void badBanknote(BanknoteValidator validator) {

	}

}
