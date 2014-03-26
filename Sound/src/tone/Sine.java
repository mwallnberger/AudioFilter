package tone;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioFormat;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import GUI.StyleSheet;

import tools.MiniTools;

public class Sine extends Tone implements ChangeListener,ActionListener
{
	int frequency;
	JSlider sliderfrequency;
	JTextField boxfrequncy;
	int lenght;
	int amplitude;
	JSlider slideramplitude;
	JTextField boxamplitude;
	public Sine(int frequency)
	{
		super("Sinus");
		this.frequency=frequency;
		createOptionPanel();
		this.lenght=1;
	}
	
	private void createOptionPanel()
	{
		JLabel frequency = new JLabel("Frequenz (Hz):");
		JLabel amplitude = new JLabel("Amplitude");
		amplitude.setForeground(StyleSheet.CORTEX_BLUE);
		frequency.setForeground(StyleSheet.CORTEX_BLUE);
		this.OptionPanel = new JLayeredPane();
		this.OptionPanel.setBorder(	BorderFactory.createTitledBorder(BorderFactory.createLineBorder(StyleSheet.CORTEX_BLUE), "Optionen", TitledBorder.LEFT, TitledBorder.CENTER, StyleSheet.FONT_H_12, StyleSheet.CORTEX_BLUE));
		this.OptionPanel.setLayout(new GridBagLayout());
		this.sliderfrequency= new JSlider(JSlider.HORIZONTAL,1, 25000, 1);
		this.sliderfrequency.setMajorTickSpacing(1000);
		this.sliderfrequency.setPaintTicks(true);
		this.sliderfrequency.setOpaque(false);
		this.sliderfrequency.addChangeListener(this);
		this.boxfrequncy = new JTextField(5);
		this.boxfrequncy.addActionListener(this);
		this.sliderfrequency.setValue(300);
		
		this.slideramplitude= new JSlider(JSlider.HORIZONTAL,0, 100, 1);
		this.slideramplitude.setMajorTickSpacing(10);
		this.slideramplitude.setPaintTicks(true);
		this.slideramplitude.setOpaque(false);

		this.slideramplitude.addChangeListener(this);
		this.boxamplitude = new JTextField(5);
		this.boxamplitude.addActionListener(this);
		this.slideramplitude.setValue(100);
		
	    MiniTools.addComponent(this.OptionPanel , frequency, 0, 0, 1, 1, 1.0, 0.7, GridBagConstraints.CENTER,  GridBagConstraints.BOTH, new Insets(0,0,0,0), 1, 1);
	    MiniTools.addComponent(this.OptionPanel , sliderfrequency, 1, 0, 1, 1, 1.0, 0.7, GridBagConstraints.CENTER,  GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 1, 1);
	    MiniTools.addComponent(this.OptionPanel , boxfrequncy, 2, 0, 2, 1, 1.0, 0.7, GridBagConstraints.CENTER,  GridBagConstraints.NONE, new Insets(0,0,0,0), 1, 1);

	    MiniTools.addComponent(this.OptionPanel , amplitude, 0, 1, 1, 1, 1.0, 0.7, GridBagConstraints.CENTER,  GridBagConstraints.BOTH, new Insets(0,0,0,0), 1, 1);
	    MiniTools.addComponent(this.OptionPanel , this.slideramplitude, 1, 1, 1, 1, 1.0, 0.7, GridBagConstraints.CENTER,  GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 1, 1);
	    MiniTools.addComponent(this.OptionPanel , this.boxamplitude, 2, 1, 2, 1, 1.0, 0.7, GridBagConstraints.CENTER,  GridBagConstraints.NONE, new Insets(0,0,0,0), 1, 1);

	}
	public double getValue(double time)
	{
		return Math.sin(2*Math.PI*this.frequency*time)*this.amplitude/100;
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		if(e.getSource() ==this.sliderfrequency)
		{
			this.boxfrequncy.setText(String.valueOf(sliderfrequency.getValue()));
		}
		if(e.getSource() ==this.slideramplitude)
		{
			this.amplitude=slideramplitude.getValue();

			this.boxamplitude.setText(String.valueOf(amplitude));

		}
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() ==this.boxfrequncy)
		{
			try
			{
				int value= Integer.valueOf(this.boxfrequncy.getText());
				if(value<1||value>15000)
				{
					throw new Exception();
				}
				this.sliderfrequency.setValue(value);
			}
			catch(Exception d)
			{
				
			}
		}
		if(e.getSource() ==this.boxamplitude)
		{
			try
			{
				int value= Integer.valueOf(this.boxamplitude.getText());
				if(value<0||value>100)
				{
					throw new Exception();
				}
				this.slideramplitude.setValue(value);
				this.amplitude=value;
			}
			catch(Exception d)
			{
				
			}
		}
		
	}
	public byte[] getBytes()
	{
		this.frequency=this.sliderfrequency.getValue();
		int samplerate= frequency*4;
		if(samplerate<200)
		{
			samplerate=200;
		}
		this.audioformat = new AudioFormat(samplerate, 8, 1, true, false);

		int werte = samplerate*this.lenght;
		
		byte[] data = new byte[werte];
		
		for(int x =0; x<werte; x++)
		{
			data[x] = (byte) (127*this.getValue((double)x/(double)samplerate));	
		}
		return data;
	}

	public AudioFormat getAudioformat()
	{
		return audioformat;
	}
	
}
