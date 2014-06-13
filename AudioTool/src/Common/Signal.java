package Common;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

import edu.emory.mathcs.jtransforms.fft.FloatFFT_1D;
import GUI.elements.SignalEvent;
import GUI.elements.SignalListener;

public class Signal
{
	private List<SignalListener> listeners;
	private AudioFileFormat format;
	private String name;
	private boolean changed;
	// values from -1 to 1
	private float[] signalLeft;
	private float[] signalRight;

	public Signal(AudioFileFormat format, float[] signalLeft, float[] signalRight, String name)
	{
		super();
		this.format = format;
		this.signalLeft = signalLeft;
		this.signalRight = signalRight;
		this.name = name;
		changed = false;
		listeners = new ArrayList<SignalListener>();
	}

	public String getName()
	{
		return name;
	}

	public synchronized void addListener(SignalListener listener)
	{
		if (this.listeners != null)
		{
			this.listeners.add(listener);
		}
	}
	
	public synchronized void removeListener(SignalListener listener) {
		if(this.listeners != null) {
			this.listeners.remove(listener);
		}
	}

	public void setFormat(AudioFileFormat format)
	{
		this.format = format;
//		fireChangeEvent();
	}
	
	public void setSignal(float[] signalLeft, float[] signalRight) {
		this.signalLeft = signalLeft;
		if(signalRight != null) {
			this.signalRight = signalRight;
		}
		fireChangeEvent();
	}

//	public void setSignalLeft(float[] signalLeft)
//	{
//		this.signalLeft = signalLeft;
//		fireChangeEvent();
//	}
//
//	public void setSignalRight(float[] signalRight)
//	{
//		this.signalRight = signalRight;
//		fireChangeEvent();
//	}

	public AudioFileFormat getFormat()
	{
		return format;
	}

	public float[] getSignalLeft()
	{
		if (signalLeft != null)
		{
			return signalLeft.clone();
		}
		return null;
	}

	public float[] getSignalRight()
	{
		if (signalRight != null)
		{
			return signalRight.clone();
		}
		return null;
	}

	private synchronized void fireChangeEvent()
	{
		SignalEvent event = new SignalEvent(this);
		changed = true;
		for (SignalListener listener : this.listeners)
		{
			listener.SignalChanged(event);
		}
	}

	public boolean isChanged()
	{
		return changed;
	}

	public void resetChanged()
	{
		changed = false;
	}

	public float[] getSpectrum()
	{
		FloatFFT_1D fft = new FloatFFT_1D((int) (this.format.getFormat().getSampleRate()/2.0));
		float[] left = this.signalLeft;
		fft.realForward(left);
		
		if(this.signalRight!=null)
		{
			float[] right = this.signalRight;
			fft.realForward(right);
			for(int x = 0 ; x< left.length; x++)
			{
				left[x]=(float) ((left[x]+right[x])/2.0);
			}
		}
		return left;
	}
}
