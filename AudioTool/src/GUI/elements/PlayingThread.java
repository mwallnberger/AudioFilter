package GUI.elements;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JButton;

import Common.Signal;
import IO.IOManager;

public class PlayingThread implements Runnable{
	
	private List<MarkerChangedListener> listeners;
	private final Signal signal;
	private volatile boolean playing;
	private volatile boolean paused;
	private AudioFormat audioFormat;
//	private ByteArrayOutputStream bufferStream;
	private JButton button;
	private int currIndex;
	
	private byte[] byteBuffer;
	private double currIndexDouble;
	private static double MARKER_STEPS = 0.005;

	public PlayingThread(Signal signal, JButton button) {
		this.signal = signal;
		this.button = button;
		
		signal.addListener(new SignalListener() {
			
			@Override
			public void SignalChanged(SignalEvent e) {
				if(playing) {
					playing = false;
				}
				initSignal();
			}
		});
		
		listeners = new ArrayList<MarkerChangedListener>();
		
		playing = false;
		audioFormat = signal.getFormat().getFormat();
//		bufferStream = new ByteArrayOutputStream();
		initSignal();

	}
	
	private void initSignal() {
		byteBuffer = IOManager.convertToBytes(signal);
		paused = false;
		playing = false;
		currIndex = 0;
		currIndexDouble = 0;
		if(button != null) {
			button.setText("Play");
			button.setForeground(new Color(12, 206, 2));
			button.setBackground(new Color(12, 206, 2));
		}

	}
	
	public boolean isPlaying() {
		return playing;
	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public void startPlaying() {
		if(!playing || paused) {
			playing = true;
			paused = false;
			new Thread(this).start();
		}		
	}
	
	public void pausePlaying() {
		if(playing) {
			paused = true;
		}
	}
	
	public void stopPlaying() {
		paused = false;
		playing = false;
		currIndex = 0;
		currIndexDouble = 0;
        fireChangeEvent();
	}
	
	@Override
	public void run() {
		if(button != null) {
			button.setText("Pause");
			button.setForeground(new Color(205, 205, 0));
			button.setBackground(new Color(205, 205, 0));
		}
		
		SourceDataLine soundLine;
		try {
			soundLine = AudioSystem.getSourceDataLine(audioFormat);
			soundLine.open(audioFormat);
			soundLine.start();
		        
	        //byte[] byteBuffer = bufferStream.toByteArray();
	        int len = audioFormat.getFrameSize() * (byteBuffer.length/10000);
	        if(len < audioFormat.getFrameSize()) {
	        	len = audioFormat.getFrameSize();
	        }
	        int index = currIndex;
	        while(playing && !paused && (len * index) < byteBuffer.length) {
	        	
	        	if((len * index + len) < byteBuffer.length) {
	        		soundLine.write(byteBuffer, index * len, len);
	        	}
	        	if(((float) (index * len) / (float)byteBuffer.length) >= currIndexDouble + MARKER_STEPS) {
	        		currIndexDouble = (double) (index * len) / (double)byteBuffer.length;
	        		fireChangeEvent();
	        	}
	        	index ++;
	        }
	        if(paused && (len * index) < byteBuffer.length) {
	        	currIndex = index;
	        }
	        else {
	        	stopPlaying();
	        }
	        soundLine.stop();
	        soundLine.close();
		        
		} catch (LineUnavailableException e) {

		}
		playing = false;
		if(button != null) {
			button.setText("Play");
			button.setForeground(new Color(12, 206, 2));
			button.setBackground(new Color(12, 206, 2));
		}
	}
	
	public synchronized void addMarkerChangedListener(MarkerChangedListener listener)
	{
		if (this.listeners != null)
		{
			this.listeners.add(listener);
		}
	}
	
	public synchronized void removeMarkerChangedListener(MarkerChangedListener listener) {
		if(this.listeners != null) {
			this.listeners.remove(listener);
		}
	}
	
	private synchronized void fireChangeEvent()
	{
		MarkerChangedEvent event = new MarkerChangedEvent(currIndexDouble);
		for (MarkerChangedListener listener : this.listeners)
		{
			listener.MarkerChanged(event);
		}
	}

}
