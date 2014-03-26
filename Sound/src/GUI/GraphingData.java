package GUI;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.xy.*;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.NumberAxis;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.chart.renderer.xy.XYItemRenderer;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;

import tools.MiniTools;


public class GraphingData extends JPanel
{
	byte[] data;
	XYSeries series ;
	private XYSeries series2;
	XYSeriesCollection xyDataset;
	XYPlot plot;

	
	public GraphingData()
	{
		super();
		this.setBackground(Color.BLUE);
		this.setLayout(new GridBagLayout());
		series = new XYSeries("Average Size");
		series2 = new XYSeries("Play");
		series2.add(1000000, -255);
		series2.add(1000001, 255);
	}
	public void gen()
	{
		xyDataset = new XYSeriesCollection(series);
	//	xyDataset.addSeries(series2);
		
		JFreeChart chart = ChartFactory.createXYLineChart("","","",xyDataset,PlotOrientation.VERTICAL,false,false,false);
		plot = chart.getXYPlot();
		chart.setBackgroundPaint(StyleSheet.CORTEX_GREY);
		System.out.println(chart.getAntiAlias());
		NumberAxis numberAxis = (NumberAxis) plot.getDomainAxis();
		plot.getRangeAxis().setTickLabelPaint(StyleSheet.CORTEX_BLUE);
		numberAxis.setTickLabelPaint(StyleSheet.CORTEX_BLUE);
		XYItemRenderer test = plot.getRenderer();
		test.setSeriesPaint(0, StyleSheet.CORTEX_BLUE);
		test.setSeriesPaint(1, StyleSheet.CORTEX_BLUE);
	
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setOpaque(false);
        chartPanel.setPreferredSize(null);
        this.setOpaque(false);
	    MiniTools.addComponent(this, chartPanel, 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,  GridBagConstraints.BOTH, new Insets(0,0,0,0), 0, 0);
	}
	public void addValue(long x, int y)
	{
		series.add(x, y);
	}
	public void clear()
	{
		this.xyDataset.removeSeries(series);
		series.clear();
	}
	public void paint()
	{
		this.xyDataset.addSeries(series);
	}
	public void setData(byte[] data2)
	{
		this.data = data2;
		this.series.clear();
		this.xyDataset.removeSeries(series);
		for(int x=0; x<data2.length; x++)
		{
			this.series.add(x,data2[x]);
		}
		this.xyDataset.addSeries(series);
	}
	public void autojustage()
	{
		plot.getDomainAxis().setAutoRange(true);
		plot.getRangeAxis().setAutoRange(true);
	
	}
}
