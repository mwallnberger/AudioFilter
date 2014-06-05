package GUI;

import java.util.List;

import Common.Signal;
import Controller.MainController;
import GUI.FilterPanel;
import GUI.SignalEvent;
import GUI.SignalListener;
import GUI.SignalPanel;
import GUI.elements.TabPane;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame implements SignalListener {
	
	//test
	MainController controller;
	
	List<FilterPanel> filterpanels;
	List<SignalPanel> signalpanels;
	
	private JFrame frmTest;
	String filename;
	String dir;
	
	public MainWindow(MainController controller) {
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
	
	
	public void addSignal(Signal signal) {
		signal.addListener(this);
		//TODO
		//Creates a new Tab with the Signal
	}


	@Override
	public void SignalChanged(SignalEvent e)
	{
		Signal sig = e.getSource();
		
		if(sig!=null)
		{
			//TODO
		}
		
	}
	
	private void initialize() {
		//create frame (maybe change title)
		frmTest.setTitle("AudioTool, FUCKERS!");
		frmTest.setBounds(100, 100, 800, 800);
		frmTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//create tabframe
		TabPane tabPanel = new TabPane();
		frmTest.add(tabPanel);
		
		//create toolbar
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		frmTest.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		//create openbutton and add it to toolbar
		JButton openButton = new JButton("");
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser c = new JFileChooser();
				//TODO use IOManager when available
			    if (c.showOpenDialog(tabPanel) == JFileChooser.APPROVE_OPTION) {
			    	tabPanel.createNewTab(c.getSelectedFile().getName());
			    }
			}
		});
		openButton.setIcon(new ImageIcon(MainWindow.class.getResource("/fatcow-hosting-icons-3000/32x32/folder.png")));
		toolBar.add(openButton);
		
		//TODO create savebutton and add it to toolbar
		JButton saveButton = new JButton("");
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		saveButton.setIcon(new ImageIcon(MainWindow.class.getResource("/fatcow-hosting-icons-3000/32x32/disk.png")));
		toolBar.add(saveButton);
		
		
	}

}
