package GuiForI3;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

// this one show recipt and take back to startwindow
public class ReciptGui {
private JFrame startFrame;
	
	ReciptGui() {
		initialize();
	}
	
	public void initialize() {
		startFrame = new JFrame();
		startFrame.setLayout(new BorderLayout(10, 5));
		this.startFrame.setTitle("reciptPage");
		this.startFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.startFrame.setSize(500,400);
		this.startFrame.setLocationRelativeTo(null);
		this.startFrame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		
		panel.setBackground(Color.gray);
		
		Button endbutton = new Button("End checkout"); // got to add on click start sesion and switch to main window
		panel.add(endbutton);
		startFrame.add(panel, BorderLayout.CENTER);

}
}
