package Filter;

import Common.Argument;
import Common.GeneralException;
import Common.Signal;

public class BPFilter extends Filter
{
	int numberOfTaps;
	double tapTotal;
	float cutoffFreq, samplingRate, cutoffFreq2;

	public BPFilter(Signal signal)
	{
		this.name = "BPFilter";
		this.signal = signal;
		this.numberOfTaps = 1000;
		this.samplingRate = signal.getFormat().getFormat().getSampleRate();
		this.cutoffFreq = 500;
		
		argumentList = new Argument[3];
		argumentList[0] = new Argument(0, this.samplingRate, this.cutoffFreq, "Grenzfrequenz low");
		argumentList[1] = new Argument(0, this.samplingRate, this.cutoffFreq2, "Grenzfrequenz high");
		argumentList[2] = new Argument(0, 500, this.numberOfTaps, "Fenstergröße");
		
		init();

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
		
		this.init();
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

	@Override
	public Argument[] getParamList()
	{
		return argumentList;
	}

	public void init()
	{		
		//nicht mehr nötig?
//		this.numberOfTaps = (int) argumentList[2].getValue();
//		this.cutoffFreq = (int) argumentList[0].getValue();
//		this.cutoffFreq2 = (int) argumentList[1].getValue();
		
		final double[] bs = createLowpass(this.numberOfTaps, this.cutoffFreq, this.cutoffFreq2, this.samplingRate);

		final int half = this.numberOfTaps >> 1;
		for (int i = 0; i < bs.length; i++)
		{
			bs[i] = (i == half ? 1.0 : 0.0) - bs[i];
		}
		this.FIRkoeff = windowHamming(bs);

	}

	double[] getFIRkoeff()
	{
		return FIRkoeff;
	}

	private double[] createLowpass(int tapTotal, float cutoffFreq, float cutoffFreq2, float samplingRate)
	{
		BSFilter filter = new BSFilter(tapTotal, cutoffFreq, cutoffFreq2, samplingRate);
		return filter.getFIRkoeff();
	}

	@Override
	public String getFilterInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
