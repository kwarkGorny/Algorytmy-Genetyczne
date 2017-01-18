package points;

//Autorzy:
//Kasia Kosiek i Adam Szczepanski
public class Square{
	private final double x1;
	private final double x2;
	private final double y1;
	private final double y2;
	
	public Square(double x1, double x2, double y1, double y2){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public double y(double a, double b, double x){
		return a*x + b;
	}
	
	public double x(double a, double b, double y){
		return (y-b)/a;
	}

    //sprawdza czy kwadrat o danych współrzędnych przecina prosta a zadanych współczynnikach	
	public boolean containsLine(double a, double b){
		if (y(a, b, x1) > y1 && y(a, b, x1) < y2)
			return true;
		else if (y(a, b, x2) > y1 && y(a, b, x2) < y2)
			return true;
		else if (x(a, b, y1) > x1 && x(a, b, y1) < x2)
			return true;
		else if (x(a, b, y2) > x1 && x(a, b, y2) < x2)
			return true;
		
		return false;
	}
	
	@Override
	public String toString(){
		return "(" + x1 + "," + y2 +")" + "\t(" + x2 + "," + y2 + ") \n (" + x1 + "," + y1 +")" + "\t(" + x2 + "," + y1 + ")\n" ;
	}
}
