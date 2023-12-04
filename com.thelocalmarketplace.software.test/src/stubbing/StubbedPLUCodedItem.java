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

import com.jjjwelectronics.Mass;
import com.thelocalmarketplace.hardware.PLUCodedItem;
import com.thelocalmarketplace.hardware.PriceLookUpCode;

public class StubbedPLUCodedItem extends PLUCodedItem {

	public static final PriceLookUpCode PLUCODE = new PriceLookUpCode("12345");
	public static final Mass mass = new Mass(10);

	public StubbedPLUCodedItem() {
		this(StubbedPLUCodedItem.PLUCODE, StubbedPLUCodedItem.mass);
	}

    public StubbedPLUCodedItem(PriceLookUpCode plucode2, Mass mass2) {
        super(plucode2, mass2);

    }

}
