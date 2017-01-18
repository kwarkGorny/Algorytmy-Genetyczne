package evolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import GUI.MainFrame;


// Autorzy:
//Kasia Kosiek i Adam Szczepanski
//
public class AlgorithmMuLambda {

	private Random generator ;
	
	private List<Person> generation;
	public static List<Double> meanFitnessFunction;
	
	private Person bestPerson;
	
	private int numberOfPopulation;
	private int  numberOfChildren;
	private double tauA;
	private double tauAPrim;
	private double tauB;
	private double tauBPrim;
	private double randomNumberForAll;
	private double  randomNumberForGenA;
	private double  randomNumberForGenB;
	public AlgorithmMuLambda (int numberOfPopulation,int numberOfChildren,double K ){
		generator= new Random();
		this.numberOfPopulation=numberOfPopulation;
		this.numberOfChildren=numberOfChildren;		
		AlgorithmMuLambda.meanFitnessFunction=new ArrayList<Double>(numberOfPopulation);
		tauA=calculateTau(K,1);
		tauAPrim=calculateTauPrim(K,1);
		tauB=calculateTau(K,2);
		tauBPrim=calculateTauPrim(K,2);
		randomNumberForGenA=0;
		randomNumberForGenB=0;
		randomNumberForAll = 0;

		generation= new ArrayList<Person>(numberOfPopulation);
		bestPerson= new Person(0,0);
	}
	private void creatGeneration(int numberOfPopulation,double a,double b,double noiseA,double noiseB){
		generation.clear();
		double an=a-noiseA;
		double bn=b-noiseB;
	
		for(int i=0;i<numberOfPopulation;i++){//losowaly sie wszystkie takie same
			generation.add(new Person(an+RandomNumbers.getGaussian(0, 1)+generator.nextDouble()*2*noiseA,bn+generator.nextDouble()*2*noiseB+RandomNumbers.getGaussian(0, 1)));
		}
		
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
		double o=generator.nextDouble();

		p1.a =person1.a*o+(1-o)*person2.a;
		p1.b =person1.b*o+(1-o)*person2.b;
		p2.a =person2.a*o+(1-o)*p1.a;
		p2.b =person2.b*o+(1-o)*p1.b;
	
		p1.sigmaA =person1.sigmaA*o+(1-o)*person2.sigmaA;
		p1.sigmaB =person1.sigmaB*o+(1-o)*person2.sigmaB;
		p2.sigmaA =person2.sigmaA*o+(1-o)*person1.sigmaA;
		p2.sigmaB =person2.sigmaB*o+(1-o)*person1.sigmaB;
		kids.add(p1);
		kids.add(p2);
		return kids;
	}
	private void mutation(Person person){//mutacja
		//zmieniamy sigme
		person.sigmaA=person.sigmaA*Math.exp(tauAPrim*randomNumberForAll+tauA*randomNumberForGenA);
		person.sigmaB=person.sigmaB*Math.exp(tauBPrim*randomNumberForAll+tauB*randomNumberForGenB);
		//zmieniamy a i b
		person.a+=person.sigmaA*randomNumberForGenA;
		person.b+=person.sigmaB*randomNumberForGenB;
	}
	private void mutateAll(List<Person> generation){
		randomNumberForAll = RandomNumbers.getGaussian(0, 1);
		randomNumberForGenA=RandomNumbers.getGaussian(0, 1);
		randomNumberForGenB=RandomNumbers.getGaussian(0, 1);
		generation.forEach(c->mutation(c));
	}
	public void nextGeneration(){//tworzenie nowego pokolenia
		ArrayList<Person>children = new ArrayList<Person>();
		int iter=0;
		while(iter<(numberOfChildren/2+1)){//losujemy pary rodzicow z ktorych uzyskujemy dwoje dzieci i dodajemy je do listy dzieci (reprodukcja)
			children.addAll(crossing(generation.get(generator.nextInt(numberOfPopulation)),generation.get(generator.nextInt(numberOfPopulation))));//(krzyrzowanie)
			iter++;
		}
		mutateAll(children);//mutujemy dzieci xD (mutacja)
		
		double f;
		int index=0;
		int size=children.size();
		while(size>numberOfPopulation){//przelatujemy po tablicy nastepnej generacji i usuwamy najgorszego az nie zostanie tylko numberOfPopulation z nich
			f=children.get(0).f;
			index=0;
			for(int i=0;i<size;i++){
				if(f-children.get(i).f<0.000000000001){
					index=i;
					f=children.get(i).f;
				}
			}
			children.remove(index);
			size--;
		}
		children.forEach(p->{if(bestPerson.f<p.f)bestPerson=p;});
		generation.addAll(children);
	}
	private double meanFittnessFunction(){
		double mean=0;
		int i=0;
		for(Person p: generation){mean+=p.f;i++;}
		return mean/=i;
		
	}
	public Person startAlgorithm(int numberOfSteps, double acceptableFitnessFunction,double a,double b,double noiseA,double noiseB ){
		int currentStep=0;
//double trzeba tak przyrownywac
		creatGeneration(numberOfPopulation, a, b, noiseA, noiseB);
		while(currentStep<numberOfSteps){
			nextGeneration();
			currentStep++;
			MainFrame.textActualGeneration.setText(Integer.toString(currentStep));
			meanFitnessFunction.add(meanFittnessFunction());
		}
		//System.out.println("Koniec");
		return bestPerson;
	}
}
