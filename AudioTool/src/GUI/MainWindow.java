package GUI;

import javax.swing.JFrame;

import Common.Signal;
import Controller.MainController;

public class MainWindow extends JFrame
{
	MainController controller;
	
	List<FilterPanel> filterpanels;
	List<SingalPanel> signalpanels;
	
	
	public MainWindow(MainController controller)
	{
		this.controller = controller;
		//window erzeugen und FilterPanels hinzufügen
	}
	
	
	public void addSignal(Signal signal)
	{
		//Creates a new Tab with the Signal
	}
	
	
}
