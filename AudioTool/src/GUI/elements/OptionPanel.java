package GUI.elements;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Common.Signal;
import Controller.MainController;
import Filter.BPFilter;
import Filter.BSFilter;
import Filter.Filter;
import Filter.HPFilter;
import Filter.TPFilter;
import GUI.GeneralFilterPanel;

public class OptionPanel extends JPanel {
	
	private final Filter[] filters;
	
	public OptionPanel(Signal signal, MainController controller) {
		
		filters = new Filter[4];
		filters[0] = new TPFilter(signal);
		filters[1] = new HPFilter(signal);
		filters[2] = new BSFilter(signal);
		filters[3] = new BPFilter(signal);
		
		JFrame mainWindow = controller.getMainWindow();
		
		this.setLayout(new GridLayout(filters.length +1, 1));
		
		for(int i = 0; i < filters.length; i++) {
			JButton btnFilter = new JButton(filters[i].getName());
			btnFilter.addActionListener(new OpenFilterPanelAction(filters[i], mainWindow));
			this.add(btnFilter);
		}
		
		JButton play = new JButton("Play");
		play.setBackground(Color.red);
		
		//create new thread for playing sound (controller manages starting and stopping)
		controller.addPlayingThread(new PlayingThread(signal, play), signal);
		
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.tooglePlaying(signal);
			}
		});
		this.add(play);
		
	}
		
	class OpenFilterPanelAction implements ActionListener {
		
		private final Filter filter;
		private final JFrame mainWindow;
		
		public OpenFilterPanelAction(Filter filter, JFrame mainWindow) {
			this.filter = filter;
			this.mainWindow = mainWindow;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JDialog popup = new GeneralFilterPanel(filter, mainWindow); 
			popup.pack();
			popup.setLocationRelativeTo(mainWindow);
			popup.setVisible(true);
			
		}
		
	}
	
}
