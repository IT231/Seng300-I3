package GUIcomponents.GuiForI3;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class adminGUI {
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
		
		Button unblockbutton = new Button("unblock"); // got to add on click start sesion and switch to main window
		unblockbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//unblock session 
			}
			
		});
		
		adminFrame.add(panel, BorderLayout.CENTER);
	}
}




