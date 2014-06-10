package GUI.elements;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;

import GUI.MainWindow;
import GUI.SignalPanel;
import GUI.TPFilterPanel;

import java.awt.*;

public class TabPane extends JPanel {

	JTabbedPane panel = new JTabbedPane();
	ImageIcon closeIcon = new ImageIcon(MainWindow.class.getResource("/fatcow-hosting-icons-3000/16x16/cross.png"));

	
	public TabPane() {
		setLayout(new GridLayout(1, 1));
		add(panel);
	}
	
	protected JPanel createInnerPanel(String text) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.white);
		
		SignalPanel chart = new SignalPanel();
		TPFilterPanel filter = new TPFilterPanel();
		
		panel.add(chart.refresh(), BorderLayout.CENTER);
		panel.add(filter, BorderLayout.LINE_END);

		return panel;
		
	}
	
	public void createNewTab(String name) {
		panel.addTab(name, createInnerPanel("test"));
		int index = panel.indexOfTab(name);
		
		//for "x" button in tab
		JPanel closePanel = new JPanel();
		JButton closeButton = new JButton(closeIcon);
		JLabel tabTitle = new JLabel(name);
		closePanel.setOpaque(false);
		closeButton.addActionListener(new CloseTabActionHandler(name, panel));
		closePanel.add(tabTitle);
		closePanel.add(closeButton);
		
		panel.setTabComponentAt(index, closePanel);
	}
	
//	
//	public static void main(String[] args) {
//		JFrame frame = new JFrame("TabPane");
//		frame.getContentPane().add(new TabPane(), BorderLayout.CENTER);
//		frame.setVisible(true);
//	}
}