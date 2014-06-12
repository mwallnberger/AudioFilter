package IO;

import java.io.File;
import java.io.IOException;

import Common.GeneralException;
import Common.Signal;

public class TestIO
{

	public static void main(String[] args)
	{
		try
		{
			//byte[] array = IOManager.convertToBytes(IOManager.importFile(new File("C:/test.wav")));
			File file2 = new File("C:/Users/Martin/test2.wav");
			try
			{
				file2.createNewFile();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Signal sig = IOManager.importFile(new File("C:/test.wav"));
			IOManager.exportFile(file2,sig);
			System.out.print("");
		}
		catch (GeneralException e)
		{
			e.printStackTrace();
		}
	}

}
