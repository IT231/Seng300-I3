package test.gui;

import static org.junit.Assert.assertNotNull;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import GUIcomponents.GuiForI3.membershipnumbergui;

public class TestMembershipNumberGui {

	private membershipnumbergui testGui;

	@Before 
	public void setUp() { 
		testGui = new membershipnumbergui(); 
	}
	
	@Test 
	public void testInitialize() { 
		assertNotNull(testGui);
	}
  
	@Test 
	public void testActionPerformedEnter() { 
		// Assuming you have a JTextField in your membershipnumbergui class
		String inputText = "11111";
//		testGui.getMembershipNumField().setText(inputText);
//		ActionEvent mockEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Enter"); 
//		testGui.actionPerformed(mockEvent);
//		assertEquals(inputText, testGui.getEnteredMembershipNumber()); 
	}
	
	@After 
	public void tearDown() { 
		testGui = null; 
	}
}
