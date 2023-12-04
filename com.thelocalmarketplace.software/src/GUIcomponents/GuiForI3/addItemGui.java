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
	public static final String ADD_VISUAL_CATALOGUE_ITEM = "visual";
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
		
		Button addItemByVisualCatalogueButton = new Button("add item with visual catalogue"); // got to add on click start sesion and switch to main window
		addItemByVisualCatalogueButton.setActionCommand(ADD_VISUAL_CATALOGUE_ITEM);
		addItemByVisualCatalogueButton.addActionListener(this);
		panel.add(addItemByVisualCatalogueButton);
		
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
			
		} else if (command.equals(ADD_PLUCODED_ITEM)) { // dont know how to add plu item	`
			// add a text box and an enter button so that when the enter button is click it runs this code no matter what they put in the text box
			Simulation.station.getMainScanner().scan((BarcodedItem)Simulation.itemsToAdd.get(1).item);
			addItemGui.dispose(); 
			new MainGui();
		} else if (command.equals(ADD_VISUAL_CATALOGUE_ITEM)) {
			new VisualCatalogueGUI();
		}
		
	}

}