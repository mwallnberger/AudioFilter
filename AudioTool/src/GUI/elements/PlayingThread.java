package GUI.elements;

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
	private AudioFormat audioFormat;
//	private ByteArrayOutputStream bufferStream;
	private JButton button;
	
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
//		float[] floatLeft = signal.getSignalLeft();
//		float[] floatRight = signal.getSignalRight();
		
		byteBuffer = IOManager.convertToBytes(signal);
		
//		boolean stereo = floatRight != null;
//		
//		bufferStream.reset();
//		
//		int sampleBits = audioFormat.getSampleSizeInBits();
//		int bytesPerSample = audioFormat.getFrameSize();	//sampleBits / Byte.SIZE;
//		int maxValue = (int) Math.pow(2, sampleBits) - 1;
//		
//		
//		int floatIndex = 0;
//		while(floatIndex < floatLeft.length) {
//			float in = floatLeft[floatIndex];
//			if (in < -1.0) in = -1.0f;
//	        if (in > +1.0) in = +1.0f;
//	        
//	        // convert to bytes
//	        int s = (int) (maxValue * in);
//	        for(int i = 0; i < bytesPerSample; i++) {
//	        	bufferStream.write((byte) s);
//	        	s = s >> 8;
//	        }
//
//	        
//	        if(stereo) {
//	        	in = floatRight[floatIndex];
//				if (in < -1.0) in = -1.0f;
//		        if (in > +1.0) in = +1.0f;
//		        floatIndex ++;
//		        // convert to bytes
//		        s = (int) (maxValue * in);
//		        for(int i = 0; i < bytesPerSample; i++) {
//		        	bufferStream.write((byte) s);
//		        	s = s >> 8;
//		        }
//	        }
//	        floatIndex ++;
//		}
	}
	
	public void startPlaying() {
		if(!playing) {
			playing = true;
			new Thread(this).start();
		}
		
	}
	
	@Override
	public void run() {
		if(button != null) {
			button.setText("Stop");
		}
		
		SourceDataLine soundLine;
		try {
			soundLine = AudioSystem.getSourceDataLine(audioFormat);
			soundLine.open(audioFormat);
			soundLine.start();
		        
	        //byte[] byteBuffer = bufferStream.toByteArray();
	        int len = audioFormat.getFrameSize() * (byteBuffer.length/1000);
	        int index = 0;
	        while(playing && (len * index) < byteBuffer.length) {
	        	
	        	if((len * index + len) < byteBuffer.length) {
	        		soundLine.write(byteBuffer, index * len, len);
	        	}
	        	index ++;
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
	
	public boolean isPlaying() {
		return playing;
	}
	
	public void stopPlaying() {
		playing = false;
	}

}
