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
import java.util.List;

import javax.naming.OperationNotSupportedException;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scale.IElectronicScale;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.Product;

import ca.ucalgary.seng300.simulation.NullPointerSimulationException;
import managers.OrderManager;
import managers.enums.ScanType;
import managers.enums.SessionStatus;
import managers.interfaces.IOrderManagerNotify;
import observers.order.BarcodeScannerObserver;
import observers.order.ScaleObserver;

public class StubbedOrderManager extends OrderManager {
	public StubbedSystemManager smStub;
	public boolean addItemToOrderCalled;
	public boolean addCustomerBagsCalled;
	public boolean getTotalPriceCalled;
	public boolean getExpectedMassCalled;
	public boolean getProductsCalled;
	public boolean onAttendantOverrideCalled;
	public boolean onDoNotBagRequestCalled;
	public boolean removeItemFromOrderCalled;

	public StubbedOrderManager(StubbedSystemManager sm, BigDecimal leniency) {
		super(sm, leniency);

		smStub = sm;

		addItemToOrderCalled = false;
		addCustomerBagsCalled = false;
		getTotalPriceCalled = false;
		getExpectedMassCalled = false;
		getProductsCalled = false;
		onAttendantOverrideCalled = false;
		onDoNotBagRequestCalled = false;
		removeItemFromOrderCalled = false;
	}
	
	public BarcodeScannerObserver getMainBarcodeObserver() {
		return super.main_bso;
	}
	
	public BarcodeScannerObserver getHandheldBarcodeObserver() {
		return super.handheld_bso;
	}
	
	public ScaleObserver getBaggingAreaObserver() {
		return super.baggingarea_so;
	}
	
	public List<IElectronicScale> getOverloadedScales() {
		return super.overloadedScales;
	}
	
	public List<IOrderManagerNotify> getListeners() {
		return super.listeners;
	}

	public BigDecimal getLeniency() {
		return super.leniency;
	}

	public void setActualWeight(BigDecimal a) {
		super.actualWeight = a;
	}

	public BigDecimal getActualWeight() {
		return super.actualWeight;
	}

	@Override
	public BigDecimal getWeightAdjustment() {
		return super.getWeightAdjustment();
	}

	@Override
	public void setWeightAdjustment(BigDecimal a) {
		super.setWeightAdjustment(a);
	}

	public void addProduct(Product p) {
		super.products.add(p);
	}

	public void setState(SessionStatus s) {
		smStub.setSessionState(s);
	}

	@Override
	public void checkWeightDifference(BigDecimal difference) {
		super.checkWeightDifference(difference);
	}

	public boolean getNoBaggingRequest() {
		return super.noBaggingRequested;
	}

	public AbstractSelfCheckoutStation getMachine() {
		return super.machine;
	}

	private boolean configured;

	@Override
	public void configure(AbstractSelfCheckoutStation machine) {
		super.configure(machine);

		configured = true;
	}

	public boolean getConfigured() {
		return configured;
	}

	@Override
	public void addItemToOrder(Item item, ScanType method) {
		addItemToOrderCalled = true;
		super.addItemToOrder(item, method);
	}

	@Override
	public void addCustomerBags(Item bags) throws IllegalArgumentException {
		addCustomerBagsCalled = true;
		super.addCustomerBags(bags);
	}

	@Override
	public BigDecimal getTotalPrice() {
		getTotalPriceCalled = true;
		return super.getTotalPrice();
	}

	@Override
	public BigDecimal getExpectedMass() {
		getExpectedMassCalled = true;
		return super.getExpectedMass();
	}

	@Override
	public List<Product> getProducts() throws NullPointerSimulationException {
		getProductsCalled = true;
		return super.getProducts();
	}

	public List<Product> getProductsVar() {
		return super.products;
	}

	@Override
	public void onAttendantOverride() {
		onAttendantOverrideCalled = true;
		super.onAttendantOverride();
	}

	@Override
	public void onDoNotBagRequest(Item item) {
		onDoNotBagRequestCalled = true;
		super.onDoNotBagRequest(item);
	}

	@Override
	public void removeItemFromOrder(Item item) {
		removeItemFromOrderCalled = true;
		super.removeItemFromOrder(item);
	}

}
