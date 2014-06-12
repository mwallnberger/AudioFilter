package GUI.elements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;

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
        	if(signal.isChanged()) {
        		SaveSignalActionHandler saveSignal = new SaveSignalActionHandler(tabPane, controller);
        		saveSignal.actionPerformed(null);
        	}
        	controller.signalClose(signal);
            tabPane.removeTabAt(index);
        }

    }
}
