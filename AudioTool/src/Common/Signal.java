package Common;

import java.util.Set;

import javax.sound.sampled.AudioFileFormat;

import GUI.SignalEvent;
import GUI.SignalListener;

public class Signal
{
	private Set<SignalListener> listeners;
	private AudioFileFormat format;
	private float[] signalLeft;
	private float[] signalRight;

	public Signal(AudioFileFormat format, float[] signalLeft,float[] signalRight)
	{
		super();
		this.format = format;
		this.signalLeft = signalLeft;
		this.signalRight = signalRight;
	}

	public synchronized void addListener(SignalListener listener)
	{
		if (this.listeners != null)
		{
			this.listeners.add(listener);
		}
	}

	public void setFormat(AudioFileFormat format)
	{
		this.format = format;
		fireChangeEvent();
	}

	public void setSignalLeft(float[] signalLeft)
	{
		this.signalLeft = signalLeft;
		fireChangeEvent();
	}

	public void setSignalRight(float[] signalRight)
	{
		this.signalRight = signalRight;
		fireChangeEvent();
	}

	public AudioFileFormat getFormat()
	{
		return format;
	}

	public float[] getSignalLeft()
	{
		return signalLeft;
	}

	public float[] getSignalRight()
	{
		return signalRight;
	}

	private void fireChangeEvent()
	{
		for (SignalListener listener : this.listeners)
		{
			listener.SignalChanged(new SignalEvent(this));
		}
	}
}
