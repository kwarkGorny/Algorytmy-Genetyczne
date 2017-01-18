package evolution;
//zrodlo:
// https://en.wikipedia.org/wiki/Marsaglia_polar_method
public class RandomNumbers {
	
	private static double spare;
	private static boolean isSpareReady = false;
//losujemy z rzokladu gassua o sredniej mean i wariancji stdDev 
	public static synchronized double getGaussian(double mean, double stdDev) {
	    if (isSpareReady) {
	        isSpareReady = false;
	        return spare * stdDev + mean;
	    } else {
	        double u, v, s;
	        do {
	            u = Math.random() * 2 - 1;
	            v = Math.random() * 2 - 1;
	            s = u * u + v * v;
	        } while (s >= 1 || s == 0);
	        double mul = Math.sqrt(-2.0 * Math.log(s) / s);
	        spare = v * mul;
	        isSpareReady = true;
	        return mean + stdDev * u * mul;
	    }
	}    
}
