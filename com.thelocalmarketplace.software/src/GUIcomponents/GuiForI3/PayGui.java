package GUIcomponents.GuiForI3;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.OperationNotSupportedException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PayGui extends Launcher {
	
	private JFrame payFrame;
	
	PayGui() {
		initialize();
	}
	
	public void initialize() {
		payFrame = new JFrame();
		payFrame.setLayout(new BorderLayout(10, 5));
		this.payFrame.setTitle("payPage");
		this.payFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.payFrame.setSize(500,400);
		this.payFrame.setLocationRelativeTo(null);
		this.payFrame.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		
		panel.setBackground(Color.red);
		
		Button paybytapbutton = new Button("Pay by tap"); // got to add on click start sesion and switch to main window
		panel.add(paybytapbutton);
		
		Button paybyswipebutton = new Button("pay by swipe"); // got to add on click start sesion and switch to main window
		paybyswipebutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Use this section to add code after button push
				
				
			}
			
		});
		panel.add(paybyswipebutton);
		
		Button paybyinsertbutton = new Button("pay by insert"); // got to add on click start sesion and switch to main window
		panel.add(paybyinsertbutton);
		
		Button paybycoinbutton = new Button("pay by coin"); // got to add on click start sesion and switch to main window
		paybycoinbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new paybyCashGui();
				payFrame.dispose();
				
				
			}
		});
		panel.add(paybycoinbutton);
		
		Button paybybillbutton = new Button("pay by bill"); // got to add on click start sesion and switch to main window
		panel.add(paybybillbutton);
		
		
		payFrame.add(panel, BorderLayout.CENTER);
	}

}
