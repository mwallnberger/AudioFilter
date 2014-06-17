package Filter;

import Common.Argument;
import Common.GeneralException;
import Common.Signal;

public class BPFilter extends Filter
{
	
	double tapTotal;
	float cutoffFreq, cutoffFreq2;

	public BPFilter(Signal signal)
	{
		this.name = "BPFilter";
		this.signal = signal;
		this.samplingRate = signal.getFormat().getFormat().getSampleRate();
		
		argumentList = new Argument[3];
		argumentList[0] = new Argument(0, this.samplingRate/2, 500, "Grenzfrequenz low");
		argumentList[1] = new Argument(0, this.samplingRate/2, 1000, "Grenzfrequenz high");
		argumentList[2] = new Argument(0, 500, 100, "Fenstergröße");

	}

	BPFilter(int numberOfTaps, float cutoffFrequ, float cutoffFrequ2, float samplingRate)
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
		
		final double[] bs = createBandPass(this.numberOfTaps, this.cutoffFreq, this.cutoffFreq2, this.samplingRate);

		final int half = this.numberOfTaps >> 1;
		for (int i = 0; i < bs.length; i++)
		{
			bs[i] = (i == half ? 1.0 : 0.0) - bs[i];
		}
		this.FIRkoeff = windowHamming(bs);

	}

	private double[] createBandPass(int tapTotal, float cutoffFreq, float cutoffFreq2, float samplingRate)
	{
		BSFilter filter = new BSFilter(signal);
		Argument[] args = filter.getParamList();
		args[0].setValue(tapTotal);
		args[1].setValue(cutoffFreq);
		args[1].setValue(cutoffFreq2);
		filter.init();
		return filter.getFIRkoeff();
	}

}
