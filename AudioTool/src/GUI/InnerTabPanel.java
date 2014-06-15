package GUI;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import Common.Signal;
import Controller.MainController;
import GUI.elements.PlayingThread;

public class InnerTabPanel extends JPanel{
	
	private final Signal signal;
	private final MainController controller;
	private final PlayingThread thread;
	private final SignalPanel signalChart;

	public InnerTabPanel(Signal signal, MainController controller) {
		this.signal = signal;
		this.controller = controller;
		
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white); 
		
		OptionPanel options = new OptionPanel(signal, controller);
		//create new thread for playing sound (controller manages starting and stopping)
		this.thread = new PlayingThread(signal, options.getPlayButton());
		this.signalChart = new SignalPanel(signal, controller);
		// add signalChangedListener signalChart to signal
		signal.addListener(signalChart);
		// adding MarkerChangedListener for marker in chart
		thread.addMarkerChangedListener(signalChart);
		// adding MarkerChangedListener for jumping to pos in thread
		signalChart.addMarkerChangedListener(thread);
		
		this.add(signalChart, BorderLayout.CENTER);
		this.add(options, BorderLayout.LINE_END);
	}

	public Signal getSignal() {
		return signal;
	}

	public PlayingThread getThread() {
		return thread;
	}

	public SignalPanel getSignalChart() {
		return signalChart;
	}

}
