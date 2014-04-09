package Filter;

import javax.swing.JPanel;

abstract class FilterPanel extends JPanel
{
	
	public abstract String getName();
	//Soll einen Filter liefern, bei korrekten Einstellungen, bei Fehler null
	//Dieser Fehler ist anzuzeigen
	public abstract Filter getFilter();
	

}
