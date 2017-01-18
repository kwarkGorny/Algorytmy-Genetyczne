package evolution;

import points.Line;

//Autorzy:
//Kasia Kosiek i Adam Szczepanski
public class Person {
	public double a;
	public double b;
	public double sigmaA;
	public double sigmaB;
	public double f; // mozna dac w kontruktorze
	
	public static Line line;//trzeba reczne zainicjowac patrz klasa Simulation
	
	public Person(double a, double b){
		this.a = a;
		this.b = b;
		this.f=line.fitnessFunction(a, b);
		
	}
	public Person(double a, double b, Line line){
		this.a = a;
		this.b = b;
		Person.line=line;
		this.f=line.fitnessFunction(a, b);
	}
	public Person(double a, double b, double sigmaA, double sigmaB){
		this.a = a;
		this.b = b;
		this.sigmaA = sigmaA;
		this.sigmaB = sigmaB;
		this.f=line.fitnessFunction(a, b);
	}
	
	
	
	
	
	
}
