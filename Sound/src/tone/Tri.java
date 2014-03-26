package tone;

public class Tri extends Tone
{
	int frequency;
	public Tri(int frequency)
	{
		super("Dreieck");
		this.frequency=frequency;
		
	}
	public double getValue(double time)
	{
		return Math.signum(Math.cos(2*Math.PI*this.frequency*time))*(frequency*time - Math.PI*Math.floor(frequency*time/Math.PI + 1/2));
	}
}
