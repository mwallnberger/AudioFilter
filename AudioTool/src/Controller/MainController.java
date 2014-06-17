package Controller;

import java.io.File;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.UIManager;
import Common.GeneralException;
import Common.SaveSignal;
import Common.Signal;
import Filter.Filter;
import GUI.InnerTabPanel;
import GUI.MainWindow;
import GUI.TabPanel;
import GUI.elements.PlayingThread;
import IO.IOManager;

/**
 * MainController for AudioTool.
 */

public class MainController
{
	private final MainWindow window;
	private final TabPanel tabPanel;

	public MainController() {		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

		}
		window = new MainWindow(this);
		tabPanel = window.getTabPanel();
	}
	
	public boolean signalExists(Signal signal) {
		List<Signal> signals = tabPanel.getSignals();
		for(Signal s : signals) {
			if(s.getName().equals(signal.getName())) {
				return true;
			}
		}
		return false;		
	}
	
	public Signal loadFile(File file) throws GeneralException{
		List<Signal> signals = tabPanel.getSignals();
		for(Signal s : signals) {
			if(s.getName().equals(file.getName())) {
				throw new GeneralException("Es ist bereits eine Datei mit dem gleichen Namen geöffnet.");
			}
		}
		try {
			pauseAllPlaying();
			return IOManager.importFile(file);
		} catch (GeneralException e) {
			throw e;
		}	
	}
	
	public boolean export(File file, Signal signal) throws GeneralException{
		try {
			IOManager.exportFile(file, signal);
		} catch (GeneralException e) {
			throw e;
		}	
		return true;
	}
	
	public boolean closeSignal(Signal signal) {
		try {
			if(!SaveSignal.saveSignalIfChanged(tabPanel.getComponentOf(signal), signal, this)) {
				return false;
			}
		} catch (GeneralException e) {
		}
		tabPanel.removeTabOf(signal);
		return true;
	}
	
	public void performFilter(Filter filter) {
		filter.performFiltering();
	}	
	
	public JFrame getMainWindow() {
		return window;
	}
	
	public Signal getActiveSignal() {
		return tabPanel.getActiveSignal();
	}
	
	public List<Signal> getSignals() {
		return tabPanel.getSignals();
	}
	
	public List<InnerTabPanel> getTabs() {
		return tabPanel.getTabs();
	}
	
	public PlayingThread getPlayingThread(Signal s) {
		return tabPanel.getComponentOf(s).getThread();
	}
	
	public void tooglePlaying(Signal s) {
		PlayingThread playThread = tabPanel.getComponentOf(s).getThread();
		if(playThread != null) {
			if(!playThread.isPlaying() || playThread.isPaused()) {
				playThread.startPlaying();
			}
			else {
				playThread.pausePlaying();
			}
		}
	}
	
	public void startPlaying(Signal s) {
		PlayingThread thread = tabPanel.getComponentOf(s).getThread();
		if(thread != null) {
			thread.startPlaying();
		}
	}
	
	public void pausePlaying(Signal s) {
		PlayingThread thread = tabPanel.getComponentOf(s).getThread();
		if(thread != null) {
			thread.pausePlaying();
		}
	}
	
	public void stopPlaying(Signal s) {
		PlayingThread thread = tabPanel.getComponentOf(s).getThread();
		if(thread != null) {
			thread.stopPlaying();
		}
	}
	
	public void pauseAllPlaying() {
		List<InnerTabPanel> innerTabList = tabPanel.getTabs();
		innerTabList.forEach(t -> {
			t.getThread().pausePlaying();
		});
	}
	
	public void addNewTab(Signal s) throws GeneralException {
		if(signalExists(s)) {
			throw new GeneralException("Es ist bereits eine Datei mit dem gleichen Namen geöffnet.");
		}
		tabPanel.createNewTab(s);
	}
	
	public void renameTab(Signal s) {
		tabPanel.renameTab(s);
	}
	
}
