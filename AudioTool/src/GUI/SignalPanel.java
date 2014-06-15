package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.*;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.*;
import org.jfree.chart.ChartPanel;

import Common.Signal;
import Controller.MainController;
import GUI.elements.MarkerChangedEvent;
import GUI.elements.MarkerChangedListener;
import GUI.elements.SignalEvent;
import GUI.elements.SignalListener;


public class SignalPanel extends JPanel implements MarkerChangedListener, SignalListener{
	
	private List<MarkerChangedListener> listeners;
	
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
	private JPanel SignalWindow;
	private JPanel SpectrumWindow;
	private boolean stereo;
	
	private int ticking = 1;
	private int signalLength;
	private double currIndexDouble;
	private ValueMarker valueMarker;
	
	private static int MAX_NUMBER_OF_VALUES = 50000;

	public SignalPanel(Signal signal, MainController controller) {
		super();
		this.setBackground(new Color(0xC8DDF2));
		this.setLayout(new BorderLayout());
		this.signal = signal;
		this.stereo = false;
		xyDataRight = new XYSeriesCollection();
		xyDataSpectrum = new XYSeriesCollection();
		xyDataLeft = new XYSeriesCollection();		
		listeners = new ArrayList<MarkerChangedListener>();
		initialize();
	}
	
	public void paintSignal() {
		
		seriesLeft = new XYSeries("Left");
		seriesRight = new XYSeries("Right");
		seriesSpectrum = new XYSeries("Spectrum");
		
		float[] leftSignal = signal.getSignalLeft();
		float[] rightSignal = signal.getSignalRight();
		float[] spectrumSignal = signal.getSpectrum();
		
		signalLength = leftSignal.length;
		
		if(rightSignal != null) {
			stereo = true;
		}
		
		while(leftSignal.length / ticking > MAX_NUMBER_OF_VALUES) {
			ticking ++;
		}
		
		for(int i = 0; i < leftSignal.length; i+= ticking) {
			seriesLeft.add(i, leftSignal[i]);
		}
		if(stereo) {
			for(int i = 0; i < rightSignal.length; i+=ticking) {
				seriesRight.add(i, rightSignal[i]);
			}
		}	
		
		for(int i = 0; i < spectrumSignal.length; i+= ticking) {
			seriesSpectrum.add(i, spectrumSignal[i]);
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
	
	public void refreshMarker(double currIndexDouble) {
		this.currIndexDouble = currIndexDouble;
		valueMarker.setValue(this.currIndexDouble * signalLength);
	}
	
	public void addMarkerChangedListener(MarkerChangedListener listener)
	{
		if (this.listeners != null)
		{
			this.listeners.add(listener);
		}
	}
	
	public void removeMarkerChangedListener(MarkerChangedListener listener) {
		if(this.listeners != null) {
			this.listeners.remove(listener);
		}
	}
	
	public void removeAllMarkerChangedListeners() {
		listeners = new ArrayList<MarkerChangedListener>();
	}
	
	private void fireChangeEvent()
	{
		MarkerChangedEvent event = new MarkerChangedEvent(currIndexDouble);
		for (MarkerChangedListener listener : this.listeners)
		{
			listener.MarkerChanged(event);
		}
	}
	
	@Override
	public void MarkerChanged(MarkerChangedEvent e) {
		refreshMarker(e.getValue());
	}	
	
	@Override
	public synchronized void SignalChanged(SignalEvent e) {
		refresh();
	}
	
	public void initialize() {

		SignalWindow = new JPanel(new GridLayout(2,1));
		SpectrumWindow = new JPanel(new GridLayout());
		
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
        
        
        SignalWindow.add(panelLeft);
        SignalWindow.add(panelRight);
        SignalWindow.setMinimumSize(new Dimension(700, 180));
        SpectrumWindow.add(panelSpectrum);
        
        this.add(SignalWindow, BorderLayout.WEST);
        this.add(SpectrumWindow, BorderLayout.CENTER);
        
        paintSignal();
		
		xyDataRight.addSeries(seriesRight);
		xyDataLeft.addSeries(seriesLeft);
		xyDataSpectrum.addSeries(seriesSpectrum); 
		
		valueMarker = new ValueMarker(0);
		valueMarker.setPaint(Color.BLACK);
		plotLeft.addDomainMarker(valueMarker);
		if(stereo) {
			plotRight.addDomainMarker(valueMarker);
		}
		
		ChartMouseClickListener mouseListener = new ChartMouseClickListener();
		panelLeft.addChartMouseListener(mouseListener);
		if(stereo) {
			panelRight.addChartMouseListener(mouseListener);
		}
	}

	class ChartMouseClickListener implements ChartMouseListener {

		@Override
		public void chartMouseClicked(ChartMouseEvent e) {
			if(e.getEntity() instanceof XYItemEntity) {
				XYItemEntity item = (XYItemEntity) e.getEntity();
				XYDataset dataset = item.getDataset();
				double markerValue = dataset.getXValue(item.getSeriesIndex(), item.getItem());
				currIndexDouble = markerValue / signalLength;
				valueMarker.setValue(markerValue);
				fireChangeEvent();
			}
			
		}

		@Override
		public void chartMouseMoved(ChartMouseEvent e) {
			
		}
	}

}
