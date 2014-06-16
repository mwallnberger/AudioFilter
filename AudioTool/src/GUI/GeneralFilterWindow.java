package GUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import Common.Argument;
import Controller.MainController;
import Filter.Filter;
import GUI.elements.ValueChangedListener;

public class GeneralFilterWindow extends JDialog{
	
	JDialog dialog;
	
	public GeneralFilterWindow(Filter filter, JFrame mainFrame, MainController controller) {
		super(mainFrame);
		this.setTitle(filter.getName());
		this.setModal(true);
		this.setResizable(false);
		dialog = this;
		
		
		Argument[] params = filter.getParamList();
		
		this.setLayout(new GridLayout(params.length + 1, 1));
		
		for(int i = 0; i < params.length; i++) {
			
			JPanel paramPanel = new JPanel();
			JLabel paramLabel = new JLabel(params[i].getName());
			paramLabel.setPreferredSize(new Dimension(100, 50));
			paramLabel.setHorizontalAlignment(JLabel.RIGHT);
			JTextField paramValue = new JTextField(String.valueOf(params[i].getValue()));
			paramValue.setEditable(true);
			paramValue.setPreferredSize(new Dimension(50, 20));
			paramValue.setHorizontalAlignment(JTextField.RIGHT);
			int max = Math.round(params[i].getMax());
			int min = Math.round(params[i].getMin());
			int init = Math.round(params[i].getValue());
			JSlider paramSlider = new JSlider(JSlider.HORIZONTAL, min, max, init);
			paramSlider.setMajorTickSpacing((max - min) / 10);
			paramSlider.setMinorTickSpacing((max - min) / 100);
			paramSlider.setPaintLabels(true);
			paramSlider.setPreferredSize(new Dimension(400, 50));
			ValueChangedListener changeListener = new ValueChangedListener(params[i], paramValue, paramSlider);
			paramSlider.addChangeListener(changeListener);
			paramValue.addActionListener(changeListener);
			paramPanel.add(paramLabel);
			paramPanel.add(paramValue);
			paramPanel.add(paramSlider);
			
			this.add(paramPanel);
		}		
		
		JPanel buttonPanel = new JPanel();
		
		JButton btnFilter = new JButton("Filter");
		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.pauseAllPlaying();
				controller.performFilter(filter);
				dialog.dispose();
				
			}
		});
		buttonPanel.add(btnFilter);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
			}
		});
		buttonPanel.add(cancel);
		
		this.add(buttonPanel);
	}
	
}
