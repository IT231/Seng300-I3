package test.gui;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import GUIcomponents.GuiForI3.MainGui;
import GUIcomponents.GuiForI3.removeItemGUI;

public class TestRemoveItemGUI {
	removeItemGUI testGui;
	
	@Before 
	public void setUp() { 
		testGui = new removeItemGUI(); 
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
