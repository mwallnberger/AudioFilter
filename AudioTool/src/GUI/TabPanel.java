package GUI;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Common.Signal;
import Controller.MainController;
import GUI.elements.CloseTabActionHandler;
import GUI.elements.CloseTabPanel;
import GUI.elements.PlayingThread;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TabPanel extends JTabbedPane {

//	private final JTabbedPane panel = new JTabbedPane();
	
	private final MainController controller;
//	private final List<Signal> signals;
	
	public TabPanel(MainController controller) {
		super();
		this.controller = controller;
//		setLayout(new GridLayout(1, 1));
//		signals = new ArrayList<Signal>();
//		add(panel);
	}
	
//	protected JPanel createInnerPanel(Signal signal) {
//		JPanel panel = new JPanel(new BorderLayout());
//		panel.setBackground(Color.white); 
//		
//		OptionPanel options = new OptionPanel(signal, controller);
//		//create new thread for playing sound (controller manages starting and stopping)
//		PlayingThread thread = new PlayingThread(signal, options.getPlayButton());
//		controller.addPlayingThread(thread, signal);
//		SignalPanel chart = new SignalPanel(signal, controller, thread);
//		thread.addMarkerChangedListener(chart);
//		
//		
//		panel.add(chart, BorderLayout.CENTER);
//		panel.add(options, BorderLayout.LINE_END);
//		return panel;
//	}
	
	public void createNewTab(Signal signal) {
		InnerTabPanel innerPanel = new InnerTabPanel(signal, controller);
		addTab(signal.getName(), innerPanel);
		int index = indexOfTab(signal.getName());
		
//		//for "x" button in tab
//		JPanel closePanel = new JPanel();
//		JButton closeButton = new JButton(closeIcon);
//		JLabel tabTitle = new JLabel(signal.getName());
//		closePanel.setOpaque(false);
//		closeButton.addActionListener(new CloseTabActionHandler(signal, this, controller));
//		
//		closePanel.add(tabTitle);
//		closePanel.add(closeButton);
////		signals.add(index, signal);
		
		setTabComponentAt(index, new CloseTabPanel(signal, controller));	
		setSelectedIndex(index);
	}
	
	public InnerTabPanel getSelectedComponent() {
		Component tabComponent = super.getSelectedComponent();
		if(tabComponent instanceof InnerTabPanel) {
			return (InnerTabPanel) tabComponent;
		}
		return null;
	}
	
	public InnerTabPanel getComponentAt(int index) {
		Component tabComponent = super.getComponentAt(index);
		if(tabComponent instanceof InnerTabPanel) {
			return (InnerTabPanel) tabComponent;
		}
		return null;
	}
	
	public Signal getActiveSignal() {
		InnerTabPanel tabComponent = getSelectedComponent();
		if(tabComponent != null) {
			return tabComponent.getSignal();
		}
		return null;
	}
	
	public int getIndexOf(Signal s) {
		for (int i = 0; i < getTabCount(); i++) {
			if(s == getComponentAt(i).getSignal()) {
				return i;
			}
		}
		return - 1;
	}
	
	public InnerTabPanel getComponentOf(Signal s) {
		int index = getIndexOf(s);
		if(index < 0) {
			return null;
		}
		return getComponentAt(index);
	}
	
	public List<InnerTabPanel> getTabs() {
		List<InnerTabPanel> tabList = new ArrayList<InnerTabPanel>();
		for (int i = 0; i < getTabCount(); i++) {
			tabList.add(getComponentAt(i));
		}
		return tabList;
	}
	
	public List<Signal> getSignals() {
		List<Signal> signalList = new ArrayList<Signal>();
		for (int i = 0; i < getTabCount(); i++) {
			signalList.add(getComponentAt(i).getSignal());
		}
		return signalList;
	}
	
	public void removeTabOf(Signal s) {
		InnerTabPanel innerTab = getComponentOf(s);
		if(innerTab != null) {
			PlayingThread thread = innerTab.getThread();
			if(thread.isPlaying()) {
				thread.stopPlaying();
			}
			thread.removeAllMarkerChangedListeners();
			s.removeAllListeners();
			innerTab.getSignalChart().removeAllMarkerChangedListeners();
			this.removeTabAt(getIndexOf(s));
		}

	}
	
	public void renameTab(Signal s) {
		int index = getIndexOf(s);
		if(index >= 0) {
			setTitleAt(index, s.getName());
			setTabComponentAt(index, new CloseTabPanel(s, controller));
		}
	}

}