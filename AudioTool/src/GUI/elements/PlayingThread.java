package GUI.elements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JButton;

import Common.Signal;
import IO.IOManager;

public class PlayingThread implements Runnable, MarkerChangedListener{
	
	private List<MarkerChangedListener> listeners;
	private final Signal signal;
	private volatile boolean playing;
	private volatile boolean paused;
	private AudioFormat audioFormat;
	private JButton button;
	private int currIndex;
	
	private byte[] byteBuffer;
	private double currIndexDouble;
	private int playByteLength;
	private boolean running;
	private static double MARKER_STEPS = 0.001;
	private static int PLAY_STEPS = 40000;

	public PlayingThread(Signal signal, JButton button) {
		this.signal = signal;
		this.button = button;
		
		signal.addListener(new SignalListener() {
			
			@Override
			public void SignalChanged(SignalEvent e) {
				if(playing) {
					stopPlaying();
				}
				initSignal();
			}
		});
		
		listeners = new ArrayList<MarkerChangedListener>();
		
		playing = false;
		audioFormat = signal.getFormat().getFormat();
		initSignal();

	}
	
	private void initSignal() {
		byteBuffer = IOManager.convertToBytes(signal);
		paused = false;
		playing = false;
		currIndex = 0;
		currIndexDouble = 0;
		playByteLength = audioFormat.getFrameSize() * (byteBuffer.length/PLAY_STEPS);
		int count = 10;
		while(playByteLength < audioFormat.getFrameSize()*10) {
			int play_steps = PLAY_STEPS/count;
			if(play_steps <= 0) {
				playByteLength = audioFormat.getFrameSize();
				break;
			}
			playByteLength = audioFormat.getFrameSize() * (byteBuffer.length/play_steps);
			count*=10;
		}
		
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
		running = true;
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
		        
	        
	        while(playing && !paused && (playByteLength * currIndex) <= byteBuffer.length) {
	        	
	        	if((playByteLength * currIndex + playByteLength) <= byteBuffer.length) {
	        		soundLine.write(byteBuffer, currIndex * playByteLength, playByteLength);
	        	} 
	        	else {
	        		int restPlayLength = byteBuffer.length - currIndex * playByteLength;
	        		while(restPlayLength % audioFormat.getFrameSize() != 0) {
	        			restPlayLength--;
	        		}
	        		soundLine.write(byteBuffer, currIndex * playByteLength, restPlayLength);
	        	}
	        	currIndex ++;
	        	if(((double) (currIndex * playByteLength) / (double)byteBuffer.length) >= currIndexDouble + MARKER_STEPS) {
	        		currIndexDouble = (double) (currIndex * playByteLength) / (double)byteBuffer.length;
	        		fireChangeEvent();
	        	}
	        	
	        }
	        if(!paused && (playByteLength * currIndex) > byteBuffer.length) {
	        	stopPlaying();
	        }
	        soundLine.stop();
	        soundLine.close();
		        
		} catch (LineUnavailableException e) {

		}
		if(button != null) {
			button.setText("Play");
			button.setForeground(new Color(12, 206, 2));
			button.setBackground(new Color(12, 206, 2));
		}
		running = false;
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
	
	public synchronized void removeAllMarkerChangedListeners() {
		listeners = new ArrayList<MarkerChangedListener>();
	}
	
	private synchronized void fireChangeEvent()
	{
		MarkerChangedEvent event = new MarkerChangedEvent(currIndexDouble);
		for (MarkerChangedListener listener : this.listeners)
		{
			listener.MarkerChanged(event);
		}
	}

	@Override
	public void MarkerChanged(MarkerChangedEvent e) {
		boolean oldplaying = playing;
		boolean oldpaused = paused;
		
		while(running) {
			paused = true;
		}	

		currIndexDouble = e.getValue();
		currIndex = (int) (currIndexDouble * (byteBuffer.length / playByteLength));

		if(oldplaying && !oldpaused) {
			startPlaying();
		}
		else {
			playing = oldplaying;
			paused = oldpaused;
		}
		
		
	}

}
