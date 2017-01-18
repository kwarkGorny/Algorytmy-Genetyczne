package evolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import GUI.MainFrame;

//Autorzy:
//Kasia Kosiek i Adam Szczepanski
public class AlgorithmMuPlusLambda {
	Random generator;
	
	private List<Person> generation;
	public static List<Double> meanFitnessFunction;
	
	private Person bestPerson;
	
	private int numberOfPopulation;
	private int numberOfChildren;
	
	private double tauA;
	private double tauAPrim;
	private double tauB;
	private double tauBPrim;
	
	private int  getMax(List<Person> generation){
		double f=generation.get(0).f;
		int i=0;
		int index=0;
		for(Person p:generation){
			if(f<p.f){ f=p.f;  index=i;}
			i++;
		}
		return index;
	}
	
	
	public AlgorithmMuPlusLambda (int numberOfPopulation,int numberOfChildren,double K ){
		this.numberOfPopulation=numberOfPopulation;
		this.numberOfChildren=numberOfChildren;	
		generator= new Random();
		AlgorithmMuPlusLambda.meanFitnessFunction=new ArrayList<Double>(numberOfPopulation);
		
		tauA=calculateTau(K,1);
		tauAPrim=calculateTauPrim(K,1);
		tauB=calculateTau(K,2);
		tauBPrim=calculateTauPrim(K,2);
		
		generation= new ArrayList<Person>(numberOfPopulation);	
		bestPerson= new Person(0,0);
	}
	private void creatGeneration(int numberOfPopulation,double a,double b,double noiseA,double noiseB){
		generation.clear();
		double an=a-noiseA;
		double bn=b-noiseB;
		double c=0.2;
		for(int i=0;i<numberOfPopulation;i++){//losowaly sie wszystkie takie same
			generation.add(new Person(an+generator.nextInt(50)/50.0+Math.random()*2*noiseA,bn+Math.random()*2*noiseB+generator.nextInt(50)/50.0));
		c+=c;
		}
		//print(generation);
		bestPerson= generation.get(0);//zeby nie był pusty i było do czego przyrownywac
	}
	private double calculateTau(double K,int n){
		return K/Math.sqrt(2*n);
	}
	private double calculateTauPrim(double K,int n){
		return K/Math.sqrt(2*Math.sqrt(n));
	}
	private List<Person> crossing(Person person1,Person person2){//krzyzowanie
		List<Person> kids= new ArrayList<Person>(2);
		Person p1= new Person(0,0);
		Person p2= new Person(0,0);
		

		p1.a =person1.a*generator.nextDouble()+(1-Math.random())*person2.a;
		p1.b =person1.b*generator.nextDouble()+(1-Math.random())*person2.b;
		p2.a =person2.a*generator.nextDouble()+(1-Math.random())*person1.a;
		p2.b =person2.b*generator.nextDouble()+(1-Math.random())*person1.b;
	
		p1.sigmaA =person1.sigmaA*generator.nextDouble()+(1-Math.random())*person2.sigmaA;
		p1.sigmaB =person1.sigmaB*generator.nextDouble()+(1-Math.random())*person2.sigmaB;
	
		p2.sigmaA =person2.sigmaA*generator.nextDouble()+(1-Math.random())*person1.sigmaA;
		p2.sigmaB =person2.sigmaB*generator.nextDouble()+(1-Math.random())*person1.sigmaB;
		
		kids.add(p1);
		kids.add(p2);
		return kids;
	}
	private void mutation(Person person, double randomForAll){//mutacja
		//zmieniamy sigme
		person.sigmaA=person.sigmaA*Math.exp(tauAPrim*randomForAll+tauA*RandomNumbers.getGaussian(0, 1));
		person.sigmaB=person.sigmaB*Math.exp(tauBPrim*randomForAll+tauB*RandomNumbers.getGaussian(0, 1));
		
		//zmieniamy a i b
		person.a+=person.sigmaA*RandomNumbers.getGaussian(0, 1);
		person.b+=person.sigmaB*RandomNumbers.getGaussian(0, 1);
	}
	private void mutateAll(List<Person> generation){
		generation.forEach(c->mutation(c,RandomNumbers.getGaussian(0, 1)));
	}
	private void nextGeneration(){
		ArrayList<Person>children= new ArrayList<Person>();
		for(int i=0;i<numberOfChildren;i++)//losujemy pary rodzicow z ktorych uzyskujemy dwoje dzieci i dodajemy je do listy dzieci (reprodukcja)
			children.addAll(crossing(generation.get(generator.nextInt(numberOfPopulation)),generation.get(generator.nextInt(numberOfPopulation))));//(krzyrzowanie)
		
		mutateAll(children);
		 //czyscimy nastepne pokolenie
		
		children.addAll(generation);//dodajemy stare i dzieci
		//System.out.println(children.size()+"   "+numberOfPopulation);
			
		children.forEach(p->{if(bestPerson.f<p.f)bestPerson=p;});
		generation.removeAll(generation);
		
		for(int i=0;i<numberOfPopulation;i++){
			int a= getMax(children);
			generation.add(children.get(a));
			children.remove(a);
		}
		//print(generation);

	}
	private double meanFittnessFunction(){
		double mean=0;
		double i=0;
		for(Person p: generation){mean+=p.f;i++;}
		return mean/=i;
	}
	public Person startAlgorithm(int numberOfSteps, double acceptableFitnessFunction ,double a,double b,double noiseA,double noiseB){
		int currentStep=0;
	//double trzeba tak przyrownywac
		creatGeneration(numberOfPopulation, a, b, noiseA, noiseB);
		while(currentStep<numberOfSteps){
			nextGeneration();
			meanFitnessFunction.add(meanFittnessFunction());
			MainFrame.textActualGeneration.setText(Integer.toString(currentStep));
			currentStep++;
		}
		
		return bestPerson;
	}
	/*private void print(List<Person> generation){
		System.out.println("Witam");
		generation.forEach(p->{System.out.println("f: "+p.f);});
		
	}*/
}
