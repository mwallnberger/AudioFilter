package GUI;

import Common.Signal;
import Controller.MainController;
import GUI.elements.CloseTabPanel;
import GUI.elements.PlayingThread;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

/**
 * Creates new JTabbedPane for AudioTool, with methods to close tab, rename tab and create new tab
 */

public class TabPanel extends JTabbedPane {

	private final MainController controller;
	
	public TabPanel(MainController controller) {
		super();
		this.controller = controller;
	}
	
	public void createNewTab(Signal signal) {
		InnerTabPanel innerPanel = new InnerTabPanel(signal, controller);
		addTab(signal.getName(), innerPanel);
		int index = indexOfTab(signal.getName());
		
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
			// stop playing
			PlayingThread thread = innerTab.getThread();
			if(thread.isPlaying()) {
				thread.stopPlaying();
			}
			// remove all listeners
			thread.removeAllMarkerChangedListeners();
			s.removeAllListeners();
			innerTab.getSignalChart().removeAllMarkerChangedListeners();
			// close tab
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