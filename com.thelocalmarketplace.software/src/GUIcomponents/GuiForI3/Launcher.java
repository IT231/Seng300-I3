package GUIcomponents.GuiForI3;

import java.awt.event.WindowAdapter;

import javax.swing.SwingUtilities;


public class Launcher {

	public static void main(String[] args) {
		Simulation.start();
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new adminGUI();
				StartWindow startframe = new StartWindow();
				
			}
			
		});

	}
}


