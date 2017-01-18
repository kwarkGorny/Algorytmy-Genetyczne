package evolution;
import java.util.ArrayList;
import java.util.List;
import GUI.MainFrame;


//Autorzy:
//Kasia Kosiek i Adam Szczepanski
public class AlgorithmOneOfFiveSucess
{

	private List<Person> generation;
	public static  List<Double> meanFitnessFunction;
	
	private int numberOfPopulation;
	
	private double mutation;
	
	private double numberOfSucess;

	private Person bestPerson;
	
	public AlgorithmOneOfFiveSucess(int numberOfPopulation, double sigma){
		AlgorithmOneOfFiveSucess.meanFitnessFunction=new ArrayList<Double>(numberOfPopulation);
		this.numberOfPopulation=numberOfPopulation;
		
		generation= new ArrayList<Person>(numberOfPopulation);
		mutation= sigma;
		numberOfSucess=0;
		
		
	}
	private void creatGeneration(int numberOfPopulation,double a,double b,double noiseA,double noiseB){
		generation.clear();
		double an=a-noiseA;
		double bn=a-noiseB;
		for(int i=0;i<numberOfPopulation;i++)
			generation.add(new Person(an+Math.random()*2*noiseA,bn+Math.random()*2*noiseB));
		bestPerson= generation.get(0);//zeby nie był pusty i było do czego przyrownywac
	}
	
	private void changeMutation(){
		if(numberOfSucess>numberOfPopulation/5) {
			mutation*=0.82;
			numberOfSucess=0;
		}
		else if(numberOfSucess<numberOfPopulation/5){
			mutation *=1.22;
			numberOfSucess=0;
		}
		
	}
	private Person createChild(Person parent){
		Person child = new Person (parent.a+mutation*RandomNumbers.getGaussian(0, 1),parent.b+mutation*RandomNumbers.getGaussian(0, 1));
		if(child.f > parent.f){
			numberOfSucess++;
			return child;
		}
		else return parent;
	}
	private void findBestPerson(List<Person> generation){
		generation.forEach(p->{if(bestPerson.f<p.f)bestPerson=p;});
	}
	private void NextGeneration(){
		List<Person> nextGeneration= new ArrayList<Person>(numberOfPopulation);
		
		generation.forEach(p->{ nextGeneration.add(createChild(p));});
		changeMutation();
		findBestPerson(nextGeneration);
		generation.removeAll(generation);
		generation.addAll(nextGeneration);
	}
	private double meanFittnessFunction(){
		double mean=0;
		double i=0;
		for(Person p: generation){mean+=p.f;i++;}
		//System.out.println(mean/=i);
		return mean/=i;
		
	}
	public Person startAlgorithm(int numberOfSteps, double acceptableFitnessFunction,double a,double b,double noiseA,double noiseB ){
		
		int currentStep=0;;//double trzeba tak przyrownywac
		creatGeneration(numberOfPopulation, a, b, noiseA, noiseB);
		//System.out.println(a+" "+b+" ");
	
		while(currentStep<numberOfSteps){
			NextGeneration();
			currentStep++;
			MainFrame.textActualGeneration.setText(Integer.toString(currentStep));
			meanFitnessFunction.add(meanFittnessFunction());
			
		}
		
		//System.out.println("Koniec");
		return bestPerson;

	}
	/*
	private void print(List<Person> generation){
		System.out.println("Witam");
		generation.forEach(p->{System.out.println("f: "+p.f);});
		
	}*/
}
