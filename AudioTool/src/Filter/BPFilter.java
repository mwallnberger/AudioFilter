package Filter;

import Common.Argument;
import Common.GeneralException;
import Common.Signal;

public class BPFilter extends Filter
{

	public BPFilter(Signal signal)
	{
		super("BPFilter", signal);
		
		argumentMap.put(CUTOFFFREQU_LOW, new Argument(0, this.samplingRate/2, 500, CUTOFFFREQU_LOW, "Hz"));
		argumentMap.put(CUTOFFFREQU_HIGH, new Argument(0, this.samplingRate/2, 1000, CUTOFFFREQU_HIGH, "Hz"));

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

}
