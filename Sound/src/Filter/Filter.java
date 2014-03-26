package Filter;

import javax.swing.JCheckBox;

import GUI.StyleSheet;

public class Filter extends JCheckBox
{
	public Filter(String name)
	{
		super(name);
		this.setForeground(StyleSheet.CORTEX_BLUE);
		this.setOpaque(false);
	}

}
