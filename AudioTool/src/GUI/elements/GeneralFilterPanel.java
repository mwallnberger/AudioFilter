package GUI.elements;

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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Common.Argument;
import Common.GeneralException;
import Common.Signal;
import Filter.Filter;

public class GeneralFilterPanel extends JDialog{
	
	public GeneralFilterPanel(Filter filter, JFrame mainFrame) {
		super(mainFrame);
		this.setTitle(filter.getName());
		this.setModal(true);
		
		Argument[] params = filter.getParams();
		
		this.setLayout(new GridLayout(params.length + 1, 1));
		
		for(int i = 0; i < params.length; i++) {
			
			JPanel paramPanel = new JPanel();
			JLabel paramLabel = new JLabel(params[i].getName());
			paramLabel.setPreferredSize(new Dimension(100, 50));
			paramLabel.setHorizontalAlignment(JLabel.RIGHT);
			JTextField paramValue = new JTextField(String.valueOf(params[i].getValue()));
			paramValue.setEditable(false);
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
			paramSlider.addChangeListener(new SliderChangedListener(params[i], paramValue, paramSlider));
			paramPanel.add(paramLabel);
			paramPanel.add(paramValue);
			paramPanel.add(paramSlider);
			
			this.add(paramPanel);
		}		
		
		JPanel buttonPanel = new JPanel();
		
		JButton btnFilter = new JButton("Filter");
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.dispose();
			}
		});
		
		buttonPanel.add(btnFilter);
		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filter.performFiltering();				
			}
		});
		buttonPanel.add(cancel);
		
		this.add(buttonPanel);
	}

	
	public void performFiltering() throws GeneralException {
		
	
	}
	
	private class SliderChangedListener implements ChangeListener {
		
		private final Argument parameter;
		private final JTextField paramValue;
		private final JSlider paramSlider;
		

		SliderChangedListener(Argument parameter, JTextField paramValue, JSlider paramSlider) {
			this.parameter = parameter;
			this.paramValue = paramValue;
			this.paramSlider = paramSlider;
		}
		
		@Override
		public void stateChanged(ChangeEvent arg0) {
			paramValue.setText(String.valueOf(paramSlider.getValue()));	
			parameter.setValue(paramSlider.getValue());
		}
		
	}
	
}
