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

import com.tdc.coin.Coin;
import com.tdc.coin.CoinValidator;
import com.tdc.coin.CoinValidatorObserver;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;

import driver.Driver;
import managers.PaymentManager;
import observers.AbstractComponentObserver;

/**
 * This is the "Pay via Coin" use case.
 */

/**
 * <p>
 * This class is an observer that listens to the events emitted by a
 * {@link CoinValidator} object. The reason being is that there is no other way
 * to access the coins or cash inputed into the system from the
 * {@code SelfCheckoutStation}.
 * </p>
 * 
 * <p>
 * This class does nothing but listen for the {@link Coin}s emitted by a
 * {@link CoinValidator}.
 * </p>
 * 
 * @see CoinValidator
 * @see CoinValidatorObserver
 * @see Driver
 */
public class CoinCollector extends AbstractComponentObserver implements CoinValidatorObserver {

	// object references
	private PaymentManager ref;

	/**
	 * Creates an observer to listen to the events emitted by a
	 * {@link CoinValidator}.
	 */
	public CoinCollector(PaymentManager pm, CoinValidator device) {
		super(device);

		// checking for null
		if (pm == null) {
			throw new IllegalArgumentException("PaymentManager cannot be null.");
		}

		this.ref = pm;
		device.attach(this);
	}

	/**
	 * Listens for the `validCoinDetected` event from a {@link CoinValidator} so
	 * that this object can infer the amount of coins or cash inputed into the
	 * {@link AbstractSelfCheckoutStation} and therefore its balance.
	 * 
	 * @see CoinValidator
	 * @see CoinValidatorObserver
	 */
	@Override
	public void validCoinDetected(CoinValidator validator, BigDecimal value) {
		this.ref.notifyBalanceAdded(value);
	}

	@Override
	public void invalidCoinDetected(CoinValidator validator) {
		// TODO notify the parent object to dispense the invalid coin
	}

}
