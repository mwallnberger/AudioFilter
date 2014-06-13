package GUI.elements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;

import Common.GeneralException;
import Common.SaveSignal;
import Common.Signal;
import Controller.MainController;
/***
 * closes Tab with given tabname on given JTabbedPane
 *
 */
public class CloseTabActionHandler implements ActionListener {
	
	private JTabbedPane tabPane;
	private String tabName;
	private Signal signal;
	private MainController controller;

    public CloseTabActionHandler(Signal signal, JTabbedPane tabPane, MainController controller) {
    	this.signal = signal;
        this.tabName = signal.getName();
        this.tabPane = tabPane;
        this.controller = controller;
    }

    public String getTabName() {
        return tabName;
    }

    public void actionPerformed(ActionEvent evt) {
        int index = tabPane.indexOfTab(getTabName());
        if (index >= 0) {
        	try {
				SaveSignal.saveSignalIfChanged(tabPane, signal, controller);
			} catch (GeneralException e) {

			}
        	controller.signalClose(signal);
            tabPane.removeTabAt(index);
        }

    }
}
