package test.gui;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import GUIcomponents.GuiForI3.paybyCashGui;

public class TestPayByCashGui {
	
    private paybyCashGui testGui;
    
	@Before 
	public void setUp() { 
		testGui = new paybyCashGui(); 
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