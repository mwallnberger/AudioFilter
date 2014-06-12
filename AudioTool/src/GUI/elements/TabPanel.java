package GUI.elements;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Common.Signal;
import Controller.MainController;
import GUI.MainWindow;
import GUI.SignalPanel;

import java.awt.*;

public class TabPanel extends JPanel {

	private final JTabbedPane panel = new JTabbedPane();
	private final ImageIcon closeIcon = new ImageIcon(MainWindow.class.getResource("/fatcow-hosting-icons-3000/16x16/cross.png"));
	private final MainController controller;
	
	public TabPanel(MainController controller) {
		super();
		this.controller = controller;
		setLayout(new GridLayout(1, 1));
		add(panel);
	}
	
	protected JPanel createInnerPanel(Signal signal) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.white); 
		
		SignalPanel chart = new SignalPanel(signal);
		OptionPanel options = new OptionPanel(signal, controller);
		
		panel.add(chart, BorderLayout.CENTER);
		panel.add(options, BorderLayout.LINE_END);
		chart.refresh();
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
		closeButton.addActionListener(new CloseTabActionHandler(signal.getName(), panel));
		closePanel.add(tabTitle);
		closePanel.add(closeButton);
		
		panel.setTabComponentAt(index, closePanel);
	}
	
	public Signal getActiveSignal() {
		return ((SignalPanel) panel.getTabComponentAt(panel.getSelectedIndex())).getSignal();
	}
}