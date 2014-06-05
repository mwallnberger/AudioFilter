package GUI.elements;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;



public class ChartPan extends JPanel {
	byte[] data;
	private XYSeries series;
	XYSeriesCollection xyDataset;
	XYPlot plot;

	
	public ChartPan() {
		this.setBackground(Color.BLUE);
		this.setLayout(new GridBagLayout());
		
		xyDataset = new XYSeriesCollection();

		series = new XYSeries("Play");
		series.add(1.0, 5.0);
		series.add(2.0, 7.0);
		series.add(3.0, 6.0);
		series.add(4.0, 8.0);
		series.add(5.0, 4.0);
		series.add(6.0, 4.0);
		series.add(7.0, 2.0);
		series.add(8.0, 1.0);
	}
	
	public ChartPanel generate() {
		
		xyDataset.addSeries(series);
		
		JFreeChart chart = ChartFactory.createXYLineChart("","","",xyDataset,PlotOrientation.VERTICAL,false,false,false);
		plot = chart.getXYPlot();
		
		XYItemRenderer test = plot.getRenderer();
        
        test.setSeriesPaint(0, Color.BLUE);
		test.setSeriesPaint(1, Color.BLUE);
		
        ChartPanel chartPanel = new ChartPanel(chart);
		
        chartPanel.setOpaque(false);
        chartPanel.setPreferredSize(null);
        this.setOpaque(false);
        
        return chartPanel;
	}
}
