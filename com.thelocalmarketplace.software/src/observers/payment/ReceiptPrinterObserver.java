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

package observers.payment;

import com.jjjwelectronics.printer.IReceiptPrinter;
import com.jjjwelectronics.printer.ReceiptPrinterListener;

import managers.PaymentManager;
import observers.AbstractDeviceObserver;

public class ReceiptPrinterObserver extends AbstractDeviceObserver implements ReceiptPrinterListener {

	// object references
	private PaymentManager ref;

	public ReceiptPrinterObserver(PaymentManager paymentManager, IReceiptPrinter device) {
		super(device);

		if (paymentManager == null) {
			throw new IllegalArgumentException("payment manager cannot be null");
		}

		this.ref = paymentManager;
		device.register(this);
	}

	@Override
	public void thePrinterIsOutOfPaper() {
		this.ref.maintainPaper(false, true);
	}

	@Override
	public void thePrinterIsOutOfInk() {
		this.ref.maintainInk(false, true);
	}

	@Override
	public void thePrinterHasLowInk() {
		this.ref.maintainInk(true, true);

	}

	@Override
	public void thePrinterHasLowPaper() {
		this.ref.maintainInk(true, true);
	}

	@Override
	public void paperHasBeenAddedToThePrinter() {
		this.ref.maintainPaper(true, false);
	}

	@Override
	public void inkHasBeenAddedToThePrinter() {
		this.ref.maintainInk(true, false);
	}

}
