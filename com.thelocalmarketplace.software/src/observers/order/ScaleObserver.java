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

package observers.order;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;

import managers.OrderManager;
import observers.AbstractDeviceObserver;

public class ScaleObserver extends AbstractDeviceObserver implements ElectronicScaleListener {
	// object references
	private OrderManager om;

	// Take keep the supplied scale as a pointer
	// and register yourself with that scale.
	public ScaleObserver(OrderManager om, IElectronicScale device) {
		super(device);

		// checking for null
		if (om == null) {
			throw new IllegalArgumentException("OrderManager cannot be null.");
		}

		this.om = om;
		device.register(this);
	}

	// If outside the bounds of expectation, shut the system down.
	// Once we're back within these bounds, re-enable the system
	// This only works if the WeightChecker is enabled.
	@Override
	public void theMassOnTheScaleHasChanged(IElectronicScale scale, Mass mass) {
		this.om.notifyMassChanged(this, mass.inGrams());
	}

	@Override
	public void theMassOnTheScaleHasExceededItsLimit(IElectronicScale scale) {
		this.om.notifyScaleOverload(scale, true);
	}

	@Override
	public void theMassOnTheScaleNoLongerExceedsItsLimit(IElectronicScale scale) {
		this.om.notifyScaleOverload(scale, false);
	}
}