package GUI.elements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import Common.GeneralException;
import Common.Signal;
import Controller.MainController;

public class OpenSignalActionHandler implements ActionListener{

	
	
	private TabPanel tabPanel;
	private MainController controller;
	
	public OpenSignalActionHandler(TabPanel tabPanel, MainController controller) {
		this.tabPanel = tabPanel;
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser c = new JFileChooser();
		
	    if (c.showOpenDialog(tabPanel) == JFileChooser.APPROVE_OPTION) {
	    	Signal signal;
			try {
				signal = controller.loadFile(c.getSelectedFile());
				tabPanel.createNewTab(signal);
	    		tabPanel.repaint();
			} catch (GeneralException e1) {
				JOptionPane.showMessageDialog(controller.getMainWindow(), e1.toString(), "Fehler", JOptionPane.OK_OPTION);
			}
		}
	}

}
