package Common;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Controller.MainController;

public class SaveSignal{

	/**
	 * Saves controller.getActiveSignal() if changed.
	 * always asking Question to save.
	 * With errorhandling.
	 * 
	 * @param component
	 * @param controller
	 * @throws GeneralException
	 * 			just when controller.getActiveSignal() returns null or controller == null
	 */	
	public static void saveActiveSignalIfChanged(Component component, MainController controller) throws GeneralException {
		save(component, controller.getActiveSignal(), controller, 0, false);
	}
	
	/**
	 * Saves signal if changed.
	 * always asking Question to save.
	 * With errorhandling.
	 * 
	 * @param component
	 * @param controller
	 * @throws GeneralException
	 * 			just when controller.getActiveSignal() returns null or controller == null
	 */	
	public static void saveSignalIfChanged(Component component, Signal signal, MainController controller) throws GeneralException {
		save(component, controller.getActiveSignal(), controller, 0, false);
	}
	
	
	/**
	 * Saves controller.getActiveSignal().
	 * With errorhandling
	 * 
	 * @param component
	 * @param controller
	 * @param askQuestion	0 - dialog asking to save
	 * 						-1 - no dialog asking to save
	 * @throws GeneralException
	 * 			just when signal or controller is null
	 */	
	public static void saveActiveSignal(Component component, MainController controller, int askQuestion, boolean saveWithoutChanges) throws GeneralException {
		save(component, controller.getActiveSignal(), controller, askQuestion, saveWithoutChanges);
	}
	
	/**
	 * Saves signal.
	 * With errorhandling
	 * 
	 * @param component
	 * @param controller
	 * @param askQuestion	0 - dialog asking to save
	 * 						-1 - no dialog asking to save
	 * @param saveWithoutChanges ture signal will be saved even without signalchanged
	 * @throws GeneralException
	 * 			just when signal or controller is null
	 */	
	private static void save(Component component, Signal signal, MainController controller, int askQuestion, boolean saveWithoutChanges) throws GeneralException {
		
		if(controller == null || signal == null) {
			throw new GeneralException("Es ist kein Signal geöffnet.");
		}
		
		if(askQuestion < -1 || askQuestion > 0) {
			askQuestion = 0;
		}
		
		if(signal.isChanged() || saveWithoutChanges) {
    		while(askQuestion < 1) {
    			if(askQuestion == 0) {
    				if(JOptionPane.showConfirmDialog(component, 
    			            "Möchten Sie die änderungen am Signal " + signal.getName() + " speichern?", "Änderungen speichern?", 
    			            JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
    					askQuestion = 1;
    					break;
    				}
    			}
    			
        		if(askQuestion <= 0) {
        			
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
							askQuestion = 1;
						} catch (GeneralException e) {
							if(JOptionPane.showConfirmDialog(component, 
						        "Fehler beim Speichern der Datei! \r\nMöchten Sie den Vorgang wiederholen?", "Fehler beim Speichern der Datei " + signal.getName(), 
						        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {	
								
								askQuestion = -1;
								
								}
								else {
								    askQuestion = 1;
								}
						}
        				
        			}
        			else {
        				askQuestion = 1;
        			}
				}
    		}
    	}
		
		

		
	}

}
