package Filter;

import Common.Argument;
import Common.GeneralException;
import Common.Signal;

public class TPFilter extends Filter
{
	double tapTotal;

	public TPFilter(Signal signal)
	{
		super("TPFilter", signal);
		
		argumentMap.put(CUTOFFFREQU, new Argument(0, this.samplingRate/2, 500, CUTOFFFREQU, "Hz"));

	}
	
	public TPFilter(Signal signal, int tabTotal, float cutoffFreq) {
		this(signal);
		argumentMap.get(NUMBER_OF_TAPS).setValue(tabTotal);
		argumentMap.get(CUTOFFFREQU).setValue(cutoffFreq);
	}

//	TPFilter(int numberOfTaps, float cutoffFrequ, float samplingRate)
//	{
//		this.numberOfTaps = numberOfTaps;
//		this.cutoffFreq = cutoffFrequ;
//		this.samplingRate = samplingRate;
//	}

	@Override
	public void performFiltering()
	{
		init();
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
		int numofTabs = (int) argumentMap.get(NUMBER_OF_TAPS).getValue();
		
		final double cutoff = argumentMap.get(CUTOFFFREQU).getValue() / this.samplingRate;
		
		FIRkoeff = new double[numofTabs + 1];
		final double factor = 2.0 * cutoff;
		final int half = numofTabs >> 1;

		for (int i = 0; i < FIRkoeff.length; i++)
		{
			FIRkoeff[i] = factor * sinc(factor * (i - half));
		}

		windowHamming(this.FIRkoeff);

	}
}
