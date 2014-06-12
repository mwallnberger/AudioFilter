package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.*;
import org.jfree.chart.ChartPanel;

import Common.Signal;


public class SignalPanel extends JPanel {
	byte[] data;
	private XYSeries seriesRight;
	private XYSeries seriesLeft;
	private XYSeries seriesSpectrum;
	private Signal signal;
	XYSeriesCollection xyDataRight;
	XYSeriesCollection xyDataSpectrum;
	XYSeriesCollection xyDataLeft;
	XYPlot plotRight;
	XYPlot plotLeft;
	XYPlot plotSpectrum;

	public SignalPanel(Signal signal) {
		super();
		this.setBackground(Color.BLUE);
		this.setLayout(new GridBagLayout());
		this.signal = signal;
		xyDataRight = new XYSeriesCollection();
		xyDataSpectrum = new XYSeriesCollection();
		xyDataLeft = new XYSeriesCollection();		
		
		initialize();
	}
	
	public void paintSignal() {
		
		
		seriesSpectrum = new XYSeries("Spectrum");
		
		seriesRight = new XYSeries("Right");
		
		seriesLeft = new XYSeries("Left");
		
		float[] leftSignal = signal.getSignalLeft();
		float[] rightSignal = signal.getSignalRight();
		for(int i = 0; i < leftSignal.length; i++) {
			seriesLeft.add(i, leftSignal[i]);
		}
		if(rightSignal != null) {
			for(int i = 0; i < rightSignal.length; i++) {
				seriesRight.add(i, rightSignal[i]);
			}
		}
		
	}
	
	public void refresh() {
		xyDataRight.removeSeries(seriesRight);
		xyDataLeft.removeSeries(seriesLeft);
		xyDataSpectrum.removeSeries(seriesSpectrum);
		
		paintSignal();
		
		xyDataRight.addSeries(seriesRight);
		xyDataLeft.addSeries(seriesLeft);
		xyDataSpectrum.addSeries(seriesSpectrum);
	}
	
	public void initialize() {
		
		signal.addListener(new SignalListener() {

			@Override
			public void SignalChanged(SignalEvent e) {
				refresh();
			}
			
		});
			
		JPanel chartWindow = new JPanel(new FlowLayout());
		JPanel SignalWindow = new JPanel(new GridLayout(2, 0));
		JPanel SpectrumWindow = new JPanel(new GridLayout());
		chartWindow.setBackground(new Color(0xC8DDF2));
		
		paintSignal();
		
		xyDataRight.addSeries(seriesRight);
		xyDataLeft.addSeries(seriesLeft);
		xyDataSpectrum.addSeries(seriesSpectrum); 
		
		JFreeChart chartRight = ChartFactory.createXYLineChart("","","",xyDataRight,PlotOrientation.VERTICAL,false,false,false);
		chartRight.setBackgroundPaint(new Color(0xC8DDF2));
		TextTitle rightTitle = new TextTitle("Right", new Font("Verdana", Font.PLAIN, 20));
		chartRight.setTitle(rightTitle);
		
		plotRight = chartRight.getXYPlot();
		plotRight.getRangeAxis().setVisible(false);
		plotRight.getDomainAxis().setVisible(false);
		
		
		JFreeChart chartLeft = ChartFactory.createXYLineChart("","","",xyDataLeft,PlotOrientation.VERTICAL,false,false,false);
		chartLeft.setBackgroundPaint(new Color(0xC8DDF2));
		TextTitle leftTitle = new TextTitle("Left", new Font("Verdana", Font.PLAIN, 20));
		chartLeft.setTitle(leftTitle);
		plotLeft = chartLeft.getXYPlot();
		plotLeft.getRangeAxis().setVisible(false);
		plotLeft.getDomainAxis().setVisible(false);
		
		JFreeChart chartSpectrum = ChartFactory.createXYLineChart("","","",xyDataSpectrum,PlotOrientation.VERTICAL,false,false,false);
		chartSpectrum.setBackgroundPaint(new Color(0xC8DDF2));
		TextTitle spectrumTitle = new TextTitle("Spectrum", new Font("Verdana", Font.PLAIN, 20));
		chartSpectrum.setTitle(spectrumTitle);
		plotSpectrum = chartSpectrum.getXYPlot();
		plotSpectrum.getRangeAxis().setVisible(false);
		plotSpectrum.getDomainAxis().setVisible(false);
		
		
		XYItemRenderer test = plotRight.getRenderer();
        test.setSeriesPaint(0, Color.BLUE);		
		
		
        ChartPanel panelRight = new ChartPanel(chartRight);
        ChartPanel panelLeft = new ChartPanel(chartLeft);
        ChartPanel panelSpectrum = new ChartPanel(chartSpectrum);
        panelSpectrum.setMaximumSize(new Dimension(200, 200));
        
        SignalWindow.add(panelRight);
        SignalWindow.add(panelLeft);
        SignalWindow.setPreferredSize(new Dimension(700, 200));
        SpectrumWindow.add(panelSpectrum);
        SpectrumWindow.setPreferredSize(new Dimension(300, 200));
        
        chartWindow.add(SignalWindow);
        chartWindow.add(SpectrumWindow);
        this.add(chartWindow);
	}
	
	public Signal getSignal() {
		return signal;
	}
		
	
}
