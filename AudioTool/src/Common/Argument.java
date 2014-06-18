package Common;

public class Argument
{
	float value;
	float step;
	float min;
	float max;
	String name;
	String einheit;
	
	public Argument(float min, float max, float initValue, String name, String einheit)
	{
		this.min = min;
		this.max = max;
		if(initValue < min) {
			this.value = min;
		}
		else {
			if(initValue > max) {
				this.value = max;
			}
			else {
				this.value = initValue;
			}
			
		}
		this.name = name;
		this.einheit = einheit;
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
	public String getEinheit() {
		return einheit;
	}
	
	public String toString() {
		return name + " [" + einheit + "]";
	}
	
}
