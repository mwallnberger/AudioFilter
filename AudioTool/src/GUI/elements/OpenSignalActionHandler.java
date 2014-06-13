package GUI.elements;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

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
		FileFilter filter = new FileNameExtensionFilter("WAV File","wav");
		c.setFileFilter(filter);
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
