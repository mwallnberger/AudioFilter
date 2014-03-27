package GUI;

import javax.swing.JPanel;

import Common.GeneralException;
import Common.Signal;

public abstract class FilterPanel
{
	public abstract JPanel getFilterPanel() throws GeneralException;
	
	public abstract void performFiltering(Signal signal) throws GeneralException;

}
