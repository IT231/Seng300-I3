package GUIcomponents.GuiForI3;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainGui extends JFrame implements ActionListener {

	public static final String PAY = "pay";
	public static final String ADD_ITEM = "add_item";
	public static final String REMOVE_ITEM = "remove_item";
	
	MainGui() {
		initialize();
	}
	
	public void initialize() {
		setLayout(new BorderLayout(10, 5));
		setTitle("mainPage");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500,400);
		setLocationRelativeTo(null);
		setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		
		//panel.setBackground(Color.red);
		
		Button paybutton = new Button("Pay"); // got to add on click start sesion and switch to main window
		paybutton.setActionCommand(PAY);
		paybutton.addActionListener(this);
		panel.add(paybutton);
		
		Button additembutton = new Button("add item"); // got to add on click start sesion and switch to main window
		additembutton.setActionCommand(ADD_ITEM);
		additembutton.addActionListener(this);
		panel.add(additembutton);
		
		Button removeitembutton = new Button("remove item"); // got to add on click start sesion and switch to main window
		removeitembutton.setActionCommand(REMOVE_ITEM);
		removeitembutton.addActionListener(this);
		panel.add(removeitembutton);
		add(panel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (PAY.equals(command)) {
			new PayGui();
		} else if (ADD_ITEM.equals(command)) {
			new addItemGui();
		} else if(REMOVE_ITEM.equals(command)) {
			new removeItemGUI();
		}
		
	}
}

