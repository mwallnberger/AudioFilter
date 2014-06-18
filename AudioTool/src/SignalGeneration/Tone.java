package SignalGeneration;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

import Common.Argument;
import Common.Signal;

public class Tone
{
	private static float sampleFrequency = 44100;
	public static Argument[] getParamList()
	{
		Argument[] argument = new Argument[2];
		argument[0] = new Argument(1,15000, 500,"Frequenz", "Hz");
		argument[1] = new Argument(1,200, 10,"Länge", "sec");
		return argument;
	}

	public static Signal createRectangle(Argument[] argument)
	{
		int signalFrequency = (int) argument[0].getValue();
		float lengthInSeconds = argument[1].getValue();
		
		float[] Signal = new float[(int) (sampleFrequency * lengthInSeconds)];

		for (int i = 0; i < Signal.length; i++)
		{
			Signal[i] = getRectangleValue((float) i / (float) sampleFrequency, signalFrequency);
		}

		return new Signal(new AudioFileFormat(AudioFileFormat.Type.WAVE, new AudioFormat(sampleFrequency, 16, 2, true, false), 44100), Signal,
				Signal, "Rechteck");
	}

	public static Signal createSinus(Argument[] argument)
	{
		int signalFrequency = (int) argument[0].getValue();
		float lengthInSeconds = argument[1].getValue();
		float[] Signal = new float[(int) (sampleFrequency * lengthInSeconds)];

		for (int i = 0; i < Signal.length; i++)
		{
			Signal[i] = getSinusValue((float) i / (float) sampleFrequency, signalFrequency);
		}

		return new Signal(new AudioFileFormat(AudioFileFormat.Type.WAVE, new AudioFormat(sampleFrequency, 16, 2, true, false), 44100), Signal,
				Signal, "Sinus");
	}

	public static Signal createTriangle(Argument[] argument)
	{
		int signalFrequency = (int) argument[0].getValue();
		float lengthInSeconds = argument[1].getValue();
		float[] Signal = new float[(int) (sampleFrequency * lengthInSeconds)];

		for (int i = 0; i < Signal.length; i++)
		{
			Signal[i] = getTriangleValue((float) i / (float) sampleFrequency, signalFrequency);
		}

		return new Signal(new AudioFileFormat(AudioFileFormat.Type.WAVE, new AudioFormat(sampleFrequency, 16, 2, true, false), 44100), Signal,
				Signal, "Dreieck");
	}

	public static Signal createSawtooth(Argument[] argument)
	{
		int signalFrequency = (int) argument[0].getValue();
		float lengthInSeconds = argument[1].getValue();
		float[] Signal = new float[(int) (sampleFrequency * lengthInSeconds)];

		for (int i = 0; i < Signal.length; i++)
		{
			Signal[i] = getSawtoothValue((float) i / (float) sampleFrequency, signalFrequency);
		}

		return new Signal(new AudioFileFormat(AudioFileFormat.Type.WAVE, new AudioFormat(sampleFrequency, 16, 2, true, false), 44100), Signal,
				Signal, "Sägezahn");
	}

	private static float getSawtoothValue(float time, float signalFrequency)
	{
		return (float) Math.abs(Math.signum(Math.cos(2 * Math.PI * signalFrequency * time * 3))
				* (signalFrequency * time * 3 - Math.PI * Math.floor(signalFrequency * time * 3 / Math.PI + 1 / 2)));
	}

	private static float getTriangleValue(float time, float signalFrequency)
	{
		return (float) Math.asin(Math.sin(2 * Math.PI * signalFrequency * time));
	}

	private static float getRectangleValue(float time, float signalFrequency)
	{
		return (float) Math.signum(Math.sin(Math.PI * signalFrequency * time * 2));
	}

	private static float getSinusValue(float time, float signalFrequency)
	{
		return (float) Math.sin(2 * Math.PI * signalFrequency * time);
	}

}
