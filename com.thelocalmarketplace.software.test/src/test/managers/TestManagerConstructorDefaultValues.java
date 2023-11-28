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

package test.managers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import managers.enums.SessionStatus;
import stubbing.StubbedOrderManager;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedSystemManager;

public class TestManagerConstructorDefaultValues {
	private StubbedSystemManager sm;
	private StubbedPaymentManager pm;
	private StubbedOrderManager om;

	@Before
	public void setup() {
		sm = new StubbedSystemManager();
		om = sm.omStub;
		pm = sm.pmStub;
	}

	@Test
	public void testSystemDefaultIssuer() {
		assertNotNull(sm.getIssuer());
		assertEquals(sm.getIssuer(), StubbedSystemManager.issuer);
	}

	@Test
	public void testSystemDefaultState() {
		assertNotNull(sm.getState());
		assertEquals(sm.getState(), SessionStatus.NORMAL);
	}

	@Test
	public void testSystemDefaultRecords() {
		assertNotNull(sm.getRecords());
		assertTrue(sm.getRecords().size() == 0);
	}

	@Test
	public void testPaymentDefaultSystemManager() {
		assertNotNull(pm.smStub);
		assertEquals(pm.smStub, sm);
	}

	@Test
	public void testPaymentDefaultPayment() {
		assertNotNull(pm.getPayment());
		assertEquals(pm.getPayment(), BigDecimal.ZERO);
	}

	@Test
	public void testPaymentDefaultIssuer() {
		assertNotNull(pm.getIssuer());
		assertEquals(pm.getIssuer(), StubbedSystemManager.issuer);
	}

	@Test
	public void testOrderDefaultSystemManager() {
		assertNotNull(om.smStub);
		assertEquals(om.smStub, sm);
	}

	@Test
	public void testOrderDefaultLeniency() {
		assertNotNull(om.getLeniency());
		assertEquals(om.getLeniency(), StubbedSystemManager.leniency);
	}

	@Test
	public void testOrderDefaultAdjustment() {
		assertNotNull(om.getWeightAdjustment());
		assertEquals(om.getWeightAdjustment(), BigDecimal.ZERO);
	}

	@Test
	public void testOrderDefaultActualWeight() {
		assertNotNull(om.getActualWeight());
		assertEquals(om.getActualWeight(), BigDecimal.ZERO);
	}

	@Test
	public void testOrderDefaultNoBaggingRequest() {
		assertFalse(om.getNoBaggingRequest());
	}

	@Test
	public void testOrderDefaultProducts() {
		assertNotNull(om.getProductsVar());
		assertTrue(om.getProductsVar().size() == 0);
	}

	@Test
	public void testOrderDefaultListeners() {
		assertNotNull(om.getListeners());
		assertTrue(om.getListeners().size() == 0);
	}

	@Test
	public void testOrderDefaultOverloadedScales() {
		assertNotNull(om.getOverloadedScales());
		assertTrue(om.getOverloadedScales().size() == 0);
	}
}
