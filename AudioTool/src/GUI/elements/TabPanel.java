package GUI.elements;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Common.Signal;
import Controller.MainController;
import GUI.MainWindow;
import GUI.OptionPanel;
import GUI.SignalPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TabPanel extends JPanel {

	private final JTabbedPane panel = new JTabbedPane();
	private final ImageIcon closeIcon = new ImageIcon(MainWindow.class.getResource("/fatcow-hosting-icons-3000/16x16/cross.png"));
	private final MainController controller;
	private final List<Signal> signals;
	
	public TabPanel(MainController controller) {
		super();
		this.controller = controller;
		setLayout(new GridLayout(1, 1));
		signals = new ArrayList<Signal>();
		add(panel);
	}
	
	protected JPanel createInnerPanel(Signal signal) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.white); 
		
		OptionPanel options = new OptionPanel(signal, controller);
		SignalPanel chart = new SignalPanel(signal, controller);
		
		
		panel.add(chart, BorderLayout.CENTER);
		panel.add(options, BorderLayout.LINE_END);
		return panel;
	}
	
	public void createNewTab(Signal signal) {
		panel.addTab(signal.getName(), createInnerPanel(signal));
		int index = panel.indexOfTab(signal.getName());
		
		//for "x" button in tab
		JPanel closePanel = new JPanel();
		JButton closeButton = new JButton(closeIcon);
		JLabel tabTitle = new JLabel(signal.getName());
		closePanel.setOpaque(false);
		closeButton.addActionListener(new CloseTabActionHandler(signal, panel, controller));
		
		closePanel.add(tabTitle);
		closePanel.add(closeButton);
		signals.add(index, signal);
		panel.setTabComponentAt(index, closePanel);	
		panel.setSelectedIndex(index);
	}
	
	public Signal getActiveSignal() {
		if(signals.size() < 1 || panel.getTabCount() < 1) {
			return null;
		}
		
		return signals.get(panel.getSelectedIndex());
	}
	
	public boolean removeSignal(Signal signal) {
		return signals.remove(signal);
	}

	public List<Signal> getSignals() {
		return signals;
	}

}