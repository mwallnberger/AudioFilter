package GUI.elements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;

import Common.GeneralException;
import Common.Signal;
import Controller.MainController;
/***
 * closes Tab with given tabname on given JTabbedPane
 *
 */
public class CloseTabActionHandler implements ActionListener {
	
	private JTabbedPane tabPane;
	private String tabName;
	private MainController controller;

    public CloseTabActionHandler(String tabName, JTabbedPane tabPane, MainController controller) {
        this.tabName = tabName;
        this.tabPane = tabPane;
        this.controller = controller;
    }

    public String getTabName() {
        return tabName;
    }

    public void actionPerformed(ActionEvent evt) {
        int index = tabPane.indexOfTab(getTabName());
        Signal signal = controller.getActiveSignal();
        if (index >= 0) {
        	try {
				SaveSignal.save(tabPane, controller);
			} catch (GeneralException e) {

			}
        	controller.signalClose(signal);
            tabPane.removeTabAt(index);
        }

    }
}
