package Filter;


import Common.Argument;
import Common.Signal;

public class HPFilter extends Filter
{
	int numberOfTaps;
	double[] FIRkoeff;
	double tapTotal;
	float cutoffFreq, samplingRate;
	private Argument[] argumentList;

	public HPFilter(Signal signal)
	{
		this.numberOfTaps = 1000;
		this.samplingRate = signal.getFormat().getFormat().getSampleRate();
		this.cutoffFreq = 500;
		init();

	}

	HPFilter(int numberOfTaps, float cutoffFrequ, float samplingRate)
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
		argumentList = new Argument[2];
		argumentList[0] = new Argument(0, this.samplingRate, "Grenzfrequenz");
		argumentList[1] = new Argument(0, 500, "Fenstergröße");
		return argumentList;
	}

	public void init()
	{
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

	double[] getFIRkoeff()
	{
		return FIRkoeff;
	}
}
