package IO;

public enum FileType
{
	WAVE("wav"),MP3("mp3");
	
	private String ending;
	
	private FileType(String ending)
	{
		this.ending = ending;
	}

	public String getEnding()
	{
		return ending;
	}
}
