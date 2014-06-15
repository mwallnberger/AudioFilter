package GUI.elements;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Common.Signal;
import Controller.MainController;
import GUI.MainWindow;
import GUI.TabPanel;

public class CloseTabPanel extends JPanel{
	
	private final ImageIcon closeIcon = new ImageIcon(MainWindow.class.getResource("/fatcow-hosting-icons-3000/16x16/cross.png"));
	
	public CloseTabPanel(Signal signal, MainController controller) {
		JButton closeButton = new JButton(closeIcon);
		JLabel tabTitle = new JLabel(signal.getName());
		this.setOpaque(false);
		closeButton.addActionListener(new CloseTabActionHandler(signal, controller));
		
		this.add(tabTitle);
		this.add(closeButton);

	}

}
