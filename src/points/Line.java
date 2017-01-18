package points;

import java.util.ArrayList;



//Autorzy:
//Kasia Kosiek i Adam Szczepanski
public class Line{// przechowuje punkty 
	public final double a;//wspolczynnik z ktorych generujemy prosta
	public final double b;//
	public final int numberOfPoints;//
	public final ArrayList<Point> measurements;// wygenerowana prosta
	private final Statistics stats;// rozklad szumy statystyka
	
	public Line(double a, double b,int numberOfMeasure){//dodalem zeby mozna bylo zmieniac ilosc punktow
		super();
		this.a = a;
		this.b = b;
		this.numberOfPoints=numberOfMeasure;
		measurements = new ArrayList<>(); 
		stats = new Statistics();
		createMeasurements(numberOfMeasure);
	}

	public void createMeasurements(int numberOfPoints){
		for (int i = 0; i < numberOfPoints; i++){
			double x = i + stats.getNoise();
			double y = i * a + b + stats.getNoise();
			measurements.add(new Point(x, y, stats));
		}
	}
	
	public double fitnessFunction(double a, double b){
		double result = 0;
		
		for (int k = 0; k < measurements.size(); k++)
			for(int i = 0; i < stats.getLength(); i++)
				for(int j = 0; j < stats.getLength(); j++)
					if(measurements.get(k).matrixOfUncertainity[i][j].containsLine(a, b))
						result += stats.factorMatrix[i][j];
		
		//System.out.println(result);
		return result;
	}
	/*
	private JFreeChart plotGoalFunc(){
		XYSeries series = new XYSeries("Goal function");
		for (int i = 0; i < 50; i++)
			series.add(0.1 + i*0.1, fitnessFunction(0.1 + i*0.1, 20));
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		JFreeChart chart = ChartFactory.createXYAreaChart("Funkcja celu", "x", "y", dataset);
		return chart;
	}
	
	*/
	
	
	
	
	
	
	

	
	
	
}