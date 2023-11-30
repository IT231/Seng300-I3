package GUIcomponents.GuiForI3;

import java.math.BigDecimal;

import com.thelocalmarketplace.hardware.external.CardIssuer;

import driver.Driver;
import managers.PaymentManager;
import managers.SystemManager;
import utils.DriverHelper;

public class Launcher {
	public static Driver dd = new Driver(DriverHelper.chooseMachineType());	
	public CardIssuer cardI = new CardIssuer("BankofCanada", 10000); // this makes the card issuer
	public SystemManager sysman = new SystemManager(cardI, new BigDecimal(2));  // dont really understand what lenecy is so i put 2
	public PaymentManager payman = new PaymentManager(sysman, cardI);
	GuiLauncher guil = new GuiLauncher();
	public void main() {
		dd.setup();
		guil.main(null);
		
		
	}

}
