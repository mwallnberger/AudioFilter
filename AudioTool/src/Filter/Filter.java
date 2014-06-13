package Filter;

import Common.Argument;
import Common.GeneralException;
import Common.Signal;

public abstract class Filter
{
	protected String name = "Unknown";
	protected Signal signal;
	protected double[] FIRkoeff;
	protected float samplingRate;

	protected Argument[] argumentList;

	public abstract void performFiltering();

	public abstract Argument[] getParams();

	public abstract String getFilterInfo();

	public void PerformFiltering(Signal signal, boolean doScaling, int resolution) throws GeneralException
	{
		// Filter-Matrix
		float[][] Filter;

		// Filtered Values for both channels
		float[] OutLeft, OutRight;

		float maxValue = 0;

		float[] SigLeft = signal.getSignalLeft();
		float[] SigRight = signal.getSignalRight();

		if (SigRight != null)
		{
			if (SigLeft.length != SigRight.length)
				throw new GeneralException("The length of the left channel is not the same as the length of the right channel");
		}

		// Create Filter-Matrix and Output-Array
		Filter = new float[3][FIRkoeff.length];
		for (int i = 0; i < FIRkoeff.length; i++)
		{
			Filter[0][i] = (float) FIRkoeff[i];
			Filter[1][i] = 0;
			Filter[2][i] = 0;
		}

		OutLeft = new float[(SigLeft.length + (FIRkoeff.length - 1))];
		OutRight = new float[(SigLeft.length + (FIRkoeff.length - 1))];

		// Shift Signal-Values through Matrix to compute Output-Signal
		for (int x = 0; x < (SigLeft.length + (FIRkoeff.length - 1)); x++)
		{
			for (int i = 0; i < (Filter[0].length - 1); i++)
			{
				Filter[1][(Filter[0].length) - (i + 1)] = Filter[1][(Filter[0].length) - (i + 2)];
				Filter[2][(Filter[0].length) - (i + 1)] = Filter[2][(Filter[0].length) - (i + 2)];
			}
			if (x < SigLeft.length)
			{
				Filter[1][0] = SigLeft[x];
				if (SigRight != null)
				{
					Filter[2][0] = SigRight[x];
				}
			}
			else
			{
				Filter[1][0] = 0;
				Filter[2][0] = 0;
			}

			float resultLeft = 0;
			float resultRight = 0;

			for (int i = 0; i < FIRkoeff.length; i++)
			{
				resultLeft = resultLeft + Filter[0][i] * Filter[1][i];
				if (SigRight != null)
				{
					resultRight = resultRight + Filter[0][i] * Filter[2][i];
				}
			}

			OutLeft[x] = resultLeft;
			OutRight[x] = resultRight;

			// Peek-Value of both channels (for scaling)
			if (Math.abs(OutLeft[x]) >= maxValue)
			{
				maxValue = Math.abs(OutLeft[x]);
			}
			if (Math.abs(OutRight[x]) >= maxValue)
			{
				if (OutRight != null)
				{
					maxValue = Math.abs(OutRight[x]);
				}
			}
		}

		// Scaling the output to int-values with a specific resolution
		if (doScaling)
		{
			float scalefactor = (float) ((Math.pow(2.0, (float) (resolution - 1)) / maxValue) * 0.99);

			for (int i = 0; i < OutLeft.length; i++)
			{
				OutLeft[i] = Math.round(OutLeft[i] * scalefactor);
				if (OutRight != null)
				{
					OutRight[i] = Math.round(OutRight[i] * scalefactor);
				}
			}
		}

		signal.setSignalLeft(OutLeft);
		if (OutRight != null)
		{
			signal.setSignalRight(OutRight);
		}
	}

	public String getName()
	{
		return name;
	}

	public double sinc(final double x)
	{
		if (x != 0)
		{
			final double xpi = Math.PI * x;
			return Math.sin(xpi) / xpi;
		}
		return 1.0;
	}

	public double[] windowHamming(final double[] fir)
	{
		final int m = fir.length - 1;
		for (int i = 0; i < fir.length; i++)
		{
			fir[i] *= 0.54 - 0.46 * Math.cos(2.0 * Math.PI * i / m);
		}
		return fir;
	}

}
