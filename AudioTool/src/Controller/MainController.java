package Controller;

import java.io.File;
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
	

	public MainController() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

		}
		window = new MainWindow(this);
	}
	
	public Signal loadFile(File file) {
		try {
			return IOManager.importFile(file);
		} catch (GeneralException e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	public boolean export(File file, Signal signal, FileType type) {
		try {
			IOManager.exportFile(file, signal, type);
		} catch (GeneralException e) {
			e.printStackTrace();
		}	
		return true;
	}
	
	public boolean signalClose(Signal so) {
		//TODO
		return true;
	}
	
	public void performFilter(Filter filter) {
		filter.performFiltering();
	}	
	
	public Signal getActiveSignal() {
		return window.getActiveSignal();
	}
	
	public JFrame getMainWindow() {
		return window;
	}
	
}
