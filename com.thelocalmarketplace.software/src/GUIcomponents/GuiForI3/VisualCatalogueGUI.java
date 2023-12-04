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
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jjjwelectronics.scanner.BarcodedItem;

public class VisualCatalogueGUI extends Simulation implements ActionListener {
	private JFrame visualCatalogueFrame;
	
	public static final String APPLE_JUICE = "applejuice";
	public static final String ORANGE_JUICE = "orangejuice";
	
	VisualCatalogueGUI() {
		initialize();
	}
	
	public void initialize() {
		visualCatalogueFrame = new JFrame();
		visualCatalogueFrame.setLayout(new BorderLayout(10, 5));
		this.visualCatalogueFrame.setTitle("adminPage");
		this.visualCatalogueFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.visualCatalogueFrame.setSize(500,400);
		this.visualCatalogueFrame.setLocationRelativeTo(null);
		this.visualCatalogueFrame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		
		Button addAppleJuice = new Button("Apple Juice");
		addAppleJuice.setActionCommand(APPLE_JUICE);
		addAppleJuice.addActionListener(this);
		
		Button addOrangeJuice = new Button("Orange Juice");
		addAppleJuice.setActionCommand(ORANGE_JUICE);
		addAppleJuice.addActionListener(this);
		
		panel.add(addAppleJuice);
		panel.add(addOrangeJuice);
		visualCatalogueFrame.add(panel, BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		
		if (command.equals(APPLE_JUICE)) {		
			Simulation.station.getMainScanner().scan((BarcodedItem)Simulation.itemsToAdd.get(0).item);
			visualCatalogueFrame.dispose(); 
			new MainGui();
		}
		
		if (command.equals(ORANGE_JUICE)) {		
			Simulation.station.getMainScanner().scan((BarcodedItem)Simulation.itemsToAdd.get(1).item);
			visualCatalogueFrame.dispose(); 
			new MainGui();
		}
	}
}