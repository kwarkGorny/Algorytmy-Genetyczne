package evolution;

import points.Line;

//Autorzy:
//Kasia Kosiek i Adam Szczepanski
public class Simulation {

	
	private int numberOfSteps;
	private double acceptableFitnessFunction;
	private int numberOfPopulation=0;
	private int numberOfChildren=0;
	private double K=0;
	private double sigma=0;
	
	
	// jesli chcemy uzyc Algorytmu(u+y) i (u,y)
	public Simulation(Line line,int numberOfSteps, int numberOfPopulation,int numberOfChildren,double K,double sigma){
		this.numberOfPopulation=numberOfPopulation;
		this.numberOfChildren=numberOfChildren;
		this.numberOfSteps=numberOfSteps;
		this.K=K;
		this.sigma=sigma;
		Person.line=line; //inicjalizacja linii
	}
	
	
	public Person startAlgorithm( Algorithm whichAlgorithm,double a,double b,double noiseA,double noiseB){
		Person person=null;
		switch ( whichAlgorithm){
			case OnePlusOne:
				AlgorithmOneOfFiveSucess algorithm1 = new AlgorithmOneOfFiveSucess(numberOfPopulation,sigma);
				person = algorithm1.startAlgorithm(numberOfSteps, acceptableFitnessFunction, a, b, noiseA, noiseB);
				break;
			case MuPlusLambda:
				AlgorithmMuPlusLambda algorithm2 = new AlgorithmMuPlusLambda(numberOfPopulation,numberOfChildren,K);
				person = algorithm2.startAlgorithm(numberOfSteps, acceptableFitnessFunction, a, b, noiseA, noiseB);
				break;
			case MuLambda:
				AlgorithmMuLambda  algorithm3 = new AlgorithmMuLambda(numberOfPopulation,numberOfChildren,K);
				person = algorithm3.startAlgorithm(numberOfSteps, acceptableFitnessFunction, a, b, noiseA, noiseB);
				break;
			
		}
		return person;
	}
}
