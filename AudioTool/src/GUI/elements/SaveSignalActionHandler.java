package GUI.elements;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import Common.GeneralException;
import Common.Signal;
import Controller.MainController;
import IO.FileType;

public class SaveSignalActionHandler implements ActionListener{

	private Component component;
	private MainController controller;
	
	public SaveSignalActionHandler(Component component, MainController controller) {
		this.component = component;
		this.controller = controller;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0){
		JFileChooser c = new JFileChooser();
		
	    if (c.showOpenDialog(component) == JFileChooser.APPROVE_OPTION) {
			try {
				Signal signal = controller.getActiveSignal();
				if(signal == null) {
					throw new GeneralException("Actives Signal konnte nicht gefunden werden.");
				}
				controller.export(c.getSelectedFile(), signal);
				signal.resetChanged();
			} catch (GeneralException e1) {
				JOptionPane.showMessageDialog(controller.getMainWindow(), e1.toString(), "Fehler", JOptionPane.OK_OPTION);
			}
		}
		
	}

}
