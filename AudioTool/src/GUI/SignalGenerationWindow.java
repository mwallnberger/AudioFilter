package GUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import Common.Argument;
import Common.GeneralException;
import Common.Signal;
import Controller.MainController;
import GUI.elements.ValueChangedListener;
import SignalGeneration.Tone;

public class SignalGenerationWindow extends JDialog{
	private JDialog dialog;
	MainController controller;
	
	public SignalGenerationWindow(JFrame mainFrame, MainController controller) {
		super(mainFrame);
		this.setTitle("Signalerzeugung");
		this.setModal(true);
		this.controller = controller;
		this.setResizable(false);
		dialog = this;
		
		Argument[] params = Tone.getParamList();
		
		this.setLayout(new GridLayout(params.length + 1, 1));
		
		for(int i = 0; i < params.length; i++) {
			
			JPanel paramPanel = new JPanel();
			JLabel paramLabel = new JLabel(params[i].getName());
			paramLabel.setPreferredSize(new Dimension(100, 50));
			paramLabel.setHorizontalAlignment(JLabel.RIGHT);
			JTextField paramValue = new JTextField(String.valueOf((int) params[i].getValue()));
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
		
		JButton btnRec = new JButton("Rechteck");
		btnRec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.pauseAllPlaying();
				Signal signal = Tone.createRectangle(params);
				addSignalToController(signal);
				dialog.dispose();
				
			}
		});
		buttonPanel.add(btnRec);
		
		JButton btnSawtooth = new JButton("Sägezahn");
		btnSawtooth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.pauseAllPlaying();
				Signal signal = Tone.createSawtooth(params);
				addSignalToController(signal);
				dialog.dispose();
				
			}
		});
		buttonPanel.add(btnSawtooth);
		
		JButton btnSinus = new JButton("Sinus");
		btnSinus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.pauseAllPlaying();
				Signal signal = Tone.createSinus(params);
				addSignalToController(signal);
				dialog.dispose();
				
			}
		});
		buttonPanel.add(btnSinus);
		
		JButton btnTriangle = new JButton("Dreieck");
		btnTriangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.pauseAllPlaying();
				Signal signal = Tone.createTriangle(params);
				addSignalToController(signal);
				dialog.dispose();
				
			}
		});
		buttonPanel.add(btnTriangle);
		
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialog.dispose();
			}
		});
		buttonPanel.add(cancel);
		
		this.add(buttonPanel);
	}
	
	private void addSignalToController(Signal s) {
		if(s != null) {
			try {
				controller.addNewTab(s);
			} catch (GeneralException e) {
				JOptionPane.showMessageDialog(dialog, e.toString());
			}
		}
	}

}
