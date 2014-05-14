package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;
/***
 * closes Tab with given tabname on given JTabbedPane
 *
 */
public class CloseTabActionHandler implements ActionListener {
	
	private JTabbedPane tabPane;
	private String tabName;

    public CloseTabActionHandler(String tabName, JTabbedPane tabPane) {
        this.tabName = tabName;
        this.tabPane = tabPane;
    }

    public String getTabName() {
        return tabName;
    }

    public void actionPerformed(ActionEvent evt) {
        int index = tabPane.indexOfTab(getTabName());
        if (index >= 0) {
            tabPane.removeTabAt(index);
        }

    }
}
