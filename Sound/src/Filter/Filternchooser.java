package Filter;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.border.TitledBorder;

import tools.MiniTools;
import GUI.StyleSheet;


public class Filternchooser extends JLayeredPane implements ActionListener
{
	ArrayList<Filter> myArr;
	JButton add;
	public Filternchooser()
	{
		super();
		this.myArr = new ArrayList<Filter>();
		myArr.add(new Filter("1.Filter") );
		myArr.add(new Filter("2.Filter") );
		this.setBorder(	BorderFactory.createTitledBorder(BorderFactory.createLineBorder(StyleSheet.CORTEX_BLUE), "Filter", TitledBorder.LEFT, TitledBorder.CENTER, StyleSheet.FONT_H_12, StyleSheet.CORTEX_BLUE));
		this.setLayout(new GridBagLayout());
		this.add = new JButton("Add");
		this.add.addActionListener(this);
		this.add.setOpaque(false);
		makelayout();
		
	}
	
	public void makelayout()
	{
		this.removeAll();
		for(int x=0; x<myArr.size();x++)
		{
		    MiniTools.addComponent(this, myArr.get(x), 0, x, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,  GridBagConstraints.BOTH, new Insets(0,0,0,0), 1, 1);	    			
		}
	    MiniTools.addComponent(this, this.add, 1, myArr.size()-1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,  GridBagConstraints.BOTH, new Insets(0,0,0,0), 1, 1);	    
	    this.validate();
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==this.add)
		{
			this.myArr.add(new Filter((this.myArr.size()+1) + ". Filter"));
			makelayout();
			
		}
	}
	

}
