package GUIcomponents.GuiForI3;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class StartWindow extends JFrame implements ActionListener {
	
	private JFrame startFrame;
	
	StartWindow() {
		initialize();
	}
	
	public void initialize() {
		startFrame = new JFrame();
		startFrame.setLayout(new BorderLayout(10, 5));
		this.startFrame.setTitle("StartPage");
		this.startFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.startFrame.setSize(500,400);
		this.startFrame.setLocationRelativeTo(null);
		this.startFrame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		
		panel.setBackground(Color.gray);
		
		Button button = new Button("Start checkout"); // got to add on click start sesion and switch to main window
		button.addActionListener(this);
		
		panel.add(button);
		startFrame.add(panel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Use this section to add code after button push
		new MainGui();
		startFrame.dispose(); 	
	}

}
