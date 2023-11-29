package GUIcomponents.GuiForI3;

import javax.swing.SwingUtilities;

import driver.Driver;
import utils.DriverHelper;



public class Launcher {
	
	public static Driver dd; 

	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
				     @Override
				     public void run() {
				    	dd = new Driver(DriverHelper.chooseMachineType());	     }
				}).start();
				
				StartWindow startframe = new StartWindow();
				
				    	 
				    
				
				
			}
			
		});

	}
}


