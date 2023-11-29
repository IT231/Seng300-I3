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

import com.jjjwelectronics.card.Card.CardData;

public class StubbedCardData implements CardData {

	public static final String TYPE = "DEBIT";
	public static final String NUMBER = "0123456789";
	public static final String HOLDER = "Ryan Gosling";
	public static final String CVV = "000";

	private String type;
	private String number;
	private String holder;
	private String cvv;

	public StubbedCardData() {
		this(TYPE, NUMBER, HOLDER, CVV);
	}

	public StubbedCardData(String t, String n, String h, String c) {
		type = t;
		number = n;
		holder = h;
		cvv = c;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getNumber() {
		return number;
	}

	@Override
	public String getCardholder() {
		return holder;
	}

	@Override
	public String getCVV() {
		return cvv;
	}

}
