package GUI;

import java.util.List;
import javax.swing.JFrame;
import Common.Signal;
import Controller.MainController;

public class MainWindow extends JFrame implements SignalListener
{
	
	//test
	MainController controller;
	
	List<FilterPanel> filterpanels;
	List<SignalPanel> signalpanels;
	
	
	public MainWindow(MainController controller)
	{
		this.controller = controller;
		//TODO
		//window erzeugen und FilterPanels hinzufügen
	}
	
	
	public void addSignal(Signal signal)
	{
		signal.addListener(this);
		//TODO
		//Creates a new Tab with the Signal
	}


	@Override
	public void SignalChanged(SignalEvent e)
	{
		Signal sig = e.getSource();
		
		if(sig!=null)
		{
			//TODO
		}
		
	}
}
