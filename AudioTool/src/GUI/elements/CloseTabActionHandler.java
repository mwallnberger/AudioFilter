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

	private Signal signal;
	private MainController controller;

    public CloseTabActionHandler(Signal signal, MainController controller) {
    	this.signal = signal;
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent evt) {
        controller.closeSignal(signal);
    }
}
