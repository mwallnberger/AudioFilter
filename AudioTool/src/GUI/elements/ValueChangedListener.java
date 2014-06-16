package GUI.elements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Common.Argument;

public class ValueChangedListener implements ChangeListener, ActionListener {
	
	private final Argument parameter;
	private final JTextField paramValue;
	private final JSlider paramSlider;
	

	public ValueChangedListener(Argument parameter, JTextField paramValue, JSlider paramSlider) {
		this.parameter = parameter;
		this.paramValue = paramValue;
		this.paramSlider = paramSlider;
	}
	
	@Override
	public void stateChanged(ChangeEvent arg0) {
		paramValue.setText(String.valueOf(paramSlider.getValue()));	
		parameter.setValue(paramSlider.getValue());
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int value = 0;
		try {
			value = Integer.valueOf(paramValue.getText());
		}
		catch(NumberFormatException e) {
			paramValue.setText(String.valueOf(paramSlider.getValue()));
			return;
		}
		
		if(value < parameter.getMin()) {
			value = (int) Math.round(parameter.getMin());
		}
		else {
			if(value > parameter.getMax()) {
				value = (int) Math.round(parameter.getMax());
			}
		}
		
		paramSlider.setValue(value);
		
		
	}
	
}
