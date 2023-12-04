// Aleksandr Sokolov (30191754)
// Azariah Francisco (30085863)
// Brandon Smith (30141515)
// Carlos Serrouya (30192761)
// Diego de Jaraiz (30176017)
// Emily Willams (30122865)
// Evan Ficzere (30192404)
// Jaden Taylor (30113034)
// Joshua Bourchier (30194364)
// Justine Mangaliman (30164741)
// Kaelin Good (30092239)
// Laura Yang（30156356)
// Myra Latif (30171760)
// Noelle Thundathil (30115430)
// Raj Rawat (30173990)
// Roshan Patel (30184010)
// Sam Fasakin (30161903)
// Simon Bondad (30163401)
// Simon Oseen (30144175)
// Sohaib Zia (30160114)
// Sunny Hoang (30170708)
// Yasemin Khanmoradi (30066537)

package GUIcomponents.GuiForI3;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.naming.OperationNotSupportedException;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jjjwelectronics.card.Card;

public class PayGui extends Simulation {
	
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
		
		Button paybytapbutton = new Button("Pay by tap"); // since there is no tapcode in payment manager just reusing swipe
		paybytapbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Use this section to add code after button push
				try {
					payman.swipeCard(new Card("credit", "111111111", "Bob Bob", "123", "1234", true, true)); // this just re uses swipe code
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				new ReciptGui();
				//systemManager.
				payFrame.dispose();
			}
			
		});
		panel.add(paybytapbutton);
		
		Button paybyswipebutton = new Button("pay by swipe"); // this should work just need to add the payment checker stuff
		paybyswipebutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Use this section to add code after button push
				try {
					payman.swipeCard(new Card("credit", "111111111", "Bob Bob", "123", "1234", true, true)); // this should be a good card
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
		panel.add(paybyswipebutton);
		
		Button paybyinsertbutton = new Button("pay by insert"); // no insert code just reusing swipe again
		paybytapbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Use this section to add code after button push
				try {
					payman.swipeCard(new Card("credit", "111111111", "Bob Bob", "123", "1234", true, true)); // this just re uses swipe code
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
		panel.add(paybyinsertbutton);
		
		Button paybycoinbutton = new Button("pay by coin/cash"); // switchs to coin gui
		paybycoinbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new paybyCashGui();
				payFrame.dispose();
				
				
			}
		});
		panel.add(paybycoinbutton);
		
		
		Button closebutton = new Button("Close pay"); // closes the pay gui need to add session changer stuff
		paybytapbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Use this section to add code after button push
				payFrame.dispose();
				
			}
			
		});
		panel.add(paybyinsertbutton);
		
		
		
		payFrame.add(panel, BorderLayout.CENTER);
	}

}
