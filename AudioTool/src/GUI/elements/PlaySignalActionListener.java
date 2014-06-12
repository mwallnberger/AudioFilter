package GUI.elements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Common.Signal;

public class PlaySignalActionListener implements ActionListener{
	
	private final Signal signal;
	private PlayingThread playThread;
	
	public PlaySignalActionListener (Signal signal) {
		this.signal = signal;
		playThread = new PlayingThread(signal);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(playThread.isPlaying()) {
			playThread.stopPlaying();
		}
		else {
			new Thread(playThread).start();;
		}
		
		
		
	}
	

}
