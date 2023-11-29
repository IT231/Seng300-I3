package GUIcomponents.GuiForI3;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainGui {

private JFrame MainFrame;
	
	MainGui() {
		initialize();
	}
	
	public void initialize() {
		MainFrame = new JFrame();
		MainFrame.setLayout(new BorderLayout(10, 5));
		this.MainFrame.setTitle("mainPage");
		this.MainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.MainFrame.setSize(500,400);
		this.MainFrame.setLocationRelativeTo(null);
		this.MainFrame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		
		//panel.setBackground(Color.red);
		
		Button paybutton = new Button("Pay"); // got to add on click start sesion and switch to main window
		paybutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Use this section to add code after button push
				new PayGui();
				//startFrame.dispose(); 
			}
			
		});
		panel.add(paybutton);
		
		Button additembutton = new Button("add item"); // got to add on click start sesion and switch to main window
		additembutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Use this section to add code after button push
				new addItemGui();
				//startFrame.dispose(); 
			}
			
		});
		panel.add(additembutton);
		
		Button removeitembuttom = new Button("remove item"); // got to add on click start sesion and switch to main window
		removeitembuttom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Use this section to add code after button push
				new removeItemGUI();
				//startFrame.dispose(); 
			}
			
		});
		panel.add(removeitembuttom);
		MainFrame.add(panel, BorderLayout.CENTER);
	}
}

