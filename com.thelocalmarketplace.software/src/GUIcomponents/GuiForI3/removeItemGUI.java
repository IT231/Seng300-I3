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
	
	removeItemGUI() {
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
				orderManager.getProducts().remove(orderManager.getProducts().size()-1);
			}
			
		});
		panel.add(removebutton);
		removeItemFrame.add(panel, BorderLayout.CENTER);
	}
}




