package points;

//Autorzy:
//Kasia Kosiek i Adam Szczepanski
public class Point {
	public final double x;
	public final double y;
    public final Square [][] matrixOfUncertainity; //macierz niepewności
	private Statistics stats; 
	public Point(double x, double y, Statistics stats){
		this.stats = stats;
		this.x = x;
		this.y = y;
		matrixOfUncertainity = createMatrixOfUncertaintity();
	}

    //mam przedział niepewności, dzielę go na length przedziałów
	private double [] createVectorOfSquareCoordinates(double coordinate, int length){
		double [] vector = new double [length + 1];
		vector[0] = -stats.hist.getStartXValue(0, 0);
		for (int i = 1; i < length + 1; i++){
			if (stats.hist.getEndXValue(0, i) < 0.5)
				vector[i] = -stats.hist.getEndXValue(0, i) + coordinate;
			else
				vector[i] = stats.hist.getEndXValue(0, i) + coordinate;
		}
		return vector;
	}

    //mnożę wektor i przypisuję kwadratowe pola, no tak jak w macierzy	
	public Square [][] createMatrixOfUncertaintity(){
		double [] xVector = createVectorOfSquareCoordinates(x, stats.getLength());
		double [] yVector = createVectorOfSquareCoordinates(y, stats.getLength());
		
		Square [][] squares = new Square[stats.getLength()][stats.getLength()];
		
		for (int i = 1; i < stats.getLength() + 1; i++)
			for (int j = 1; j < stats.getLength() + 1; j++)
				squares[i-1][j-1] = new Square(xVector[i-1], xVector[i], yVector[j-1], yVector[j]);
		
		return squares;
	}
	
	
	public void print(){
		System.out.println("x = " + x + ", y = " + y);
		Test.print(matrixOfUncertainity);
	}
}
