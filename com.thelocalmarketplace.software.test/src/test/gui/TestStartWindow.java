package test.gui;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import GUIcomponents.GuiForI3.StartWindow;

public class TestStartWindow {
	StartWindow testGui;
	
	@Before 
	public void setUp() { 
		testGui = new StartWindow(); 
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
