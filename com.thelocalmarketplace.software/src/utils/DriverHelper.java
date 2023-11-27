// Liam Major 30223023

package utils;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.Random;

import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.hardware.SelfCheckoutStationGold;
import com.thelocalmarketplace.hardware.SelfCheckoutStationSilver;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;
import managers.enums.SelfCheckoutTypes;

// this moves most of the setup from Driver into another class
public class DriverHelper {

	/**
	 * This randomly chooses the type of machine the customer will use.
	 * 
	 * @return the type of machine
	 */
	public static SelfCheckoutTypes chooseMachineType() {
		return switch (new Random().nextInt(0, 3)) {
		case 0:
			yield SelfCheckoutTypes.BRONZE;
		case 1:
			yield SelfCheckoutTypes.SILVER;
		case 2:
			yield SelfCheckoutTypes.GOLD;
		default:
			// should never happen
			yield SelfCheckoutTypes.BRONZE;
		};
	}

	/**
	 * This configures the self checkout machine. This includes;
	 * <ul>
	 * <li>the coin denominations</li>
	 * <li>the banknote denominations</li>
	 * <li>the capacity of the coin dispenser unit</li>
	 * <li>the capacity of the banknote storage unit</li>
	 * </ul>
	 * 
	 * @param coinDenoms              the coin denominations
	 * @param banknoteDenoms          the banknote denominations
	 * @param coinDispenserCapacity   the capacity of the coin dispenser unit
	 * @param banknoteStorageCapacity the capacity of the banknote storage unit
	 */
	public static void configureMachine(BigDecimal[] coinDenoms, BigDecimal[] banknoteDenoms, int coinDispenserCapacity,
			int banknoteStorageCapacity) {
		// resetting to defaults
		AbstractSelfCheckoutStation.resetConfigurationToDefaults();

		// configuring the machine
		AbstractSelfCheckoutStation.configureCoinDenominations(coinDenoms);
		AbstractSelfCheckoutStation.configureCoinDispenserCapacity(coinDispenserCapacity);
		AbstractSelfCheckoutStation.configureBanknoteDenominations(banknoteDenoms);
		AbstractSelfCheckoutStation.configureBanknoteStorageUnitCapacity(banknoteStorageCapacity);
		AbstractSelfCheckoutStation.configureCurrency(Currency.getInstance(Locale.CANADA));
	}

	/**
	 * This returns an instance of the self checkout machine.
	 * 
	 * @param type the type of self checkout machine to create
	 * @return the self checkout machine
	 */
	public static AbstractSelfCheckoutStation createMachine(SelfCheckoutTypes type) {
		if (type == null) {
			throw new InvalidArgumentSimulationException("invalid self checkout type");
		}

		// switching on enum type
		return switch (type) {
		case BRONZE:
			yield new SelfCheckoutStationBronze();
		case SILVER:
			yield new SelfCheckoutStationSilver();
		case GOLD:
			yield new SelfCheckoutStationGold();
		};
	}
}
