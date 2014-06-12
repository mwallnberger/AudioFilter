package Common;

public class Argument
{
	float value;
	float step;
	float min;
	float max;
	String name;
	
	public Argument(float min, float max, String name)
	{
		super();
		this.min = min;
		this.max = max;
		this.name = name;
		this.value = 0; //Default
	}
	public float getValue()
	{
		return value;
	}
	public void setValue(float value)
	{
		this.value = value;
	}
	public float getMin()
	{
		return min;
	}
	public void setMin(float min)
	{
		this.min = min;
	}
	public float getMax()
	{
		return max;
	}
	public void setMax(float max)
	{
		this.max = max;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
}
