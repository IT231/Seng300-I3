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

package observers;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;

/**
 * This is to centralize functionality of the `canUse` function out of the
 * individual observer.
 */

public abstract class AbstractDeviceObserver implements IDeviceListener, IObserverUseable {

	private IDevice<? extends IDeviceListener> device;

	public AbstractDeviceObserver(IDevice<? extends IDeviceListener> d) {
		if (d == null) {
			throw new IllegalArgumentException("observed device cannot be null.");
		}

		// copying hardware reference
		this.device = d;
	}

	@Override
	public boolean canUse() {
		if (!device.isPoweredUp()) {
			return false;
		}
		return !device.isDisabled();
	}

	@Override
	public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
		// this method is never actually called
	}

	@Override
	public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
		// this method is never actually called
	}

	@Override
	public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
		// this method IS called, but its useless on it's own
	}

	@Override
	public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
		// this method IS called, but its useless on it's own
	}

}
