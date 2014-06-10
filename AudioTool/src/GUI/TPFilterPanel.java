package GUI;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import Common.GeneralException;
import Common.Signal;

public class TPFilterPanel extends FilterPanel {

	public TPFilterPanel() {
		this.setLayout(new GridLayout(3, 0));
		JButton tpfilter = new JButton("TPFilter");
		JButton hpfilter = new JButton("HPFilter");
		JButton play = new JButton("Play");
		
		this.add(tpfilter);
		this.add(hpfilter);
		this.add(play);
		
	}
	
	@Override
	public JPanel getFilterPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void performFiltering(Signal signal) throws GeneralException {
		
	
	}
	
}
