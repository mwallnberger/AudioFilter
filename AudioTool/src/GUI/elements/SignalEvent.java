package GUI.elements;

import java.io.File;

import Common.GeneralException;
import Common.Signal;
import IO.IOManager;

public class SignalEvent
{
	private Signal source;
	
	public SignalEvent(Signal source)
	{
		this.source = source;
	}

	public Signal getSource()
	{
		return source;
	}
	
	
}
