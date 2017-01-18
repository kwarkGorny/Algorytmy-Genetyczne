package points;
import java.util.ArrayList;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

//Autorzy:
//Kasia Kosiek i Adam Szczepanski
public class Statistics{
	private final double middle;
	public final int bins = 20;
	public final HistogramDataset hist;
	private int length;
	public final double[][] factorMatrix;
	
	public Statistics(){
		middle = 0.5; //tu się znajduje pomiar
		hist = createHistogram(getExampleValues());
		length = 0;
		factorMatrix = createFactorMatrix();
	}
	
    //lambda, k - współczynniki rozkładu
	public double getNumberFromWeibullDist(){
		double lambda = 1.0;
		double k = 2;
		return lambda * (Math.pow(-Math.log(1 - Math.random()), 1 / k));
	}
	
	public double getNoise(){
		double noise = getNumberFromWeibullDist();
		if (noise < middle)
			return noise;
		else
			return -noise;
	}
	
	public double [] getExampleValues(){
		double [] s = new double[1000000];
		for (int i = 0; i < 1000000; i++)
			s[i] = getNumberFromWeibullDist();
		return s;
	}
	
	public HistogramDataset createHistogram(double [] values){
		HistogramDataset hist = new HistogramDataset();
		hist.setType(HistogramType.RELATIVE_FREQUENCY);
		hist.addSeries("Histogram", values, bins);
		return hist;
	} 

    //tworzę wektory które mnożę potem	
	private ArrayList<Double> getFactorValues(HistogramDataset hist){
		double binValue = hist.getY(0, 0).doubleValue();
		int i = 0;
		ArrayList<Double> factorVector = new ArrayList<>();
		
		while (binValue > 0.005){
			i++;
			factorVector.add(binValue);
			binValue = hist.getY(0, i).doubleValue();
		}
		length = factorVector.size();
		return factorVector;
	}

	
    //każdemu przedziałowi niepewności przyporządkowuję macierz wag - to ta macierz
	public double [][] createFactorMatrix(){
		double [][] factorMatrix;
		ArrayList<Double> factorVector = getFactorValues(hist);
		factorMatrix = new double[factorVector.size()][factorVector.size()];
		
		for (int k = 0; k < factorVector.size(); k++)
			for (int j = 0; j < factorVector.size(); j++)
				factorMatrix[k][j] = factorVector.get(k) * factorVector.get(j);
		
		return factorMatrix;
	}
	
	public int getLength(){
		return length;
	}

}
