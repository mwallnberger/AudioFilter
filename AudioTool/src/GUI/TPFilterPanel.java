package GUI;

import javax.swing.JPanel;

import Common.GeneralException;
import Common.Signal;
import Filter.TPFilter;

public class TPFilterPanel extends FilterPanel
{

	@Override
	public JPanel getFilterPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void performFiltering(Signal signal)
	{
		TPFilter tp = new TPFilter(3);
		tp.performFiltering(signal);
	}
	
}
