package Filter;

import Common.Argument;
import Common.GeneralException;
import Common.Signal;

public class BSFilter extends Filter
{
	int numberOfTaps;
	double tapTotal;
	float cutoffFreq, cutoffFreq2;

	public BSFilter(Signal signal)
	{
		this.name = "BSFilter";
		this.signal = signal;
		this.numberOfTaps = 1000;
		this.samplingRate = signal.getFormat().getFormat().getSampleRate();
		this.cutoffFreq = 500;
		
		argumentList = new Argument[3];
		argumentList[0] = new Argument(0, this.samplingRate/2, this.cutoffFreq, "Grenzfrequenz low");
		argumentList[1] = new Argument(0, this.samplingRate/2, this.cutoffFreq2, "Grenzfrequenz high");
		argumentList[2] = new Argument(0, 500, this.numberOfTaps, "Fenstergröße");
		
		init();

	}
	
	BSFilter(int numberOfTaps, float cutoffFrequ,float cutoffFrequ2, float samplingRate)
	{
		this.numberOfTaps = numberOfTaps;
		this.cutoffFreq = cutoffFrequ;
		this.cutoffFreq2 = cutoffFrequ2;
		this.samplingRate = samplingRate;
		
		argumentList = new Argument[3];
		argumentList[0] = new Argument(0, this.samplingRate, this.cutoffFreq, "Grenzfrequenz low");
		argumentList[1] = new Argument(0, this.samplingRate, this.cutoffFreq2, "Grenzfrequenz high");
		argumentList[2] = new Argument(0, 500, this.numberOfTaps, "Fenstergröße");	
	}

	@Override
	public void performFiltering()
	{
		try
		{
			PerformFiltering(signal, false, 0);
		}
		catch (GeneralException e)
		{
			e.printStackTrace();
		}
	}

	public Argument[] getParamList()
	{
		return argumentList;
	}

	public void init()
	{
		//nicht mehr nötig?
		this.numberOfTaps = (int) argumentList[2].getValue();
		this.cutoffFreq = (int) argumentList[0].getValue();
		this.cutoffFreq2 = (int) argumentList[1].getValue();
		
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

	@Override
	public String getFilterInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
