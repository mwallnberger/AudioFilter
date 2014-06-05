package Filter;

import Common.Argument;
import Common.Signal;

public abstract class Filter
{
	String name;
	
	public abstract void performFiltering(Signal sig);
	public abstract Argument[] getParams();
	
	
	public String getName()
	{
		return name;
	}
	
	
}
