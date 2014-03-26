package tools;


 
import java.io.File;
import javax.swing.filechooser.*;
 
public class AudioFileFilter extends FileFilter 
{
 
    public boolean accept(File f)
    {
        if (f.isDirectory())
        {
            return true;
        }
        if(f.toString().endsWith(".wav"))
        {
        	return true;
        }
        return false;
     }
    public String getDescription()
    {
        return "WAV";
    }
}