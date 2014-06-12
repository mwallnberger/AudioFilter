package Filter;

import Common.Argument;
import Common.Signal;

public abstract class Filter
{
	protected String name = "Unknown";
	protected Signal signal;
	protected double[] FIRkoeff;
	protected float samplingRate;
	
	public abstract void performFiltering();
	public abstract Argument[] getParams();
	public abstract String getFilterInfo();
	
	
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
