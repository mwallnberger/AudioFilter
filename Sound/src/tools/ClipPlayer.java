package tools;
import javax.sound.sampled.Clip;

public class ClipPlayer extends Thread
{
	Clip toplay;
	public ClipPlayer(Clip toplay)
	{
		this.toplay=toplay;	
	}
	
	public void run()
	{
		
		toplay.start();
		try {
			
		      Thread.sleep(toplay.getMicrosecondLength());   // 10 mins in ms
		    }
		    catch(InterruptedException e)
		    { System.out.println("Sleep Interrupted"); }
	}

}
