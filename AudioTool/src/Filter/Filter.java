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
	
	public double sinc(final double x)
	{
		if (x != 0)
		{
			final double xpi = Math.PI * x;
			return Math.sin(xpi) / xpi;
		}
		return 1.0;
	}
	
	public double[] windowHamming(final double[] fir)
	{
	    final int m = fir.length - 1;
	    for(int i = 0; i < fir.length; i++)
	    {
	        fir[i] *= 0.54 - 0.46 * Math.cos(2.0 * Math.PI * i / m);
	    }
	    return fir;
	}
	
	
}
