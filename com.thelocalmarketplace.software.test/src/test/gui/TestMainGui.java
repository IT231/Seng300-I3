package test.gui;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import GUIcomponents.GuiForI3.MainGui;

public class TestMainGui {

	private MainGui testGui;

	@Before 
	public void setUp() { 
		testGui = new MainGui(); 
	}
  
//	@Test 
//	public void testActionPerformedPay() { 
//		ActionEvent mockEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, MainGui.PAY);
//		testGui.actionPerformed(mockEvent);
//		//assertNotNull(testGui.getPayGui()); 
//	}
	
	@Test 
	public void testInitialize() { 
		assertNotNull(testGui);
	}
	
	@After 
	public void tearDown() { 
		testGui = null; 
	}
}
