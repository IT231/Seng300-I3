package test.gui;

import static org.junit.Assert.*;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import GUIcomponents.GuiForI3.adminGUI;
import managers.enums.SessionStatus;

public class TestAdminGUI {

    private adminGUI testGui;

    @Before
    public void setUp() {
        testGui = new adminGUI();
    }

//    @Test
//    public void testUnblockButtonActionPerformed() {
//        testGui.getSystemManager().setSessionState(SessionStatus.BLOCKED);
//        ActionEvent mockEvent = new ActionEvent(new JButton(), ActionEvent.ACTION_PERFORMED, "unblock");
//		  testGui.getUnblockButton().getActionListeners()[0].actionPerformed(mockEvent);
//		  assertEquals(SessionStatus.UNBLOCKED, testGui.getSystemManager().getSessionState());
//    }
	@Test 
	public void testInitialize() { 
		assertNotNull(testGui);
	}
	
	@After 
	public void tearDown() { 
		testGui = null; 
	}
}
