package GUI.elements;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Common.GeneralException;
import Common.Signal;
import Controller.MainController;

public class SaveSignal{

	/**
	 * Saves controller.getActiveSignal()
	 * With errorhandling
	 * 
	 * @param component
	 * @param controller
	 * @throws GeneralException
	 * 			just when controller.getActiveSignal() returns null or controller == null
	 */	
	public static void save(Component component, MainController controller) throws GeneralException {
		save(component, controller.getActiveSignal(), controller);
	}
	
	
	/**
	 * Saves controller.getActiveSignal()
	 * With errorhandling
	 * 
	 * @param component
	 * @param controller
	 * @throws GeneralException
	 * 			just when signal or controller is null
	 */	
	public static void save(Component component, Signal signal, MainController controller) throws GeneralException {
		
		if(controller == null || signal == null) {
			throw new GeneralException("Es ist kein Signal geöffnet.");
		}
		
		if(signal.isChanged()) {
    		int savesuccess = 0;
    		while(savesuccess < 1) {
    			if(savesuccess == 0) {
    				if(JOptionPane.showConfirmDialog(component, 
    			            "Möchten Sie die änderungen am Signal " + signal.getName() + " speichern?", "Änderungen speichern?", 
    			            JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
    					savesuccess = 1;
    					break;
    				}
    			}
    			
        		if(savesuccess <= 0) {
        			
        			JFileChooser c = new JFileChooser();
        			FileFilter filter = new FileNameExtensionFilter("WAV File","wav");
        			c.setFileFilter(filter);
        			if (c.showSaveDialog(component) == JFileChooser.APPROVE_OPTION) {
        				try {
        					File file = new File(c.getSelectedFile().getAbsolutePath());
        					if(!filter.accept(file)) {
        						file = new File(file.getAbsolutePath() + ".wav");
        					}
							controller.export(file, signal);
							signal.resetChanged();
	        				savesuccess = 1;
						} catch (GeneralException e) {
							if(JOptionPane.showConfirmDialog(component, 
						        "Fehler beim Speichern der Datei! \r\nMöchten Sie den Vorgang wiederholen?", "Fehler beim Speichern der Datei " + signal.getName(), 
						        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {	
								
								savesuccess = -1;
								
							}
						    else {
						    	savesuccess = 1;
						    }
						}
        				
        			}
				}
    		}
    	}
		
		

		
	}

}
