package Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Common.GeneralException;
import Common.Signal;
import Filter.Filter;
import GUI.MainWindow;
import IO.FileType;
import IO.IOManager;

public class MainController
{
	private final MainWindow window;
	private final List<Signal> signals;

	public MainController() {
		signals = new ArrayList<Signal>();
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

		}
		window = new MainWindow(this);
	}
	
	public Signal loadFile(File file) throws GeneralException{
		for(Signal s : signals) {
			if(s.getName().equals(file.getName())) {
				throw new GeneralException("Es ist bereits eine Datei mit dem gleichen Namen geöffnet.");
			}
		}
		try {
			Signal s = IOManager.importFile(file);
			signals.add(s);
			return s;
		} catch (GeneralException e) {
			throw e;
		}	
	}
	
	public boolean export(File file, Signal signal, FileType type) throws GeneralException{
		try {
			IOManager.exportFile(file, signal, type);
		} catch (GeneralException e) {
			e.printStackTrace();
		}	
		return true;
	}
	
	public boolean signalClose(Signal signal) {
		return signals.remove(signal);
	}
	
	public void performFilter(Filter filter) {
		filter.performFiltering();
	}	
	
//	public Signal getActiveSignal() {
//		return window.getActiveSignal();
//	}
	
	public JFrame getMainWindow() {
		return window;
	}
	
}
