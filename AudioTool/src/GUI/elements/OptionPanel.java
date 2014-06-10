package GUI.elements;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import GUI.TPFilterPanel;

public class OptionPanel extends JPanel {

	public OptionPanel() {
		this.setLayout(new GridLayout(3, 0));
		JButton tpfilter = new JButton("TPFilter");
		
		tpfilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame popup = new JFrame("TPFilter"); 
				popup.setBounds(1200, 100, 500, 500);
				popup.add(new TPFilterPanel(popup));
				popup.pack();
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
