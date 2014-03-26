package tone;

import java.awt.Color;
import java.awt.Dimension;

import javax.sound.sampled.AudioFormat;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public abstract class Tone extends JRadioButton
{
	String name;
	JLayeredPane OptionPanel;
	protected AudioFormat audioformat;

	public Tone(String name)
	{
		super(name);
		this.name=name;	
		
		OptionPanel = new JLayeredPane();
		OptionPanel.setBackground(Color.RED);
		OptionPanel.setPreferredSize(new Dimension(22,22));
		
	
		
	
	}
	
	public double getValue(double time)
	{
		return 0.0;
	}

	public String getName()
	{
		return name;
	}
	public byte[] getBytes()
	{
		return null;
	}
	

	
	public JLayeredPane getOptionPanel()
	{
		return OptionPanel;
	}
	public AudioFormat getAudioformat()
	{
		return audioformat;
	}
}
