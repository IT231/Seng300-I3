package GUIcomponents.GuiForI3;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.jjjwelectronics.scanner.BarcodedItem;

public class addItemGui extends Simulation implements ActionListener {
	
	public static final String SCAN_BARCODED_ITEM = "barcoded";
	public static final String ADD_PLUCODED_ITEM = "plu";
	private JFrame addItemGui;

	addItemGui() {
		initialize();
	}
	
	public void initialize() {
		addItemGui = new JFrame();
		addItemGui.setLayout(new BorderLayout(10, 5));
		this.addItemGui.setTitle("Pay by Cash page");
		this.addItemGui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addItemGui.setSize(500,400);
		this.addItemGui.setLocationRelativeTo(null);
		this.addItemGui.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		
		panel.setBackground(Color.red);
		
		Button addItembybarcodebutton = new Button("add item with Barcodescanner"); // got to add on click start sesion and switch to main window
		addItembybarcodebutton.setActionCommand(SCAN_BARCODED_ITEM);
		addItembybarcodebutton.addActionListener(this);
		panel.add(addItembybarcodebutton);
		
		Button addItembyitemlookupbutton = new Button("add item with look up"); // got to add on click start sesion and switch to main window
		addItembyitemlookupbutton.setActionCommand(ADD_PLUCODED_ITEM);
		addItembyitemlookupbutton.addActionListener(this);
		panel.add(addItembyitemlookupbutton);
		
		addItemGui.add(panel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		if (command.equals(SCAN_BARCODED_ITEM)) {
			
			Simulation.station.getMainScanner().scan((BarcodedItem)Simulation.itemsToAdd.get(0).item);
//			try {
//				Simulation.systemManager.addItemToOrder((BarcodedItem)Simulation.itemsToAdd.get(0).item, ScanType.MAIN);
//				System.out.println("went past");
//			} catch (OperationNotSupportedException e1) {
//				e1.printStackTrace();
//				System.out.println("this is called");
//			}
			
			addItemGui.dispose(); 
			new MainGui();
			
		} else if (command.equals(ADD_PLUCODED_ITEM)) {
			
		}
		
	}

}



