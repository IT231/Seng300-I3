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

package test.managers.system;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;

public class TestConfigures {

	private StubbedSystemManager sm;

	@Before
	public void setup() {
		StubbedStation.configure();
		
		sm = new StubbedSystemManager();
	}

	@Test
	public void testSystemManagerCopiesHardwareReference() {
		sm.configure(new StubbedStation().machine);

		assertNotNull(sm.getMachine());
	}

	@Test
	public void testSystemManagerConfiguresChildManagers() {
		sm.configure(new StubbedStation().machine);

		assertTrue(sm.omStub.getConfigured());
		assertTrue(sm.pmStub.getConfigured());
	}
	
	@Test
	public void testOrderManagerCopiedHardwareReference() {
		sm.configure(new StubbedStation().machine);

		assertNotNull(sm.omStub.getMachine());
	}
	
	@Test
	public void testPaymentManagerCopiedHardwareReference() {
		sm.configure(new StubbedStation().machine);

		assertNotNull(sm.pmStub.getMachine());
	}
	
	@Test
	public void testPaymentManagerCreatesObserversOnConfigure() {
		sm.configure(new StubbedStation().machine);
		
		assertNotNull(sm.pmStub.getBanknoteCollector());
		assertNotNull(sm.pmStub.getCardReaderObserver());
		assertNotNull(sm.pmStub.getReceiptPrinterObserver());
		assertNotNull(sm.pmStub.getCoinCollector());
	}
	
	@Test
	public void testOrderManagerCreatesObserversOnConfigure() {
		sm.configure(new StubbedStation().machine);
		
		assertNotNull(sm.omStub.getBaggingAreaObserver());
		assertNotNull(sm.omStub.getMainBarcodeObserver());
		assertNotNull(sm.omStub.getHandheldBarcodeObserver());
	}
}
