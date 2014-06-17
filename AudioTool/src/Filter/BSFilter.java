package Filter;

import Common.Argument;
import Common.GeneralException;
import Common.Signal;

public class BSFilter extends Filter
{
	double tapTotal;
	float cutoffFreq, cutoffFreq2;

	public BSFilter(Signal signal)
	{
		this.name = "BSFilter";
		this.signal = signal;
		this.samplingRate = signal.getFormat().getFormat().getSampleRate();
		
		argumentList = new Argument[3];
		argumentList[0] = new Argument(0, this.samplingRate/2, 500, "Grenzfrequenz low");
		argumentList[1] = new Argument(0, this.samplingRate/2, 1000, "Grenzfrequenz high");
		argumentList[2] = new Argument(0, 500, 100, "Fenstergröße");

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

	public void init()
	{
		this.numberOfTaps = (int) argumentList[2].getValue();
		this.cutoffFreq = (int) argumentList[0].getValue();
		this.cutoffFreq2 = (int) argumentList[1].getValue();
		
		final double[] low = createLowpass(this.numberOfTaps, this.cutoffFreq, this.signal);
	    final double[] high = createHighpass(this.numberOfTaps, this.cutoffFreq2, this.signal);
	    for(int i = 0; i < low.length; i++)
	    {
	        low[i] += high[i];
	    }
	    this.FIRkoeff=windowHamming(low);
	}

	private double[] createHighpass(int tapTotal, float cutoffFreq, Signal signal)
	{
		HPFilter filter = new HPFilter(signal);
		Argument[] args = filter.getParamList();
		args[0].setValue(cutoffFreq);
		args[1].setValue(tapTotal);
		filter.init();
		return filter.getFIRkoeff();
	}

	private double[] createLowpass(int tapTotal, float cutoffFreq, Signal signal)
	{
		TPFilter filter = new TPFilter(signal);
		Argument[] args = filter.getParamList();
		args[0].setValue(cutoffFreq);
		args[1].setValue(tapTotal);
		filter.init();
		return filter.getFIRkoeff();
	}

}
