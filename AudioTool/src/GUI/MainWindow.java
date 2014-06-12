package GUI;

import Common.GeneralException;
import Common.Signal;
import Controller.MainController;
import GUI.elements.OpenSignalActionHandler;
import GUI.elements.SaveSignal;
import GUI.elements.TabPanel;

import java.awt.EventQueue;

import javax.swing.JFrame; 
import javax.swing.JToolBar;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
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
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					jFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		initialize();
	}
	
	private void initialize(){
		//create frame (maybe change title)
		jFrame.setTitle("AudioTool!");
		jFrame.setBounds(100, 100, 1100, 450);
		jFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		jFrame.addWindowListener(new WindowAdapter() {
		
			@Override
			public void windowClosing(WindowEvent arg0) {
				
				if(JOptionPane.showConfirmDialog(jFrame, 
			            "Möchten Sie AudioTool wirklich beenden ?", "Beenden", 
			            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					
					for(Signal s : controller.getSignals()) {
						try {
							SaveSignal.save(jFrame, s, controller);
						} catch (GeneralException e) {
							
						}
					}
					jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
				}
			}
		});

		//create tabframe
		tabPanel = new TabPanel(controller);
		jFrame.add(tabPanel);
		
		//create toolbar
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		jFrame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		//create openbutton and add it to toolbar
		JButton openButton = new JButton("");
		
		openButton.addActionListener(new OpenSignalActionHandler(tabPanel, controller));
		
		openButton.setIcon(new ImageIcon(MainWindow.class.getResource("/fatcow-hosting-icons-3000/32x32/folder.png")));
		toolBar.add(openButton);
		
		//TODO create savebutton and add it to toolbar (IOManager)
		JButton saveButton = new JButton("");
		saveButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					SaveSignal.save(jFrame, controller);
				} catch (GeneralException e) {
					JOptionPane.showMessageDialog(jFrame, "Es ist kein Signal geöffnet.");
				}
				
			}
			
		});
		
		saveButton.setIcon(new ImageIcon(MainWindow.class.getResource("/fatcow-hosting-icons-3000/32x32/disk.png")));
		toolBar.add(saveButton);		
	}
	
	public Signal getActiveSignal() {
		return tabPanel.getActiveSignal();
	}
	
	public void removeSignal(Signal signal) {
		tabPanel.removeSignal(signal);
	}
}
