package GUI.elements;

import java.awt.Color;
import java.io.ByteArrayOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JButton;

import Common.Signal;
import GUI.SignalEvent;
import GUI.SignalListener;
import IO.IOManager;

public class PlayingThread implements Runnable{
	
	private final Signal signal;
	private volatile boolean playing;
	private volatile boolean paused;
	private AudioFormat audioFormat;
//	private ByteArrayOutputStream bufferStream;
	private JButton button;
	private int currIndex;
	
	private byte[] byteBuffer;

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
		
		playing = false;
		audioFormat = signal.getFormat().getFormat();
//		bufferStream = new ByteArrayOutputStream();
		initSignal();

	}
	
	private void initSignal() {
		currIndex = 0;
		byteBuffer = IOManager.convertToBytes(signal);

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
	        	index ++;
	        }
	        if(paused && (len * index) < byteBuffer.length) {
	        	currIndex = index;
	        }
	        else {
	        	currIndex = 0;
	        	playing = false;
	        	paused = false;
	        }
	        if(button != null) {
    			button.setText("Play");
    			button.setForeground(new Color(12, 206, 2));
    			button.setBackground(new Color(12, 206, 2));
    		}
	        soundLine.stop();
	        soundLine.close();
		        
		} catch (LineUnavailableException e) {

		}
		playing = false;
		if(button != null) {
			button.setText("Play");
		}
	}
	
	

}
