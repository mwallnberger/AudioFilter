package GUI.elements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Common.Signal;
import Controller.MainController;
import GUI.MainWindow;

/**
 * Creates a simple panel with title of the signal and an close button for each tab label
 */

public class CloseTabPanel extends JPanel{
	
	private final ImageIcon closeIcon = new ImageIcon(MainWindow.class.getResource("/fatcow-hosting-icons-3000/16x16/cross.png"));
	
	public CloseTabPanel(Signal signal, MainController controller) {
		JButton closeButton = new JButton(closeIcon);
		JLabel tabTitle = new JLabel(signal.getName());
		this.setOpaque(false);
		closeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
		        controller.closeSignal(signal);
		    }
			
		});
		
		this.add(tabTitle);
		this.add(closeButton);

	}

}
