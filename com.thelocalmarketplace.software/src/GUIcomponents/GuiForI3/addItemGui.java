package GUIcomponents.GuiForI3;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.OperationNotSupportedException;
import javax.swing.JFrame;
import javax.swing.JPanel;

import driver.Driver;
import utils.DriverHelper;

public class addItemGui extends Launcher {
	

private JFrame addItemFrame;
	
		addItemGui() {
		initialize();
	}
	
	public void initialize() {
		addItemFrame = new JFrame();
		addItemFrame.setLayout(new BorderLayout(10, 5));
		this.addItemFrame.setTitle("payPage");
		this.addItemFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addItemFrame.setSize(500,400);
		this.addItemFrame.setLocationRelativeTo(null);
		this.addItemFrame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		
		panel.setBackground(Color.red);
		
		Button addItembybarcodebutton = new Button("add item with Barcodescanner"); // got to add on click start sesion and switch to main window
		addItembybarcodebutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Use this section to add code after button push
				
				try {
					dd.scanItem();
					System.out.println("went past");
				} catch (OperationNotSupportedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("this is called");
				}
				addItemFrame.dispose(); 
			}
			
		});
		panel.add(addItembybarcodebutton);
		
		Button addItembyitemlookupbutton = new Button("add item with look up"); // got to add on click start sesion and switch to main window
		panel.add(addItembyitemlookupbutton);
		
		//Button addItembybarcodebutton = new Button("pay by insert"); // got to add on click start sesion and switch to main window
		//panel.add(paybyinsertbutton);
		
		//Button addItembybarcodebutton = new Button("pay by coin"); // got to add on click start sesion and switch to main window
		//panel.add(paybycoinbutton);
		
		//Button addItembybarcodebutton = new Button("pay by bill"); // got to add on click start sesion and switch to main window
		//panel.add(paybybillbutton);
		
		
		addItemFrame.add(panel, BorderLayout.CENTER);
	}

}



