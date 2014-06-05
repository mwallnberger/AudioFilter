package Filter;

import Common.Argument;
import Common.Signal;

public class BSFilter extends Filter
{
	int numberOfTaps;
	double[] FIRkoeff;
	double tapTotal;
	float cutoffFreq, samplingRate,cutoffFreq2;


	public BSFilter(Signal signal)
	{
		this.numberOfTaps = 1000;
		this.samplingRate = 5000;
		this.cutoffFreq = 500;
		init();

	}
	
	BSFilter(int numberOfTaps, float cutoffFrequ,float cutoffFrequ2, float samplingRate)
	{
		this.numberOfTaps = numberOfTaps;
		this.cutoffFreq = cutoffFrequ;
		this.cutoffFreq2 = cutoffFrequ2;
		this.samplingRate = samplingRate;
		this.init();	
	}

	@Override
	public void performFiltering(Signal sig)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public Argument[] getParams()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void init()
	{
		final double[] low = createLowpass(this.numberOfTaps, this.cutoffFreq, this.samplingRate);
	    final double[] high = createHighpass(this.numberOfTaps, this.cutoffFreq2, this.samplingRate);
	    for(int i = 0; i < low.length; i++)
	    {
	        low[i] += high[i];
	    }
	    this.FIRkoeff=windowHamming(low);
	}
	
	double[] getFIRkoeff()
	{
		return FIRkoeff;
	}

	private double[] createHighpass(int tapTotal, float cutoffFreq, float samplingRate)
	{
		HPFilter filter = new HPFilter(tapTotal,cutoffFreq,samplingRate);
		return filter.getFIRkoeff();
	}

	private double[] createLowpass(int tapTotal, float cutoffFreq, float samplingRate)
	{
		TPFilter filter = new TPFilter(tapTotal,cutoffFreq,samplingRate);
		return filter.getFIRkoeff();
	}

}
