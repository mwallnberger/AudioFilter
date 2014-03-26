package tools;
import java.awt.*;
import java.io.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.*;



public class MiniTools
{
	public static void setSizeAndPosition(JFrame zuzentrieren)
	{
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		zuzentrieren.setSize((int) dim.getWidth() * 9 / 10, (int) dim.getHeight() *9/ 10);
		//Dimension frameDim = this.getSize();
		zuzentrieren.setMinimumSize(new Dimension((int) dim.getWidth() * 4 / 10, (int) dim.getHeight() *14/30));
		
		

		int x = (int)(dim.getWidth() - zuzentrieren.getWidth());
		int y =  (int)(dim.getHeight() - zuzentrieren.getHeight());
		x = x / 2;
		y = y / 2;
		zuzentrieren.setLocation(x,y);
	}
	public static void centerwindow(JFrame zuzentrieren)
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int)(dim.getWidth() - zuzentrieren.getWidth());
		int y =  (int)(dim.getHeight() - zuzentrieren.getHeight());
		x = x / 2;
		y = y / 2;
		zuzentrieren.setLocation(x,y);
	}
	public static String getcurrentversion()
	{
		try
		{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("version.cfg")));
		String currentversion = br.readLine();
		return currentversion;
		}
		catch(Exception e)
		{
			return "";	
		}
	}
	public static void splashprogressbar(JProgressBar progressbar,String text, int wait )
	{
		try 
		{
			progressbar.setString(text);
			Thread.sleep(wait);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	public static void addComponent(Container container, Component component,
			int gridx, int gridy, int gridwith, int gridheight, double weightx,
			double weighty, int anchor, int fill, Insets insets, int  ipadx, int ipady) 
	{
		GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwith,
				gridheight, weightx, weighty, anchor, fill, insets, ipadx, ipady);
		container.add(component, gbc);
	}
	
	public static int zufallswert(int von, int bis)
	{
		int i = (int) (Math.random()*bis+von);
		return i;
	}
	
	 public static void gzipFile(String from, String to) throws IOException 
	 {
		    FileInputStream in = new FileInputStream(from);
		    GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(to));
		    byte[] buffer = new byte[4096];
		    int bytesRead;
		    while ((bytesRead = in.read(buffer)) != -1)
		      out.write(buffer, 0, bytesRead);
		    in.close();
		    out.close();
	 }
	 /**
	     * Wraps a Swing JComponent in a background image. The vertical and horizontal
	     * alignment of background image can be specified using the alignment
	     * contants from JLabel.
	     *
	     * @param component - to wrap in the a background image
	     * @param backgroundIcon - the background image (Icon)
	     * @param verticalAlignment - vertical alignment. See contants in JLabel.
	     * @param horizontalAlignment - horizontal alignment. See contants in JLabel.
	     * @return the wrapping JPanel
	     */
	    public static JPanel wrapInBackgroundImage(JComponent component,
	            Icon backgroundIcon,
	            int verticalAlignment,
	            int horizontalAlignment) {
	        
	        // make the passed in swing component transparent
	        component.setOpaque(false);
	        
	        // create wrapper JPanel
	        JPanel backgroundPanel = new JPanel(new GridBagLayout());
	        backgroundPanel.setOpaque(false);
	        
	        // add the passed in swing component first to ensure that it is in front
	        GridBagConstraints gbc = new GridBagConstraints();
	            gbc.gridx = 0;
	            gbc.gridy = 0;
	            gbc.weightx = 1.0;
	            gbc.weighty = 1.0;
	            gbc.fill = GridBagConstraints.BOTH;
	            gbc.anchor = GridBagConstraints.NORTHWEST;
	        backgroundPanel.add(component, gbc);
	        
	        // create a label to paint the background image
	        JLabel backgroundImage = new JLabel(backgroundIcon);
	        
	        // set minimum and preferred sizes so that the size of the image
	        // does not affect the layout size
	        //backgroundImage.setPreferredSize(new Dimension(1,1));
	        //backgroundImage.setMinimumSize(new Dimension(1,1));
	        
	        // align the image as specified.
	        backgroundImage.setVerticalAlignment(verticalAlignment);
	        backgroundImage.setHorizontalAlignment(horizontalAlignment);
	        
	        // add the background label
	        backgroundPanel.add(backgroundImage, gbc);
	        
	        // return the wrapper
	        return backgroundPanel;
	    }
		  public static void zipDirectory(String dir, String zipfile)throws IOException, IllegalArgumentException 
		  {
			  File d = new File(dir);
			  if (!d.isDirectory())
			  {
				  throw new IllegalArgumentException("Not a directory:  "  + dir);
			  }
			  String[] entries = d.list();
			  byte[] buffer = new byte[4096]; // Create a buffer for copying
			  int bytesRead;
			  ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));

			  for (int i = 0; i < entries.length; i++) 
			  {
				  File f = new File(d, entries[i]);
				  if (f.isDirectory())
				  {
					  continue;//Ignore directory
				  }
				  FileInputStream in = new FileInputStream(f); // Stream to read file
				  ZipEntry entry = new ZipEntry(f.getPath()); // Make a ZipEntry
				  out.putNextEntry(entry); // Store entry
				  while ((bytesRead = in.read(buffer)) != -1)
				  {
					  out.write(buffer, 0, bytesRead);
				  }
				  in.close(); 
			  }
			  out.close();
		  }
		  public static void deleteTree( File path )
		  {
		    for ( File file : path.listFiles() )
		    {
		      if ( file.isDirectory() )
		        deleteTree( file );
		      file.delete();
		    }
		    path.delete();
		  }
		  
		   public static void browse(String path) 
		   {
			   URL homepage;
				try {
					homepage = new URL(path);
					URI uri = homepage.toURI();
					 if (Desktop.isDesktopSupported()) 
				        {
				           Desktop desktop = Desktop.getDesktop();
				           try 
				           {
				              desktop.browse(uri);
				           }
				           catch (IOException e) 
				           {
				              JOptionPane.showMessageDialog(null,
				                            "Failed to launch the link, " +
				                            "your computer is likely misconfigured.",
				                            "Cannot Launch Link",JOptionPane.WARNING_MESSAGE);
				           }
				        } else 
				        {
				            JOptionPane.showMessageDialog(null,
				                    "Java is not able to launch links on your computer.",
				                    "Cannot Launch Link",JOptionPane.WARNING_MESSAGE);
				        }
					
				} catch (MalformedURLException e1) 
				{
					// TODO Auto-generated catch block
					 JOptionPane.showMessageDialog(null,
	                            "Failed to launch the link",
	                            "Cannot Launch Link",JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				} catch (URISyntaxException e1) 
				{
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null,
                            "Failed to launch the link",
                            "Cannot Launch Link",JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				}
		       
		    }
}
