package Controller;

import java.io.File;
import java.util.List;

import Common.GeneralException;
import Common.Signal;
import Filter.Filter;
import GUI.MainWindow;
import IO.FileType;
import IO.IOManager;

public class MainController
{
	List<Signal> signals;
	MainWindow window;
	

	public MainController() {
		window = new MainWindow(this);
	}
	
	public boolean loadFile(File file) {
		try {
			IOManager.importFile(file);
		} catch (GeneralException e) {
			e.printStackTrace();
		}	
		return true;
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
	
	public void performFilter(Filter filter, Signal signal) {
		filter.performFiltering(signal);
	}	
	
}
