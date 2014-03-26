package GUI;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import Filter.Filternchooser;

import tone.*;
import tools.ClipPlayer;
import tools.MiniTools;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements ActionListener
{
	JLayeredPane choosingpanel;
	JButton gen;
	JButton play;

	GUI hauptframe;
	
	// Chossingpanel
	Sine sine;
	Rect rect;
	Tri tri;
	File file;
	ButtonGroup group;
	Tone selected;
	private byte[] data;
	private AudioFormat audioformat;
	
	
	
	public ControlPanel(GUI hauptframe) 
	{
		super();
		this.setLayout(new GridBagLayout());
		this.hauptframe=hauptframe;
		
		// Chossingpanel
		this.choosingpanel = new JLayeredPane();
		this.choosingpanel.setLayout(new GridBagLayout());
		Border border = BorderFactory.createTitledBorder("Funktion");
		choosingpanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(StyleSheet.CORTEX_BLUE), "Funktion", TitledBorder.LEFT, TitledBorder.CENTER, StyleSheet.FONT_H_12, StyleSheet.CORTEX_BLUE));
		this.sine = new Sine(2);
		this.sine.setOpaque(false);
		this.sine.setForeground(StyleSheet.CORTEX_BLUE);

		this.sine.addActionListener(this);
		this.rect= new Rect(2);
		this.rect.setOpaque(false);
		this.rect.setForeground(StyleSheet.CORTEX_BLUE);


		this.rect.addActionListener(this);
		this.tri = new Tri(2);
		this.tri.setOpaque(false);
		this.tri.setForeground(StyleSheet.CORTEX_BLUE);


		this.tri.addActionListener(this);
		this.file= new File();
		this.file.setOpaque(false);
		this.file.setForeground(StyleSheet.CORTEX_BLUE);

		this.file.addActionListener(this);
		

		
		this.group = new ButtonGroup();
		this.group.add(sine);
		this.group.add(rect);
		this.group.add(tri);
		this.group.add(file);

		
	    MiniTools.addComponent(choosingpanel, this.sine, 0, 0, 1, 1, 1.0, 0.2, GridBagConstraints.CENTER,  GridBagConstraints.BOTH, new Insets(0,0,0,0), 1, 1);
	    MiniTools.addComponent(choosingpanel, this.rect, 0, 1, 1, 1, 1.0, 0.2, GridBagConstraints.CENTER,  GridBagConstraints.BOTH, new Insets(0,0,0,0), 1, 1);
	    MiniTools.addComponent(choosingpanel, this.tri, 0, 2, 1, 1, 1.0, 0.2, GridBagConstraints.CENTER,  GridBagConstraints.BOTH, new Insets(0,0,0,0), 1, 1);
	    MiniTools.addComponent(choosingpanel, this.file, 0, 3, 1, 1, 1.0, 0.2, GridBagConstraints.CENTER,  GridBagConstraints.BOTH, new Insets(0,0,0,0), 1, 1);

	    MiniTools.addComponent(this, this.choosingpanel, 0, 0, 1, 2, 1.0, 1.0, GridBagConstraints.CENTER,  GridBagConstraints.BOTH, new Insets(0,0,0,0), 1, 1);
	    
	    this.group.setSelected(this.sine.getModel(), true);
	    this.selected=sine;
	    MiniTools.addComponent(this, selected.getOptionPanel(), 1, 0, 1, 2, 1.0, 1.0, GridBagConstraints.CENTER,  GridBagConstraints.BOTH, new Insets(0,0,0,0), 1, 1);	    
	    MiniTools.addComponent(this, new Filternchooser(), 2, 0, 1, 2, 1.0, 1.0, GridBagConstraints.CENTER,  GridBagConstraints.BOTH, new Insets(0,0,0,0), 1, 1);	    

	    this.gen= new JButton("Gen");
	    this.gen.addActionListener(this);
	    this.gen.setOpaque(false);
	    this.play= new JButton("Play");
	    this.play.setOpaque(false);

	    this.play.addActionListener(this);
	    this.setOpaque(false);
	    MiniTools.addComponent(this, gen, 3, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,  GridBagConstraints.BOTH, new Insets(30,30,30,30), 1, 1);	    
	    MiniTools.addComponent(this, play, 3, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,  GridBagConstraints.BOTH, new Insets(30,30,30,30), 1, 1);	    
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if( e.getSource() instanceof Tone)
		{
			if(this.selected!=null)
			{
				this.remove(this.selected.getOptionPanel());
			}
			this.selected= (Tone) e.getSource();
		
		    MiniTools.addComponent(this, selected.getOptionPanel(), 1, 0, 1, 2, 1.0, 1.0, GridBagConstraints.CENTER,  GridBagConstraints.BOTH, new Insets(0,0,0,0), 1, 1);
			
			this.repaint();
			this.validate();
		}
		if( e.getSource() == this.gen)
		{
			this.data= this.selected.getBytes();
			this.audioformat= this.selected.getAudioformat();
			if(this.selected instanceof Sine || this.selected instanceof Rect )
			{
				this.hauptframe.diagramm.setData(data);
				this.hauptframe.diagramm.autojustage();
			}
			else
			{
				int buffer=0;
				int counter=0;
				this.hauptframe.diagramm.clear();
				for(int x =0; x<data.length-1; x=x+2)
	            {
	            	buffer+=data[x]+data[x+1];
	            	counter++;
	            	if(counter>100)
	            	{
	            		this.hauptframe.diagramm.addValue(x, buffer/counter);
	            		counter=0;
	            		buffer=0;
	            	}
	            }
				this.hauptframe.diagramm.autojustage();
				this.hauptframe.diagramm.paint();
			}
		}
		if( e.getSource() == this.play)
		{
			if(this.audioformat!=null)
			{
				try
				{
				Clip clip = (Clip) AudioSystem.getClip();
				clip.open(this.audioformat, data,0,this.data.length);
				(new ClipPlayer(clip)).start();
				}
				catch(Exception d)
				{
					
				}
			}
		}
	}
}
