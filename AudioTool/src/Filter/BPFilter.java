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
		super("BPFilter", signal);
		
		argumentMap.put(CUTOFFFREQU_LOW, new Argument(0, this.samplingRate/2, 500, CUTOFFFREQU_LOW, "Hz"));
		argumentMap.put(CUTOFFFREQU_HIGH, new Argument(0, this.samplingRate/2, 1000, CUTOFFFREQU_HIGH, "Hz"));

	}

//	BPFilter(int numberOfTaps, float cutoffFrequ, float cutoffFrequ2, float samplingRate)
//	{
//		this.numberOfTaps = numberOfTaps;
//		this.cutoffFreq = cutoffFrequ;
//		this.cutoffFreq2 = cutoffFrequ2;
//		this.samplingRate = samplingRate;
//		
//		argumentMap = new Argument[3];
//		argumentMap[0] = new Argument(0, this.samplingRate, this.cutoffFreq, "Grenzfrequenz low");
//		argumentMap[1] = new Argument(0, this.samplingRate, this.cutoffFreq2, "Grenzfrequenz high");
//		argumentMap[2] = new Argument(0, 500, this.numberOfTaps, "Fenstergröße");
//	}

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
		int numberOfTaps = (int) argumentMap.get(NUMBER_OF_TAPS).getValue();
		BSFilter bsFilter = new BSFilter(signal, numberOfTaps, argumentMap.get(CUTOFFFREQU_LOW).getValue(), argumentMap.get(CUTOFFFREQU_HIGH).getValue());
		bsFilter.init();
		
		final double[] bs = bsFilter.getFIRkoeff();

		final int half = numberOfTaps >> 1;
		for (int i = 0; i < bs.length; i++)
		{
			bs[i] = (i == half ? 1.0 : 0.0) - bs[i];
		}
		this.FIRkoeff = windowHamming(bs);

	}

//	private double[] createBandPass(int tapTotal, float cutoffFreq, float cutoffFreq2, float samplingRate)
//	{
//		BSFilter filter = new BSFilter(signal);
//		Argument[] args = filter.getParamList();
//		args[0].setValue(tapTotal);
//		args[1].setValue(cutoffFreq);
//		args[1].setValue(cutoffFreq2);
//		filter.init();
//		return filter.getFIRkoeff();
//	}

}
