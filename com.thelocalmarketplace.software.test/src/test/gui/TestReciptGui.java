package test.gui;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import GUIcomponents.GuiForI3.ReciptGui;

public class TestReciptGui {
	private ReciptGui testGui;
	
	@Before
	public void setUp() {
		testGui = new ReciptGui();
	}
	
	@Test 
	public void testInitialize() { 
		assertNotNull(testGui);
	}
}
