package tone;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import GUI.StyleSheet;
import tools.AudioFileFilter;
import tools.MiniTools;

public class File extends Tone implements ActionListener 
{
	JTextField path;
	JButton showfilechooser;
		
	public File()
	{
		super("File");
		createOptionPanel();
	}
	
	private void createOptionPanel()
	{
		JLabel file = new JLabel("File:");
		file.setForeground(StyleSheet.CORTEX_BLUE);
		JLabel amplitude = new JLabel("Amplitude");
		this.OptionPanel = new JLayeredPane();
		this.OptionPanel.setBorder(	BorderFactory.createTitledBorder(BorderFactory.createLineBorder(StyleSheet.CORTEX_BLUE), "Optionen", TitledBorder.LEFT, TitledBorder.CENTER, StyleSheet.FONT_H_12, StyleSheet.CORTEX_BLUE));
		this.OptionPanel.setLayout(new GridBagLayout());
		this.path = new JTextField(30);
		this.showfilechooser= new JButton("...");
		this.showfilechooser.addActionListener(this);

		
	    MiniTools.addComponent(this.OptionPanel , file, 0, 0, 1, 1, 1.0, 0.7, GridBagConstraints.CENTER,  GridBagConstraints.BOTH, new Insets(0,0,0,0), 1, 1);
	    MiniTools.addComponent(this.OptionPanel , path, 1, 0, 1, 1, 1.0, 0.7, GridBagConstraints.CENTER,  GridBagConstraints.NONE, new Insets(0,0,0,0), 1, 1);
	    MiniTools.addComponent(this.OptionPanel , showfilechooser, 2, 0, 1, 1, 1.0, 0.7, GridBagConstraints.WEST,  GridBagConstraints.NONE, new Insets(0,0,0,0), 1, 1);


	}
	public double getValue(double time)
	{
		return 0;
	}

	
	public byte[] getBytes()
	{
		 java.io.File file = new  java.io.File(this.path.getText());
		 
		 if(file.exists())
		 {
			 try
			 {
				 AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
		         this.audioformat     = audioInputStream.getFormat();
		      
		         int size      = (int) (audioformat.getFrameSize() * audioInputStream.getFrameLength());
		         byte[] data       = new byte[size];
		         audioInputStream.read(data, 0, size);
		         return data;
			 }
			 catch(Exception d)
			 {
				 d.printStackTrace();
			 } 
		 }
		 
		 return null;
	}

	public AudioFormat getAudioformat()
	{
		return audioformat;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==this.showfilechooser)
		{
			final JFileChooser fc = new JFileChooser();
			
			    fc.setFileFilter(new AudioFileFilter());
			    int returnVal = fc.showOpenDialog(this);
			    if (returnVal == JFileChooser.APPROVE_OPTION) 
			    {
			    	java.io.File file = fc.getSelectedFile();
			    	this.path.setText(file.getAbsolutePath());
			    }
	       
		} 
	}
}
