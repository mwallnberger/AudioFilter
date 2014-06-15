package GUI;

import Common.GeneralException;
import Common.SaveSignal;
import Common.Signal;
import Controller.MainController;
import GUI.elements.OpenSignalActionHandler;
import GUI.elements.PlayingThread;

import java.awt.EventQueue;

import javax.swing.JFrame; 
import javax.swing.JToolBar;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame{
	
	MainController controller;
	
	private JFrame jFrame;
	String filename;
	String dir;

	private TabPanel tabPanel;
	
	public MainWindow(MainController controller) {
		super();
		this.controller = controller;
		jFrame = new JFrame();
		initialize();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					jFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	private void initialize(){
		//create frame (maybe change title)
		jFrame.setTitle("AudioTool!");
		jFrame.setBounds(100, 100, 1100, 450);
		jFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		jFrame.addWindowListener(new WindowAdapter() {
		
			@Override
			public void windowClosing(WindowEvent arg0) {
				closeOperation();
			}
		});
		

		//create tabframe
		tabPanel = new TabPanel(controller);
		
		OpenSignalActionHandler openAction = new OpenSignalActionHandler(tabPanel, controller);
		
		JMenuBar menuBar = new JMenuBar();
		jFrame.setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("Datei");
		menuBar.add(fileMenu);
		JMenuItem open = new JMenuItem("Datei öffnen");
		open.addActionListener(openAction);
		fileMenu.add(open);
		
		JMenuItem saveifChanged = new JMenuItem("Änderung speichern");
		saveifChanged.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					SaveSignal.saveActiveSignal(tabPanel, controller, -1, false);
				} catch (GeneralException e) {
					
				}
			}
		});
		fileMenu.add(saveifChanged);
		
		JMenuItem save = new JMenuItem("Datei speichern");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					SaveSignal.saveActiveSignal(tabPanel, controller, -1, true);
				} catch (GeneralException e) {
					
				}
			}
		});
		fileMenu.add(save);
		
		fileMenu.addSeparator();
		
		JMenuItem exit = new JMenuItem("Beenden");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				closeOperation();
			}
		});
		fileMenu.add(exit);
		
		JMenu playMenu = new JMenu("Abspielen");
		menuBar.add(playMenu);
		
		JMenuItem play = new JMenuItem("Play");
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.startPlaying(controller.getActiveSignal());
			}
		});
		playMenu.add(play);
		
		JMenuItem pause = new JMenuItem("Pause");
		pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.pausePlaying(controller.getActiveSignal());
			}
		});
		playMenu.add(pause);
		
		JMenu signalMenu = new JMenu("Signalerzeugung");
		menuBar.add(signalMenu);
		
		JMenuItem signalGen = new JMenuItem("Signal erzeugen");
		signalGen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog popup = new SignalGenerationWindow(jFrame, controller); 
				popup.pack();
				popup.setLocationRelativeTo(jFrame);
				popup.setVisible(true);
			}
		});
		signalMenu.add(signalGen);
		
		JMenuItem stop = new JMenuItem("Stop");
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.stopPlaying(controller.getActiveSignal());
			}
		});
		playMenu.add(stop);
		
		//create toolbar
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		jFrame.add(toolBar, BorderLayout.NORTH);
		
		//create openbutton and add it to toolbar
		JButton openButton = new JButton("");
		
		openButton.addActionListener(openAction);
		
		openButton.setIcon(new ImageIcon(MainWindow.class.getResource("/fatcow-hosting-icons-3000/32x32/folder.png")));
		toolBar.add(openButton);
		
		//TODO create savebutton and add it to toolbar (IOManager)
		JButton saveButton = new JButton("");
		saveButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					SaveSignal.saveActiveSignal(jFrame, controller, -1, false);
				} catch (GeneralException e) {
					JOptionPane.showMessageDialog(jFrame, e.toString());
				}
				
			}
			
		});
		
		saveButton.setIcon(new ImageIcon(MainWindow.class.getResource("/fatcow-hosting-icons-3000/32x32/disk.png")));
		toolBar.add(saveButton);	
		
		jFrame.add(tabPanel);
	}

	public TabPanel getTabPanel() {
		return tabPanel;
	}
	
	private void closeOperation() {
		if(JOptionPane.showConfirmDialog(jFrame, 
	            "Möchten Sie AudioTool wirklich beenden ?", "Beenden", 
	            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			
			controller.getTabs().forEach(t -> {
				controller.closeSignal(t.getSignal());
			});
			
//			for(Signal s : controller.getSignals()) {
//				try {
//					if(!SaveSignal.saveSignalIfChanged(jFrame, s, controller)) {
//						return;
//					}
//				} catch (GeneralException e) {
//					
//				}
//			}
			jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			jFrame.dispose();
		}
	}
}
