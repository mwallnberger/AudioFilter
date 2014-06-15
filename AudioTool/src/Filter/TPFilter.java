package Filter;

import Common.Argument;
import Common.GeneralException;
import Common.Signal;

public class TPFilter extends Filter
{
	int numberOfTaps;
	double tapTotal;
	float cutoffFreq;
	private Argument[] argumentList;

	public TPFilter(Signal signal)
	{
		this.name = "TPFilter";
		this.signal = signal;
		this.numberOfTaps = 1000;
		this.samplingRate = signal.getFormat().getFormat().getSampleRate();
		this.cutoffFreq = 500;
		
		argumentList = new Argument[2];
		argumentList[0] = new Argument(0, this.samplingRate/2, this.cutoffFreq, "Grenzfrequenz");
		argumentList[1] = new Argument(0, 500, this.numberOfTaps, "Fenstergröße");
		
		init();

	}

	TPFilter(int numberOfTaps, float cutoffFrequ, float samplingRate)
	{
		this.numberOfTaps = numberOfTaps;
		this.cutoffFreq = cutoffFrequ;
		this.samplingRate = samplingRate;
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
		// nicht benötigt
//		this.cutoffFreq = argumentList[0].getValue();
//		this.numberOfTaps = (int) argumentList[1].getValue();

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

	@Override
	public String getFilterInfo() {
		// TODO Auto-generated method stub
		return null;
	}
}
