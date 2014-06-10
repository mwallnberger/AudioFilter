package GUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Common.GeneralException;
import Common.Signal;

public class TPFilterPanel extends FilterPanel {
	
	static final int max = 50;
	static final int min = 0;
	static final int init = 10;
	
	public TPFilterPanel(JFrame mainFrame) {
		
		this.setLayout(new GridLayout(4, 1));
		
		
		JPanel param1Panel = new JPanel();
		JLabel param1 = new JLabel("param1: ");
		JTextField param1Text = new JTextField("10");
		param1Text.setEditable(false);
		param1Text.setPreferredSize(new Dimension(20, 20));
		JSlider param1Slider = new JSlider(JSlider.HORIZONTAL, min, max, init);
		param1Slider.setMajorTickSpacing(10);
		param1Slider.setMinorTickSpacing(1);
		param1Slider.setPaintLabels(true);
		param1Slider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				param1Text.setText(String.valueOf(param1Slider.getValue()));				
			}
		});
		param1Panel.add(param1);
		param1Panel.add(param1Text);
		param1Panel.add(param1Slider);
		

		JPanel param2Panel = new JPanel();
		JLabel param2 = new JLabel("param2: ");
		JTextField param2Text = new JTextField("10");
		param2Text.setEditable(false);
		param2Text.setPreferredSize(new Dimension(20, 20));
		JSlider param2Slider = new JSlider(JSlider.HORIZONTAL, min, max, init);
		param2Slider.setMajorTickSpacing(10);
		param2Slider.setMinorTickSpacing(1);
		param2Slider.setPaintLabels(true);
		param2Slider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				param2Text.setText(String.valueOf(param2Slider.getValue()));				
			}
		});
		param2Panel.add(param2);
		param2Panel.add(param2Text);
		param2Panel.add(param2Slider);
		
		
		
		JPanel buttonPanel = new JPanel();
		
		JButton filter = new JButton("Filter");
		JButton close = new JButton("Close");
		
		buttonPanel.add(filter);
		filter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		buttonPanel.add(close);
		close.addActionListener(new closeActionListener(mainFrame));
		
		this.add(param1Panel);
		this.add(param2Panel);
		this.add(buttonPanel);
	}
	
	@Override
	public JPanel getFilterPanel() {
		return this;
	}

	
	@Override
	public void performFiltering(Signal signal) throws GeneralException {
		
	
	}
	
	private class closeActionListener implements ActionListener {

		JFrame mainFrame;
		
		public closeActionListener(JFrame mainFrame) {
			this.mainFrame = mainFrame;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			mainFrame.dispose();
		}
		
	};
	
}
