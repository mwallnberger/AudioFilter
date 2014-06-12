package GUI.elements;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Controller.MainController;
import GUI.GeneralFilterPanel;

public class OptionPanel extends JPanel {

	private final MainController controller;
	
	public OptionPanel(MainController controller) {
		this.controller = controller;
		this.setLayout(new GridLayout(5, 0));
		JButton tpfilter = new JButton("TPFilter");
		
		tpfilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog popup = new GeneralFilterPanel(controller.getMainWindow()); 
				popup.pack();
				popup.setLocationRelativeTo(controller.getMainWindow());
				popup.setVisible(true);
			}
		});
		

		JButton hpfilter = new JButton("HPFilter");
		JButton play = new JButton("Play");
		play.setBackground(Color.red);
		
		this.add(tpfilter);
		this.add(hpfilter);
		this.add(play);
		
	}
	
}
