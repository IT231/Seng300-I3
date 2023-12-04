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

package utils;

import java.util.Calendar;
import java.util.Random;

import com.jjjwelectronics.card.Card;
import com.thelocalmarketplace.hardware.external.CardIssuer;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;

public class CardHelper {

	public static final String ISSUER_NAME = "Blackrock";
	public static final long MAX_HOLDS = 100;
	private static Random r = new Random(0);

	public static final String[] BANKS = { "RBC", "Scotiabank" };

	/**
	 * Creates a card issuer.
	 * 
	 * @return the card issuer
	 */
	public static CardIssuer createCardIssuer() {
		return createCardIssuer(ISSUER_NAME, MAX_HOLDS);
	}

	/**
	 * Creates a card issuer.
	 * 
	 * @param name the name of the issuer
	 * @return the card issuer
	 */
	public static CardIssuer createCardIssuer(String name) {
		return createCardIssuer(name, MAX_HOLDS);
	}

	/**
	 * Creates a card issuer.
	 * 
	 * @param maxHolds the maximum number of holds
	 * @return the card issuer
	 */
	public static CardIssuer createCardIssuer(long maxHolds) {
		return createCardIssuer(ISSUER_NAME, maxHolds);
	}

	/**
	 * Creates a card issuer.
	 * 
	 * @param name     the name of the issuer
	 * @param maxHolds the maximum number of holds
	 * @return the card issuer
	 */
	public static CardIssuer createCardIssuer(String name, long maxHolds) {
		return new CardIssuer(name, maxHolds);
	}

	public static Card createCard(CardIssuer issuer) {
		return createCard(issuer, r.nextDouble(100, 10_000));
	}

	/**
	 * Creates a card with a specified limit.
	 * 
	 * @param issuer the card issuer the card should be added to
	 * @param limit  the limit
	 * @return a randomized card
	 */
	public static Card createCard(CardIssuer issuer, double limit) {
		// creating a card issuer if none exists
		if (issuer == null) {
			throw new IllegalArgumentException("cannot add a card to a null card issuer");
		}

		// creating the fields of the card
		String ccv = String.valueOf(r.nextLong(100, 1_000));
		Calendar expiry = Calendar.getInstance();
		expiry.add(Calendar.DAY_OF_MONTH, 20);
		String holder = "Joe Joeman";

		// finding a non-used card number
		do {
			try {
				String number = String.valueOf(r.nextLong(1, 1_000_000_000));

				// creating the card object
				Card card = new Card("debit", number, holder, ccv, null, false, false);

				// adding the card to the issuer
				issuer.addCardData(number, holder, expiry, ccv, limit);

				// returning the card to the caller
				return card;
			} catch (InvalidArgumentSimulationException e) {
				// this is so we don't loop infinitely
				if (!e.getMessage().contains("The card number is not valid.")) {
					throw new IllegalArgumentException("invalid amount was passed into the function");
				}
				
				// since only the card number is invalid, we can loop
				continue;
			}
		} while (true);
	}

	/**
	 * Simulates the creating of an invalid card, identical to createCard method
	 * except the card is not added to the issuer's database. This is similar to a
	 * counterfeit card in the real world.
	 * 
	 * @param issuer The name of the card issuer
	 * @return the invalid card
	 */
	public static Card createNonIssuedCard() {
		Random r = new Random();

		// creating the fields of the card
		String ccv = String.valueOf(r.nextLong(100, 1_000));
		Calendar expiry = Calendar.getInstance();
		expiry.add(Calendar.DAY_OF_MONTH, 20);
		String holder = "Joe Joeman";

		// finding a non-used card number
		String number = String.valueOf(r.nextLong(1, 1_000_000_000));

		// creating the card object
		Card card = new Card("debit", number, holder, ccv, null, false, false);

		// returning the card to the caller
		return card;
	}
}
