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
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.Product;

import managers.SystemManager;

// this one show recipt and take back to startwindow
public class ReciptGui extends Simulation implements ActionListener {
private JFrame startFrame;
public static final String END_SESSION = "end";
	
	public ReciptGui() {
		initialize();
	}
	
	public void initialize() {
		startFrame = new JFrame();
		startFrame.setLayout(new BorderLayout(10, 5));
		this.startFrame.setTitle("reciptPage");
		this.startFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.startFrame.setSize(500,400);
		this.startFrame.setLocationRelativeTo(null);
		this.startFrame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		
		panel.setBackground(Color.gray);
		
		Button endButton = new Button("End checkout"); //needs to close all process on click and then start Launcher again
		endButton.setActionCommand(END_SESSION);
		endButton.addActionListener(this);
		
		panel.add(endButton);
		startFrame.add(panel, BorderLayout.CENTER);
		
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
			if (i == null) {
				
			} 
			if (i instanceof BarcodedProduct) {
				
				BarcodedProduct name = (BarcodedProduct) i;
				
				JLabel product = new JLabel(name.getDescription()+ "      $"+ name.getPrice());
				list.add(product);
				//displayPanel.add(product);
				continue;
			}
			
			if (i instanceof PLUCodedProduct) {
				PLUCodedProduct name = (PLUCodedProduct) i;
				JLabel product = new JLabel(name.getDescription()+ "      $"+ name.getPrice()+"\n");
				list.add(product);
			}}
		for (int j = 0; j < list.size(); j++) {
			displayPanel.add((Component) list.get(j));
		
			}
		
		JLabel cartInfo = new JLabel("          Total Paid  $" + systemManager.getRemainingBalance()+"       ");
		displayPanel.add(cartInfo);
		if(Simulation.membership != null) {
			JLabel membership = new JLabel("Membership#  " + Simulation.membership);
			displayPanel.add(membership);
		}
		
		///
		///for loop in for loop
		//products = orderManager.getProducts(); // cant get the list to work yet
		
    	
		startFrame.add(displayPanel, BorderLayout.WEST);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
	
		if (command.equals(END_SESSION)) {		
			startFrame.dispose();
			systemManager.stopSession();

			for (int i = 0; i < frameList.size(); i++) {
				frameList.get(i).dispose();
				frameList.remove(frameList.get(i));
				i--;
			}

			for (int i = 0; i < orderManager.getProducts().size(); i++) {
				orderManager.getProducts().remove(orderManager.getProducts().get(i));
				--i;
			}

			List<Product> products = new ArrayList<Product>();
			systemManager.setProductListSM(products);

			new adminGUI();
			StartWindow startframe = new StartWindow();
		}
	}
}