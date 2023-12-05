// Aleksandr Sokolov (30191754)
// Azariah Francisco (30085863)
// Brandon Smith (30141515)
// Carlos Serrouya (30192761)
// Diego de Jaraiz (30176017)
// Emily Willams (30122865)
// Evan Ficzere (30192404)
// Jaden Taylor (30113034)
// Joshua Bourchier (30194364)
// Justine Mangaliman (30164741)
// Kaelin Good (30092239)
// Laura Yang（30156356)
// Myra Latif (30171760)
// Noelle Thundathil (30115430)
// Raj Rawat (30173990)
// Roshan Patel (30184010)
// Sam Fasakin (30161903)
// Simon Bondad (30163401)
// Simon Oseen (30144175)
// Sohaib Zia (30160114)
// Sunny Hoang (30170708)
// Yasemin Khanmoradi (30066537)

package GUIcomponents.GuiForI3;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import com.jjjwelectronics.Mass;
import com.jjjwelectronics.card.Card;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.Product;

public class MainGui extends Simulation implements ActionListener {

	public static final String PAY = "pay";
	public static final String ADD_ITEM = "add_item";
	public static final String REMOVE_ITEM = "remove_item";
	public JFrame MainGui;
	public List<Product> products;
	int num = 0;
	
	MainGui() {
		initialize();
	}
	
	public void initialize() {
		MainGui = new JFrame();
		frameList.add(MainGui);
		MainGui.setLayout(new BorderLayout(10, 5));
		this.MainGui.setTitle("Pay by Cash page");
		this.MainGui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.MainGui.setSize(500,400);
		this.MainGui.setLocationRelativeTo(null);
		this.MainGui.setVisible(true);
		
		// this section of panel is for control buttons
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		
		//panel.setBackground(Color.red);
		
		Button paybutton = new Button("Pay"); // got to add on click start sesion and switch to main window
		paybutton.setActionCommand(PAY);
		paybutton.addActionListener(this);
		panel.add(paybutton);
		
		Button additembutton = new Button("add item"); // got to add on click start sesion and switch to main window
		additembutton.setActionCommand(ADD_ITEM);
		additembutton.addActionListener(this);
		panel.add(additembutton);
		
		Button removeitembutton = new Button("remove item"); // got to add on click start sesion and switch to main window
		removeitembutton.setActionCommand(REMOVE_ITEM);
		removeitembutton.addActionListener(this);
		panel.add(removeitembutton);
		
		
		Button notifyatendentbutton = new Button("call for help"); // got to add on click start sesion and switch to main window
		notifyatendentbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Use this section to add code after button push
				systemManager.notifyAttendant("Checkoutstation needs help");
			}
			
		});
		panel.add(notifyatendentbutton);
		
		
		Button membershipbutton = new Button("add membership number"); // got to add on click start sesion and switch to main window
		membershipbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Use this section to add code after button push
				new membershipnumbergui();
			}
			
		});
		panel.add(membershipbutton);
		
		
		MainGui.add(panel, BorderLayout.CENTER);
		
		//this section is to display added items and current price
		JPanel displayPanel = new JPanel();
		//displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
		//displayPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
    	//centralPanel.add(new CartProduct(product1, session, new Mass(product1.getExpectedWeight())));
    	//centralPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		displayPanel.setBackground(Color.gray);
		displayPanel.setLayout(new GridLayout(20,1));
       // GridBagConstraints gbc = new GridBagConstraints();
    	//JScrollPane scroll = new JScrollPane(displayPanel);
    	//scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    	//scroll.setBackground(Color.red);
    //	displayPanel.setLayout(new BorderLayout());
	//	displayPanel.setLayout(new FlowLayout().RIGHT);
    	//scroll.add(displayPanel);
		ArrayList<Object> list = new ArrayList<Object>();
		
		for (Product i : orderManager.getProducts()) { // this should work for show items added does not due to of the same
			// checking for null
			num = num +1;
			if (i == null) {
				
			} 
			if (i instanceof BarcodedProduct) {
				
				BarcodedProduct name = (BarcodedProduct) i;
				
				JLabel product = new JLabel(num + "     " + name.getDescription()+ "      $"+ name.getPrice());
				list.add(product);
				//displayPanel.add(product);
				continue;
			}
			
			if (i instanceof PLUCodedProduct) {
				PLUCodedProduct name = (PLUCodedProduct) i;
				JLabel product = new JLabel(num + "     " + name.getDescription()+ "      $"+ name.getPrice()+"\n");
				list.add(product);
			} 
			}
		for (int j = 0; j < list.size(); j++) {
			displayPanel.add((Component) list.get(j));
		
			}
		
		JLabel cartInfo = new JLabel("mass: " + systemManager.getExpectedMass()+"          Remaining  $" + systemManager.getRemainingBalance());
		displayPanel.add(cartInfo);
		
		///
		///for loop in for loop
		//products = orderManager.getProducts(); // cant get the list to work yet
		
    	
		MainGui.add(displayPanel, BorderLayout.WEST);
		
		
			
		}
		
		
	

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (PAY.equals(command)) {
			new PayGui();
			
		} else if (ADD_ITEM.equals(command)) {
			MainGui.dispose();
			new addItemGui();
		} else if(REMOVE_ITEM.equals(command)) {
			new removeItemGUI();
		}
		
	}
	
}

