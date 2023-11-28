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

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;

public class StubbedItem extends Item {

	public StubbedItem(double weightValue) {
		this(new BigDecimal(weightValue));
	}

	public StubbedItem(BigDecimal weightValue) {
		this(new Mass(weightValue));
	}

	public StubbedItem(Mass weightValue) {
		super(weightValue);
	}
}
