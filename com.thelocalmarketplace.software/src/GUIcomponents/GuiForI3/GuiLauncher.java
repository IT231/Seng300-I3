package GUIcomponents.GuiForI3;

import java.math.BigDecimal;

import javax.swing.SwingUtilities;

import com.thelocalmarketplace.hardware.external.CardIssuer;

import driver.Driver;
import managers.PaymentManager;
import managers.SystemManager;
import utils.DriverHelper;



public class GuiLauncher {
	
	public static Driver dd;
	public static CardIssuer cardI = new CardIssuer("BankofCanada", 10000); // this makes the card issuer
	public static SystemManager sysman; // = new SystemManager(cardI, new BigDecimal(2));  // dont really understand what lenecy is so i put 2
	public PaymentManager payman = new PaymentManager(sysman, cardI);

	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				StartWindow startframe = new StartWindow();
				
				    	 
				    
				
				
			}
			
		});
		new Thread(new Runnable() { // have to do new threads or else the system locks up
		     @Override
		     public void run() {
		    	dd = new Driver(DriverHelper.chooseMachineType());	
		    	dd.setup();}
		}).start();
		
		new Thread(new Runnable() { // have to do new threads or else the system locks up
		     @Override
		     public void run() {
		    	 sysman = new SystemManager(cardI, new BigDecimal(2));	
		    	dd.setup();}
		}).start();

	}
}


