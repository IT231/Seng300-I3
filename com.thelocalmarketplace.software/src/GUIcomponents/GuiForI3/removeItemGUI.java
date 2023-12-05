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

public class removeItemGUI extends Simulation {
private JFrame removeItemFrame;
	
	public removeItemGUI() {
		initialize();
	}
	
	public void initialize() {
		removeItemFrame = new JFrame();
		removeItemFrame.setLayout(new BorderLayout(10, 5));
		this.removeItemFrame.setTitle("adminPage");
		this.removeItemFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.removeItemFrame.setSize(500,400);
		this.removeItemFrame.setLocationRelativeTo(null);
		this.removeItemFrame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		
		//panel.setBackground(Color.red);
		
		Button removebutton = new Button("remove item"); // got to add on click start sesion and switch to main window
		removebutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//unblock session 
			}
			
		});
		panel.add(removebutton);
		removeItemFrame.add(panel, BorderLayout.CENTER);
	}
}




