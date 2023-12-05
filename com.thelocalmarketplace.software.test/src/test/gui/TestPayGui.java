package test.gui;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Window;
import java.io.IOException;

import javax.swing.JFrame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.card.Card;

import GUIcomponents.GuiForI3.PayGui;
import GUIcomponents.GuiForI3.ReciptGui;
import managers.PaymentManager;

public class TestPayGui {

	private PayGui testGui;

	@Before 
	public void setUp() { 
		testGui = new PayGui(); 
	}
  
	@Test 
	public void testInitialize() { 
		assertNotNull(testGui);
	}
	
	@After 
	public void tearDown() { 
		testGui = null; 
	}
	
//	@Test 
//	public void testPayByTapButtonActionPerformed() { 
//		PaymentManager payman = null;
//		Window payFrame = null;
//		try {
//			payman.swipeCard(new Card("credit", "111111111", "Bob Bob", "123", "1234", true, true)); 
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		new ReciptGui();
//		payFrame.dispose();
//	}
}
