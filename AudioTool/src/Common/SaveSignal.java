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
	 * 
	 * @return saveok		true if saveing was ok (or user desided not to save)
	 * 		
	 * @throws GeneralException
	 * 			just when controller.getActiveSignal() returns null or controller == null
	 */	
	public static boolean saveActiveSignalIfChanged(Component component, MainController controller) throws GeneralException {
		return save(component, controller.getActiveSignal(), controller, 0, false);
	}
	
	/**
	 * Saves signal if changed.
	 * always asking Question to save.
	 * With errorhandling.
	 * 
	 * @param component
	 * @param controller
	 * 
	 * @return saveok		true if saveing was ok (or user desided not to save)
	 * 		
	 * @throws GeneralException
	 * 			just when controller.getActiveSignal() returns null or controller == null
	 */	
	public static boolean saveSignalIfChanged(Component component, Signal signal, MainController controller) throws GeneralException {
		return save(component, signal, controller, 0, false);
	}
	
	
	/**
	 * Saves controller.getActiveSignal().
	 * With errorhandling
	 * 
	 * @param component
	 * @param controller
	 * @param askQuestion	0 - dialog asking to save
	 * 						-1 - no dialog asking to save
	 * 
	 * @return saveok		true if saveing was ok (or user desided not to save)
	 * 		
	 * @throws GeneralException
	 * 			just when signal or controller is null
	 */	
	public static boolean saveActiveSignal(Component component, MainController controller, int askQuestion, boolean saveWithoutChanges) throws GeneralException {
		return save(component, controller.getActiveSignal(), controller, askQuestion, saveWithoutChanges);
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
	 * 
	 * @return saveok		true if saveing was ok (or user desided not to save)
	 * 						false if user aborted
	 * @throws GeneralException
	 * 			just when signal or controller is null
	 */	
	private static boolean save(Component component, Signal signal, MainController controller, int askQuestion, boolean saveWithoutChanges) throws GeneralException {
		
		if(controller == null || signal == null) {
			throw new GeneralException("Es ist kein Signal geöffnet.");
		}
		
		if(askQuestion < -1 || askQuestion > 0) {
			askQuestion = 0;
		}
		
		if(signal.isChanged() || saveWithoutChanges) {
    		while(askQuestion < 1) {
    			int desicion = 0;
    			if(askQuestion == 0) {
    				desicion = JOptionPane.showConfirmDialog(component, 
    			            "Möchten Sie die änderungen am Signal " + signal.getName() + " speichern?", "Änderungen speichern?", 
    			            JOptionPane.YES_NO_OPTION);
    				
    				if (desicion == JOptionPane.NO_OPTION) {
    					return true;
    				}
    				if(desicion == JOptionPane.CANCEL_OPTION || desicion == JOptionPane.CLOSED_OPTION) {
    					return false;
    				}
    			}
    			
        		if(askQuestion <= 0) {
        			JFileChooser c = new JFileChooser();
        			FileFilter filter = new FileNameExtensionFilter("WAV File","wav");
        			c.setFileFilter(filter);
        			desicion = c.showSaveDialog(component);
        			if (desicion == JFileChooser.APPROVE_OPTION) {
        				try {
        					File file = new File(c.getSelectedFile().getAbsolutePath());
        					if(!filter.accept(file)) {
        						file = new File(file.getAbsolutePath() + ".wav");
        					}
        					if(file.exists()) {
        						if(JOptionPane.showConfirmDialog(c, "Die Datei existiert bereits.\r\nMöchten Sie die Datei Überschreiben", "Datei " + signal.getName() + " existiert bereits", 
						        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
        							controller.pauseAllPlaying();
        							controller.export(file, signal);
        							signal.setName(file.getName());
        							controller.renameTab(signal);
        							signal.resetChanged();
        							return true;
        						}
        						else {
        							askQuestion = -1;
        						}
        					}
        					
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
        				return false;
        			}
				}
    		}
    	}
		return true;
		
		

		
	}

}
