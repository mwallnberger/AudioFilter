package Common;

public class GeneralException extends Exception
{
	String error;
	public GeneralException(String error)
	{
		this.error = error;	
	}
	
	@Override
	public String toString()
	{
		return this.error;
	}

}
