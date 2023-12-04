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
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class membershipnumbergui extends Simulation {
private JFrame startFrame;
	
membershipnumbergui() {
		initialize();
	}
	
	public void initialize() {
		startFrame = new JFrame();
		startFrame.setLayout(new BorderLayout(10, 5));
		this.startFrame.setTitle("membershipPage");
		this.startFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.startFrame.setSize(500,400);
		this.startFrame.setLocationRelativeTo(null);
		this.startFrame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 50));
		
		panel.setBackground(Color.gray);
		JTextField membershipnum = new JTextField(20);
	//	Button endbutton = new Button("End checkout"); // got to add on click start sesion and switch to main window
		JButton enter = new JButton("Enter");
		enter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//if(systemManager.getSessionState() == SessionStatus.BLOCKED) {
				membershipnum.getText();
				}
				//systemManager.unblockSession();
		//	}
			
		});
		panel.add(enter);
		
		panel.add(membershipnum);
		
		startFrame.add(panel, BorderLayout.CENTER);

}
}
