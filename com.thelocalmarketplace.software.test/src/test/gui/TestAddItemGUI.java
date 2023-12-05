package test.gui;

import static org.junit.Assert.*; 
import org.junit.After; 
import org.junit.Before; 
import org.junit.Test;
import GUIcomponents.GuiForI3.addItemGui;

public class TestAddItemGUI {

	private addItemGui testGui;

	@Before 
	public void setUp() { 
		testGui = new addItemGui(); 
	}
  
	@Test 
	public void testInitialize() { 
		assertNotNull(testGui);
	}
	
	@After 
	public void tearDown() { 
		testGui = null; 
	}
}

