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
