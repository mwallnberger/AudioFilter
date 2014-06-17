package Common;

import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioFileFormat;
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

	public void setName(String name)
	{
		if (name != null)
		{
			this.name = name;
		}
	}

	public synchronized void addListener(SignalListener listener)
	{
		if (this.listeners != null)
		{
			this.listeners.add(listener);
		}
	}

	public synchronized void removeListener(SignalListener listener)
	{
		if (this.listeners != null)
		{
			this.listeners.remove(listener);
		}
	}

	public synchronized void removeAllListeners()
	{
		listeners = new ArrayList<SignalListener>();
	}

	public void setFormat(AudioFileFormat format)
	{
		this.format = format;
	}

	public void setSignal(float[] signalLeft, float[] signalRight)
	{
		this.signalLeft = signalLeft;
		if (signalRight != null)
		{
			this.signalRight = signalRight;
		}
		fireChangeEvent();
	}

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

	private float[] calcSpec(float[] sig)
	{
		int N = sig.length;       
        float[] signal = new float[N];
        System.arraycopy( sig, 0, signal,
                           0, signal.length );        
        int Fs =  (int) this.format.getFormat().getSampleRate();    
        float df = Fs/N;     
        float[] f = new float[N/2];
        for(int i=0; i<N/2; i++)
        {
        f[i] = (float) (df*i);
        }      
        float[] S = new float[N/2];
        
        FloatFFT_1D fft = new FloatFFT_1D(N);
        fft.realForward(signal);            
        for(int i=0; i<N/2; i++){
        S[i] = (float) Math.sqrt(signal[2*i]*signal[2*i] +signal[2*i+1]*signal[2*i+1]);
        }
        
        float scalefactor = (float) ((double)S.length / ((double)Fs/2.0));
        float[] array = new float[(Fs/2)];
        int count = 0;
        for(int x = 0; x<array.length; x++)
        {
        	array[x]= S[(int) ((double)(x)*scalefactor)];
        }
        return array;
	}

	public float[] getSpectrum()
	{
		/*
		 * } FloatFFT_1D fft = new FloatFFT_1D((int)
		 * (this.format.getFormat().getSampleRate()/2));
		 * 
		 * float[] specL = new float[this.signalLeft.length]; System.arraycopy(
		 * this.signalLeft, 0, specL, 0, this.signalLeft.length );
		 * 
		 * float[] fftData = new float[this.signalLeft.length*2]; for (int i =
		 * 0; i < this.signalLeft.length; i++) { // copying audio data to the
		 * fft data buffer, imaginary part is 0 fftData[2 * i] =
		 * this.signalLeft[i]; fftData[2 * i + 1] = 0; }
		 * 
		 * // calculating the fft of the data, so we will have spectral power of
		 * each frequency component // fft.complexForward(fftData);
		 * 
		 * 
		 * 
		 * fft.realForward(fftData); float magnitude[] = new float[(int)
		 * (this.format.getFormat().getSampleRate() / 2)];
		 * 
		 * 
		 * /* if(this.signalRight!=null) { float[] specR = new
		 * float[this.signalLeft.length]; System.arraycopy( this.signalRight, 0,
		 * specR, 0, this.signalRight.length ); fft.realForward(specR);
		 * 
		 * for(int x = 0 ; x< specL.length; x++) { specL[x]=(float)
		 * ((specL[x]+specR[x])/2.0); } }
		 */
		/*
		 * for(int x =0; x< magnitude.length-1; x++) { float re = fftData[2*x];
		 * float im = fftData[2*x+1]; magnitude[x] = (float)
		 * Math.sqrt(re*re+im*im); } return magnitude;
		 */
		return calcSpec(this.signalLeft);
	}
}
