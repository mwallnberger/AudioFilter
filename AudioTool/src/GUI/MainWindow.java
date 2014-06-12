package GUI;

import java.util.List;

import Common.GeneralException;
import Common.Signal;
import Controller.MainController;
import GUI.elements.OpenSignalActionHandler;
import GUI.elements.SaveSignalActionHandler;
import GUI.elements.TabPanel;

import java.awt.EventQueue;

import javax.swing.JFrame; 
import javax.swing.JToolBar;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame{
	
	MainController controller;
	
	private JFrame frmTest;
	String filename;
	String dir;

	private TabPanel tabPanel;
	
	public MainWindow(MainController controller) {
		super();
		this.controller = controller;
		frmTest = new JFrame();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmTest.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		initialize();
	}
	
	private void initialize(){
		//create frame (maybe change title)
		frmTest.setTitle("AudioTool!");
		frmTest.setBounds(100, 100, 1100, 350);
		frmTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frmTest.setResizable(false);

		//create tabframe
		tabPanel = new TabPanel(controller);
		frmTest.add(tabPanel);
		
		//create toolbar
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		frmTest.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		//create openbutton and add it to toolbar
		JButton openButton = new JButton("");
		openButton.addActionListener(new OpenSignalActionHandler(tabPanel, controller));
		
		openButton.setIcon(new ImageIcon(MainWindow.class.getResource("/fatcow-hosting-icons-3000/32x32/folder.png")));
		toolBar.add(openButton);
		
		//TODO create savebutton and add it to toolbar (IOManager)
		JButton saveButton = new JButton("");
		saveButton.addActionListener(new SaveSignalActionHandler(frmTest, controller));
		
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
