package Filter;

import java.text.DecimalFormat;

import Common.Argument;
import Common.Signal;

public class TPFilter extends Filter
{
	int numberOfTaps;
	double[] FIRkoeff;
	double tapTotal;
	float cutoffFreq, samplingRate;

	public TPFilter(Signal signal)
	{
		this.numberOfTaps = 1000;
		this.samplingRate = 5000;
		this.cutoffFreq = 500;
		init();

		for (int x = 0; x < this.FIRkoeff.length; x++)
		{
			System.out.println(new DecimalFormat("#.########").format(this.FIRkoeff[x]));
		}

	}

	TPFilter(int numberOfTaps, float cutoffFrequ, float samplingRate)
	{
		this.numberOfTaps = numberOfTaps;
		this.cutoffFreq = cutoffFrequ;
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

		final double cutoff = this.cutoffFreq / this.samplingRate;
		FIRkoeff = new double[this.numberOfTaps + 1];
		final double factor = 2.0 * cutoff;
		final int half = this.numberOfTaps >> 1;

		for (int i = 0; i < FIRkoeff.length; i++)
		{
			FIRkoeff[i] = factor * sinc(factor * (i - half));
		}

		windowHamming(this.FIRkoeff);

	}

	double[] getFIRkoeff()
	{
		return FIRkoeff;
	}
}
