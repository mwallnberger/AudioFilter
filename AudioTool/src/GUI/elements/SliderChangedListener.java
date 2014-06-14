package GUI.elements;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Common.Argument;

public class SliderChangedListener implements ChangeListener {
	
	private final Argument parameter;
	private final JTextField paramValue;
	private final JSlider paramSlider;
	

	public SliderChangedListener(Argument parameter, JTextField paramValue, JSlider paramSlider) {
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
