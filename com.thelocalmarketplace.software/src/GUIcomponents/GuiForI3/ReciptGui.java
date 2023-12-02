package GUIcomponents.GuiForI3;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.Product;

// this one show recipt and take back to startwindow
public class ReciptGui extends Simulation {
private JFrame startFrame;
	
	ReciptGui() {
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
		
		Button endbutton = new Button("End checkout"); //needs to close all process on click and then start Launcher again
		
		panel.add(endbutton);
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
		
		///
		///for loop in for loop
		//products = orderManager.getProducts(); // cant get the list to work yet
		
    	
		startFrame.add(displayPanel, BorderLayout.WEST);
	}
}