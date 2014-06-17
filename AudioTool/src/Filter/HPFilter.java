package Filter;


import Common.Argument;
import Common.GeneralException;
import Common.Signal;

public class HPFilter extends Filter
{
	double tapTotal;
	float cutoffFreq;

	public HPFilter(Signal signal)
	{
		this.name = "HPFilter";
		this.signal = signal;
		this.samplingRate = signal.getFormat().getFormat().getSampleRate();
		
		argumentList = new Argument[2];
		argumentList[0] = new Argument(0, this.samplingRate/2, 500, "Grenzfrequenz");
		argumentList[1] = new Argument(0, 500, 200, "Fenstergröße");

	}

	HPFilter(int numberOfTaps, float cutoffFrequ, float samplingRate)
	{
		this.numberOfTaps = numberOfTaps;
		this.cutoffFreq = cutoffFrequ;
		this.samplingRate = samplingRate;
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
		this.cutoffFreq = argumentList[0].getValue();
		this.numberOfTaps = (int) argumentList[1].getValue();
		
		final double cutoff = this.cutoffFreq / this.samplingRate;
		FIRkoeff = new double[this.numberOfTaps + 1];
		final double factor = 2.0 * cutoff;
		final int half = this.numberOfTaps >> 1;
		for (int i = 0; i < FIRkoeff.length; i++)
		{
			FIRkoeff[i] = (i == half ? 1.0 : 0.0) - factor * sinc(factor * (i - half));
		}
		windowHamming(this.FIRkoeff);
	}
	
}
