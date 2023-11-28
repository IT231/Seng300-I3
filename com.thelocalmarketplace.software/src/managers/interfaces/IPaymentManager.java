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

import java.io.IOException;
import java.math.BigDecimal;

import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.card.Card.CardData;
import com.tdc.NoCashAvailableException;
import com.tdc.banknote.Banknote;
import com.tdc.coin.Coin;

import managers.enums.PaymentType;

/**
 * A unified interface of what the payment manager should and should not do.
 */
public interface IPaymentManager extends IManager {
	/**
	 * Returns the amount of money the customer has inputted into the system
	 * 
	 * @return the amount of money
	 */
	BigDecimal getCustomerPayment();

	/**
	 * Allows the customer to swipe their credit card
	 * 
	 * @param creditCard a credit card
	 * @throws IOException
	 */
	void swipeCard(Card card) throws IOException;

	/**
	 * Allows the customer to insert a coin into the system.
	 * 
	 * @param coin a coin
	 */
	void insertCoin(Coin coin);

	/**
	 * Allows the customer to insert a banknote into the system.
	 * 
	 * @param banknote a banknote
	 */
	void insertBanknote(Banknote banknote);

	/**
	 * Provides change back to customer after completion of payment
	 * 
	 * @throws NoCashAvailableException
	 * @throws RuntimeException
	 */
	boolean tenderChange() throws RuntimeException, NoCashAvailableException;

	/**
	 * This method sets the internal state to {@code PAID}, should only be used
	 * internally.
	 */
	void notifyPaid();

	/**
	 * Stores a transaction for the manager.
	 * 
	 * @param card       the card data
	 * @param holdnumber the hold number
	 * @param amount     the price of the order
	 */
	void recordTransaction(CardData card, Long holdnumber, Double amount);

	/**
	 * Prints a receipt for a given set of products and payment details.s
	 * 
	 * @param type The payment type used (e.g., cash, credit card).
	 * @param card The card data associated with the payment (if applicable,
	 *             otherwise null).
	 */
	public void printReceipt(PaymentType type, Card card);
}
