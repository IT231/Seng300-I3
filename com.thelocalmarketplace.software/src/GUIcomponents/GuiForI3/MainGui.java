package GUIcomponents.GuiForI3;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainGui extends Simulation implements ActionListener {

	public static final String PAY = "pay";
	public static final String ADD_ITEM = "add_item";
	public static final String REMOVE_ITEM = "remove_item";
	private JFrame MainGui;
	
	MainGui() {
		initialize();
	}
	
	public void initialize() {
		MainGui = new JFrame();
		MainGui.setLayout(new BorderLayout(10, 5));
		this.MainGui.setTitle("Pay by Cash page");
		this.MainGui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.MainGui.setSize(500,400);
		this.MainGui.setLocationRelativeTo(null);
		this.MainGui.setVisible(true);
		
		// this section of panel is for control buttons
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
		MainGui.add(panel, BorderLayout.CENTER);
		
		//this section is to display added items and current price
		JPanel displayplanel = new JPanel();
		//JList bagarea = new JList();
		displayplanel.add(new JScrollPane());
		displayplanel.setBackground(Color.red);
		MainGui.add(displayplanel, BorderLayout.WEST);
		
		
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

