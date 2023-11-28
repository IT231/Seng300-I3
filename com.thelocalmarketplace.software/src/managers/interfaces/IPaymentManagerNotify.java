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

import java.math.BigDecimal;

import com.jjjwelectronics.card.Card.CardData;

/**
 * This interface is used by any object that {@link IPaymentManager} owns, this
 * exists for the child objects to communicate with their parent object without
 * the super class SystemManager being involved does it have to implement these
 * methods
 */
public interface IPaymentManagerNotify {

	/**
	 * This method notifies the {@link IPaymentManager} that a child object received
	 * a card swipe.
	 * 
	 * @param the swiped card data
	 * @return
	 */
	void notifyCardSwipe(CardData cardData);

	/**
	 * This method notifies the {@link IPaymentManager} that a child object has
	 * received some payment, either from a banknote or from a coin.
	 * 
	 * @param value the value of the inputted banknote or coin
	 */
	void notifyBalanceAdded(BigDecimal value);
	
	/**
	 * Notifies the system about the paper status.
	 *
	 * @param hasPaper A boolean indicating whether there is paper available (true) or not (false).
	 */
	void notifyPaper(boolean hasPaper);
	
	/**
	 * Notifies the system about the ink status.
	 *
	 * @param hasInk A boolean indicating whether there is ink available (true) or not (false).
	 */
	void notifyInk(boolean hasInk);
}
