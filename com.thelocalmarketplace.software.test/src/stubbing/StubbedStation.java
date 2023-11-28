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
import java.util.Currency;
import java.util.Locale;

import com.jjjwelectronics.scale.ElectronicScaleGold;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;

public class StubbedStation extends SelfCheckoutStationGold {

	public static final BigDecimal[] coinDenominations = new BigDecimal[] { new BigDecimal(0.01), new BigDecimal(0.05),
			new BigDecimal(0.10), new BigDecimal(0.25), new BigDecimal(1), new BigDecimal(2) };
	public static final BigDecimal[] banknoteDenominations = new BigDecimal[] { new BigDecimal(5), new BigDecimal(10),
			new BigDecimal(20), new BigDecimal(50) };
	public static final int coinDispenserCapacity = 100;
	public static final int banknoteStorageCapacity = 100;

	public AbstractSelfCheckoutStation machine;

	public StubbedStation() {
		machine = new SelfCheckoutStationGold();
	}
	
	public ElectronicScaleGold getBaggingAreaScale() {
		// at runtime, this is a ElectronicScaleGold object
		return (ElectronicScaleGold) machine.baggingArea;
	}

	public static void configure() {
		AbstractSelfCheckoutStation.resetConfigurationToDefaults();
		AbstractSelfCheckoutStation.configureCoinDenominations(coinDenominations);
		AbstractSelfCheckoutStation.configureCoinDispenserCapacity(coinDispenserCapacity);
		AbstractSelfCheckoutStation.configureBanknoteDenominations(banknoteDenominations);
		AbstractSelfCheckoutStation.configureBanknoteStorageUnitCapacity(banknoteStorageCapacity);
		AbstractSelfCheckoutStation.configureCurrency(Currency.getInstance(Locale.CANADA));
	}

	public static void setBaggingAreaWeightLimit(double weight) {
		AbstractSelfCheckoutStation.configureScaleMaximumWeight(weight);
	}

}
