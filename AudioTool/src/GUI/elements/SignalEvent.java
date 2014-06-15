package GUI.elements;

import Common.Signal;

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
