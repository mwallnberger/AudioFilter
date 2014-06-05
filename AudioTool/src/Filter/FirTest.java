package Filter;

import java.text.DecimalFormat;

public class FirTest {

	public static void main(String[] args) {
		//new HPFilter(null);
		
		//new TPFilter(null);
		BSFilter filter = new BSFilter(500,500,1500,5000);

		double FIRkoeff[] = filter.getFIRkoeff();
		
		for (int x = 0; x < FIRkoeff.length; x++)
		{
			System.out.println(new DecimalFormat("#.########").format(FIRkoeff[x]));
		}
	}
	

}
