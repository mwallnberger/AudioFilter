package Controller;

import java.util.List;

import Common.Signal;
import GUI.MainWindow;

public class MainController
{
	List<Signal> signals;
	MainWindow window;

	public MainController()
	{
		window = new MainWindow(this);
	}
}
