import GUI.GUI;


public class Main {
	static GUI fenster;

	public static void main(String[] args) 
	{
		  fenster = new GUI();
			fenster.diagramm.gen();
			fenster.pack();
		
	}

	public static void convertToFraction(double x, int maxDenom) {
		double alphabuffer = 0;
		double alphabufferReverse = 0;
		int alpha;
		int numer_1 = 1;
		int numer_2 = 0;
		int numer;
		int denom_1 = 0;
		int denom_2 = 1;
		int denom;

		alpha = (int) Math.floor(x);

		numer = alpha * numer_1 + numer_2;
		numer_2 = numer_1;
		numer_1 = numer;

		denom = alpha * denom_1 + denom_2;
		denom_2 = denom_1;
		denom_1 = denom;

		if ((double) numer / (double) denom != x) {
			alphabuffer = x - alpha;
			alphabufferReverse = 1.0 / alphabuffer;

			alpha = (int) Math.floor(alphabufferReverse);

			numer = alpha * numer_1 + numer_2;
			numer_2 = numer_1;
			numer_1 = numer;

			denom = alpha * denom_1 + denom_2;
			denom_2 = denom_1;
			denom_1 = denom;

			alphabufferReverse = 1.0 / alphabuffer;

			while (denom < maxDenom && (double) numer / (double) denom != x) {
				alphabuffer = alphabufferReverse
						- alpha;
				alphabufferReverse = 1.0 / alphabuffer;
				alpha = (int) Math.floor(alphabufferReverse);

				numer = alpha * numer_1 + numer_2;
				numer_2 = numer_1;
				numer_1 = numer;

				denom = alpha * denom_1 + denom_2;
				denom_2 = denom_1;
				denom_1 = denom;
			}

			if ((double) numer / (double) denom != x) {
				numer = numer_2;
				denom = denom_2;
			}
			System.out.print(numer+"/"+denom);
			
		} else {
			System.out.print(numer+"/"+denom);
		}

	}
}
/*
 * } public static void plat(int[] test, int n) { int lenght=1; int
 * lenght_old=0; int index=0; int index_old=0;
 * 
 * int x = 1; while(x < n) { while( x < n && test[x-1] == test[x]) { lenght++;
 * x++; } if(lenght>lenght_old) { index_old = index; lenght_old =lenght; }
 * lenght=1; index=x; x++; } System.out.print("Index: " +
 * index_old+" Lenght:"+lenght_old); }
 */

