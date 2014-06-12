package GUI.elements;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import Common.Signal;

public class PlayingThread implements Runnable{
	
	private final Signal signal;
	private boolean playing;
	private AudioFormat audioFormat;

	public PlayingThread(Signal signal) {
		this.signal = signal;
		playing = false;
		audioFormat = signal.getFormat().getFormat();
	}
	
	@Override
	public void run() {
		playing = true;
		float[] floatLeft = signal.getSignalLeft();
		byte[] buffer = new byte[floatLeft.length*2];
		
		int bufferIndex = 0;
		int floatIndex = 0;
		while(floatIndex < floatLeft.length) {
			float in = floatLeft[floatIndex];
			if (in < -1.0) in = -1.0f;
	        if (in > +1.0) in = +1.0f;
	        floatIndex ++;
	        // convert to bytes
	        short s = (short) (Short.MAX_VALUE * in);
	        buffer[bufferIndex] = (byte) s;
	        bufferIndex++;
	        buffer[bufferIndex] = (byte) (s >> 8);
	        bufferIndex++;
		}
		
		SourceDataLine soundLine;
		try {
			soundLine = AudioSystem.getSourceDataLine(audioFormat);
			soundLine.open(audioFormat);
			soundLine.start();
		        int len = audioFormat.getFrameSize();
		        
		        int index = 0;
		        do {
		        	signal.getSignalLeft();
		        	if((len * index) < buffer.length) {
		        		soundLine.write(buffer, index * len, len);
		        	}
		        	index ++;
		        } while(playing && (len * index) < buffer.length); 	
		        soundLine.stop();
		        soundLine.close();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isPlaying() {
		return playing;
	}
	
	public void stopPlaying() {
		playing = false;
	}

}
