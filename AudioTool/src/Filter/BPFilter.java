package Filter;

import java.text.DecimalFormat;

import Common.Argument;
import Common.Signal;

public class BPFilter extends Filter
{
	int numberOfTaps;
	double[] FIRkoeff;
	double tapTotal;
	float cutoffFreq, samplingRate, cutoffFreq2;

	public BPFilter(Signal signal)
	{
		this.numberOfTaps = 1000;
		this.samplingRate = 5000;
		this.cutoffFreq = 500;
		init();

	}

	BPFilter(int numberOfTaps, float cutoffFrequ, float cutoffFrequ2, float samplingRate)
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

}
