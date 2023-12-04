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

import managers.enums.SessionStatus;

public class adminGUI extends Simulation {
private JFrame adminFrame;
	
	adminGUI() {
		initialize();
	}
	
	public void initialize() {
		adminFrame = new JFrame();
		adminFrame.setLayout(new BorderLayout(10, 5));
		this.adminFrame.setTitle("adminPage");
		this.adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.adminFrame.setSize(500,400);
		this.adminFrame.setLocationRelativeTo(null);
		this.adminFrame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		
		//panel.setBackground(Color.red);
		
		Button unblockbutton = new Button("unblock"); // should unblock session
		unblockbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//if(systemManager.getSessionState() == SessionStatus.BLOCKED) {
					systemManager.unblockSession();
				}
				//systemManager.unblockSession();
		//	}
			
		});
		panel.add(unblockbutton);
		
		Button additembutton = new Button("add item"); // should unblock session
		additembutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new addItemGui();
			}
			
		});
		panel.add(additembutton);
		
		
		adminFrame.add(panel, BorderLayout.CENTER);
	}
}




