package GUI;


import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.UIManager;

import tools.MiniTools;

public class GUI extends JFrame
{
	public GraphingData diagramm;
	public ControlPanel controlpanel;
	public GUI()
	{
		super("Digitale Filter");
		this.getContentPane().setLayout(new GridBagLayout());
		this.setSize(1000,600);
		this.setPreferredSize(new Dimension(1000,600));
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{	
			e.printStackTrace();
		}
		
	     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     this.diagramm= new GraphingData();
	     this.controlpanel = new ControlPanel(this);
	     this.getContentPane().setBackground(StyleSheet.CORTEX_GREY);
	
	     MiniTools.addComponent(this.getContentPane(), this.diagramm, 0, 0, 1, 1, 1.0, 0.7, GridBagConstraints.CENTER,  GridBagConstraints.BOTH, new Insets(0,0,0,0), 1, 1);
	     MiniTools.addComponent(this.getContentPane(), this.controlpanel, 0, 1, 1, 1, 1.0, 0.3, GridBagConstraints.CENTER,  GridBagConstraints.BOTH, new Insets(10,10,10,10), 1, 1);
	
	     this.setLocation(10,10);
	     this.setVisible(true);
	}
}
