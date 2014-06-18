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
		super("BSFilter", signal);
		
		argumentMap.put(CUTOFFFREQU_LOW, new Argument(0, this.samplingRate/2, 500, CUTOFFFREQU_LOW, "Hz"));
		argumentMap.put(CUTOFFFREQU_HIGH, new Argument(0, this.samplingRate/2, 1000, CUTOFFFREQU_HIGH, "Hz"));

	}
	
	public BSFilter(Signal signal, int tabTotal, float cutoffFreq, float cutoffFreq2) {
		this(signal);
		argumentMap.get(NUMBER_OF_TAPS).setValue(tabTotal);
		argumentMap.get(CUTOFFFREQU_LOW).setValue(cutoffFreq);
		argumentMap.get(CUTOFFFREQU_HIGH).setValue(cutoffFreq2);
	}
	
//	BSFilter(int numberOfTaps, float cutoffFrequ,float cutoffFrequ2, float samplingRate)
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
		
		TPFilter tpFilter = new TPFilter(signal, (int) argumentMap.get(NUMBER_OF_TAPS).getValue(), argumentMap.get(CUTOFFFREQU_LOW).getValue());
		tpFilter.init();
		HPFilter hpFilter = new HPFilter(signal, (int) argumentMap.get(NUMBER_OF_TAPS).getValue(), argumentMap.get(CUTOFFFREQU_HIGH).getValue());
		hpFilter.init();
		
		final double[] low = tpFilter.getFIRkoeff();
	    final double[] high = hpFilter.getFIRkoeff();
	    for(int i = 0; i < low.length; i++)
	    {
	        low[i] += high[i];
	    }
	    this.FIRkoeff=windowHamming(low);
	}

}
