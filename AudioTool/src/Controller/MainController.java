package Controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.UIManager;

import Common.GeneralException;
import Common.Signal;
import Filter.Filter;
import GUI.MainWindow;
import GUI.elements.PlayingThread;
import GUI.elements.TabPanel;
import IO.IOManager;

public class MainController
{
	private final MainWindow window;
	private final TabPanel tabPanel;
	private final Map<Signal, PlayingThread> playThreads;

	public MainController() {
		playThreads = new HashMap<Signal, PlayingThread>();
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

		}
		window = new MainWindow(this);
		tabPanel = window.getTabPanel();
	}
	
	public Signal loadFile(File file) throws GeneralException{
		for(Signal s : tabPanel.getSignals()) {
			if(s.getName().equals(file.getName())) {
				throw new GeneralException("Es ist bereits eine Datei mit dem gleichen Namen geöffnet.");
			}
		}
		try {
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
	
	public boolean signalClose(Signal signal) {
		//wiedergabe stoppen
		PlayingThread playThread = playThreads.get(signal);
		if(playThread != null) {
			while(playThread.isPlaying()) {
				playThread.stopPlaying();
			}
		}
		return tabPanel.removeSignal(signal) && removePlayingThread(signal);
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
	
	public void addPlayingThread(PlayingThread playThread, Signal signal) {
		playThreads.put(signal, playThread);
	}
	
	public PlayingThread getPlayingThread(Signal s) {
		return playThreads.get(s);
	}
	
	public void tooglePlaying(Signal s) {
		PlayingThread playThread = playThreads.get(s);
		if(playThread != null) {
			if(!playThread.isPlaying() || playThread.isPaused()) {
				playThread.startPlaying();
			}
			else {
				playThread.pausePlaying();
			}
		}
	}
	
//	public void startPlaying(Signal s) {
//		playThreads.get(s).startPlaying();
//	}
	
	public void stopPlaying(Signal s) {
		playThreads.get(s).stopPlaying();
	}
	
	private boolean removePlayingThread(Signal s) {
		if(playThreads.remove(s) != null) {
			return true;
		}
		return false;
	}

	
}
