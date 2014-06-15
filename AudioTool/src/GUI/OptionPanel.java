package GUI;

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
import GUI.elements.PlayingThread;

public class OptionPanel extends JPanel {
	
	private final Filter[] filters;
	private JButton btnPlay;
	
	public OptionPanel(Signal signal, MainController controller) {
		
		filters = new Filter[4];
		filters[0] = new TPFilter(signal);
		filters[1] = new HPFilter(signal);
		filters[2] = new BSFilter(signal);
		filters[3] = new BPFilter(signal);
		
		JFrame mainWindow = controller.getMainWindow();
		
		this.setLayout(new GridLayout(0, 1));
		
		for(int i = 0; i < filters.length; i++) {
			JButton btnFilter = new JButton(filters[i].getName());
			btnFilter.addActionListener(new OpenFilterPanelAction(filters[i], mainWindow, controller));
			this.add(btnFilter);
		}
		
		btnPlay = new JButton("Play");
		btnPlay.setForeground(new Color(12, 206, 2));
		btnPlay.setBackground(new Color(12, 206, 2));
		
		//create new thread for playing sound (controller manages starting and stopping)
//		controller.addPlayingThread(new PlayingThread(signal, btnPlay), signal);
		
		btnPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.tooglePlaying(signal);
			}
		});
		this.add(btnPlay);
		
		JButton btnStop = new JButton("Stop");
		btnStop.setForeground(Color.red);
		btnStop.setBackground(Color.red);
		btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.stopPlaying(signal);
			}
		});
		this.add(btnStop);
		
	}
	
	public JButton getPlayButton() {
		return btnPlay;
	}
		
	class OpenFilterPanelAction implements ActionListener {
		
		private final Filter filter;
		private final JFrame mainWindow;
		private MainController controller;
		
		public OpenFilterPanelAction(Filter filter, JFrame mainWindow, MainController controller) {
			this.filter = filter;
			this.mainWindow = mainWindow;
			this.controller = controller;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JDialog popup = new GeneralFilterWindow(filter, mainWindow, controller); 
			popup.pack();
			popup.setLocationRelativeTo(mainWindow);
			popup.setVisible(true);
			
		}
		
	}
	
}
