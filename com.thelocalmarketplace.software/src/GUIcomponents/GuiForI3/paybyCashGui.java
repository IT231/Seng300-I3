package GUIcomponents.GuiForI3;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Currency;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.tdc.banknote.Banknote;
import com.tdc.coin.Coin;

public class paybyCashGui extends Simulation {

private JFrame paybyCashGui;
	
paybyCashGui() {
		initialize();
	}
	
	public void initialize() {
		paybyCashGui = new JFrame();
		paybyCashGui.setLayout(new BorderLayout(10, 5));
		this.paybyCashGui.setTitle("Pay by Cash page");
		this.paybyCashGui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.paybyCashGui.setSize(500,400);
		this.paybyCashGui.setLocationRelativeTo(null);
		this.paybyCashGui.setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		
		panel.setBackground(Color.gray);
		
		Button fiftybutton = new Button("$50"); // got to add on click start sesion and switch to main window
		fiftybutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				payman.insertBanknote(new Banknote(Currency.getInstance("CAD"), new BigDecimal(50)));
				//System.out.println("made past it");
				
				
			}
			
		});
		panel.add(fiftybutton);
		
		Button twentybutton = new Button("$20"); // got to add on click start sesion and switch to main window
		twentybutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				payman.insertBanknote(new Banknote(Currency.getInstance("CAD"), new BigDecimal(20)));
				
				
			}
			
		});
		panel.add(twentybutton);
		
		Button tenbutton = new Button("$10"); // got to add on click start sesion and switch to main window
		tenbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				payman.insertBanknote(new Banknote(Currency.getInstance("CAD"), new BigDecimal(10)));
				
				
			}
			
		});
		panel.add(tenbutton);
		
		Button fivebutton = new Button("$5"); // got to add on click start sesion and switch to main window
		fivebutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				payman.insertBanknote(new Banknote(Currency.getInstance("CAD"), new BigDecimal(5)));
				
				
			}
			
		});
		panel.add(fivebutton);
		
		Button twobutton = new Button("$2"); // got to add on click start sesion and switch to main window
		twobutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				payman.insertCoin(new Coin(Currency.getInstance("CAD"), new BigDecimal(2)));
				
				
			}
			
		});
		panel.add(twobutton);
		Button onebutton = new Button("$1"); // got to add on click start sesion and switch to main window
		onebutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				payman.insertCoin(new Coin(Currency.getInstance("CAD"), new BigDecimal(1)));
				
				
			}
			
		});
		panel.add(onebutton);
		Button dottwentyfivebutton = new Button("$0.25"); // got to add on click start sesion and switch to main window
		dottwentyfivebutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				payman.insertCoin(new Coin(Currency.getInstance("CAD"), new BigDecimal(0.25)));
				
				
			}
			
		});
		panel.add(dottwentyfivebutton);
		
		Button dottenbutton = new Button("$0.10"); // got to add on click start sesion and switch to main window
		dottenbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				payman.insertCoin(new Coin(Currency.getInstance("CAD"), new BigDecimal(.1)));
				
			}
			
		});
		panel.add(dottenbutton);
		Button dotfivebutton = new Button("$0.05"); // got to add on click start sesion and switch to main window
		dotfivebutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				payman.insertCoin(new Coin(Currency.getInstance("CAD"), new BigDecimal(0.05)));
				
				
			}
			
		});
		panel.add(dotfivebutton);
		Button dotonebutton = new Button("$0.01"); // got to add on click start sesion and switch to main window
		dotonebutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				payman.insertCoin(new Coin(Currency.getInstance("CAD"), new BigDecimal(0.01)));
				
				
			}
			
		});
		
		panel.add(dotonebutton);
		Button closebutton = new Button("close"); // got to add on click start sesion and switch to main window
		closebutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				paybyCashGui.dispose();
				
				
			}
			
		});
		panel.add(closebutton);
		
		
		
		paybyCashGui.add(panel, BorderLayout.CENTER);
	}

}
