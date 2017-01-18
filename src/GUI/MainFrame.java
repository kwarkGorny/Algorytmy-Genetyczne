package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import evolution.Algorithm;
import evolution.AlgorithmMuLambda;
import evolution.AlgorithmMuPlusLambda;
import evolution.AlgorithmOneOfFiveSucess;
import evolution.Person;
import evolution.Simulation;
import points.Line;


//Autorzy:
//Kasia Kosiek i Adam Szczepanski

public class MainFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	Line line;
	JFreeChart chartOfFitnessFunction;
	JFreeChart chartOfMeanFitness;
	private JPanel paramsPanel;
	private JPanel linePanel;
	private JPanel chartPanel;
	//private JLabel label;
	private JLabel labelNumberOfPopulation;
	private JLabel labelNumberOfChildren;
	private JLabel labelNumberOfGenerations;
	private JLabel labelSigma;
	private JLabel labelK;
	private JLabel labelChooseAl;
	private JLabel labelPress;
	private JLabel labelActualGeneration;
	private JLabel labelStartingA;
	private JLabel labelStartingB;
	private JLabel labelStartingNoiseA;
	private JLabel labelStartingNoiseB;
	private JLabel labelEndingA;
	private JLabel labelEndingB;
	
	private JTextField textEndingA;
	private JTextField textEndingB;
	private JTextField textStartingA;
	private JTextField textStartingB;
	private JTextField textStartingNoiseA;
	private JTextField textStartingNoiseB;
	private JTextField textPopulation;
	private JTextField textChildren;
	private JTextField textGenerations;
	private JTextField textSigma;
	private JTextField textK;
	public static JTextField textActualGeneration ;
	
	private JLabel labelCreateLine;
	private JLabel labelNumberOfPoints;
	private JLabel labelParamA;
	private JLabel labelParamB;
	private JTextField textNumberOfPoints;
	private JTextField textParamA;
	private JTextField textParamB;
	
	boolean isChartActual=false;
	boolean isAlgorithmWorking;
	double xfirst;
	double xlast;
	
	private JButton createLine;
	private JButton startSimulation;
	private JComboBox<?> algos;
	//private JScrollBar scroll;
	
	XYDataset data;
	XYSeries  series1;
	XYSeries  series2;
	ChartPanel PanelOfFitnessFunction ;
	ChartPanel PanelOfMeanFitness ;
	private void initFrame(){
		this.setVisible(true);//u mnie bez tego nie dziala :(
		this.setLayout(new BorderLayout());
		this.setSize(1200, 800);
		this.setTitle("Witaj w moim Wirtualnym Domu");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private void initParamsPanel(){
		paramsPanel = new JPanel();
		paramsPanel.setLayout(new GridLayout(2, 10));
		
		labelNumberOfPopulation = new JLabel("Liczba Populacja \u00B5:");
		labelNumberOfChildren = new JLabel("Liczba dzieci \u03BB: ");
		labelNumberOfGenerations = new JLabel("Liczba pokoleń t: ");
		labelChooseAl= new JLabel("Wybierz Algorytm: ");
		labelK = new JLabel("Parametr \uD835\uDED5 : ");
		labelSigma = new JLabel("Zasieg mutacji  \u03C3: ");
		labelPress= new JLabel("Wcisnij Przycisk ");
		labelActualGeneration= new JLabel("Obecne Pokolenie:");
		labelEndingA= new JLabel ("Koncowe a:");
		labelEndingB= new JLabel ("Koncowe b:");
		
		textPopulation = new JTextField("10");
		textPopulation.setEditable(true);
		textPopulation.setHorizontalAlignment(JTextField.RIGHT);
		
		textChildren = new JTextField("10");
		textChildren.setEditable(true);
		textChildren.setHorizontalAlignment(JTextField.RIGHT);


		textGenerations = new JTextField("10");
		textGenerations.setEditable(true);
		textGenerations.setHorizontalAlignment(JTextField.RIGHT);
		
		textSigma = new JTextField("10");
		textSigma.setEditable(true);
		textSigma.setHorizontalAlignment(JTextField.RIGHT);
		
		textK = new JTextField("10");
		textK.setEditable(true);
		textK.setHorizontalAlignment(JTextField.RIGHT);
		
		textEndingA = new JTextField("0");
		textEndingA.setEditable(false);
		textEndingA.setHorizontalAlignment(JTextField.RIGHT);
		
		textEndingB = new JTextField("10");
		textEndingB.setEditable(false);
		textEndingB.setHorizontalAlignment(JTextField.RIGHT);
		
		String [] algosNames = {"Algorytm (1 + 1)", "Algorytm (\u00B5 + \u03BB)", "Algorytm (\u00B5, \u03BB)"}; //TO dodaj nazwy algorytmów
		algos = new JComboBox<String>(algosNames);
		
		startSimulation = new JButton("Start");// inicjacja przycisku
		startSimulation.addActionListener(pressButton->startAction());//wcisniecie przycisku
		
		paramsPanel.add(labelNumberOfPopulation);
		paramsPanel.add(labelNumberOfChildren);
		paramsPanel.add(labelNumberOfGenerations);
		paramsPanel.add(labelSigma);
		paramsPanel.add(labelK);
		paramsPanel.add(labelChooseAl);
		paramsPanel.add(labelPress);
		paramsPanel.add(labelActualGeneration);
		paramsPanel.add(labelEndingA);
		paramsPanel.add(labelEndingB);
		
		paramsPanel.add(textPopulation);
		paramsPanel.add(textChildren);
		paramsPanel.add(textGenerations);
		paramsPanel.add(textSigma);	
		paramsPanel.add(textK);
		paramsPanel.add(algos);
		paramsPanel.add(startSimulation);	
		paramsPanel.add(textActualGeneration);
		paramsPanel.add(textEndingA);
		paramsPanel.add(textEndingB);
	}
	private void initLinePanel(){
		linePanel = new JPanel();
		linePanel.setLayout(new GridLayout(2, 8));
		labelNumberOfPoints= new JLabel("Liczba Punktow(nie wiecej niz 100): ");
		labelParamA= new JLabel("Wspolczynnik a: ");
		labelParamB= new JLabel("Wspolczynnik b: ");
		labelCreateLine= new JLabel("Wylosuj prostą: ");
		labelStartingA= new JLabel("Startowe a: ");
		labelStartingB= new JLabel("Startowe b: ");
		labelStartingNoiseA= new JLabel("Szumy startowego a: ");
		labelStartingNoiseB= new JLabel("Szumy startowego b: ");
		
		textStartingA= new JTextField("1");
		textStartingA.setHorizontalAlignment(JTextField.RIGHT);
		textStartingA.setEditable(true);
		
		textStartingB= new JTextField("1");
		textStartingB.setHorizontalAlignment(JTextField.RIGHT);
		textStartingB.setEditable(true);
		
		textStartingNoiseA= new JTextField("1");
		textStartingNoiseA.setHorizontalAlignment(JTextField.RIGHT);
		textStartingNoiseA.setEditable(true);
		
		textStartingNoiseB= new JTextField("1");
		textStartingNoiseB.setHorizontalAlignment(JTextField.RIGHT);
		textStartingNoiseB.setEditable(true);
		
		textNumberOfPoints= new  JTextField("50");
		textNumberOfPoints.setHorizontalAlignment(JTextField.RIGHT);
		textNumberOfPoints.setEditable(true);
		
		textParamA= new  JTextField("1");
		textParamA.setHorizontalAlignment(JTextField.RIGHT);
		textParamA.setEditable(true);
		
		textParamB= new  JTextField("10");
		textParamB.setHorizontalAlignment(JTextField.RIGHT);
		textParamB.setEditable(true);
		
		createLine = new JButton("Narysuj");// inicjacja przycisku
		createLine.addActionListener(pressButton->startLine());//wcisniecie przycisku
		
		linePanel.add(labelNumberOfPoints);
		linePanel.add(labelParamA);
		linePanel.add(labelParamB);
		linePanel.add(labelCreateLine);
		linePanel.add(labelStartingA);
		linePanel.add(labelStartingB);
		linePanel.add(labelStartingNoiseA);
		linePanel.add(labelStartingNoiseB);
		
		linePanel.add(textNumberOfPoints);
		linePanel.add(textParamA);
		linePanel.add(textParamB);
		linePanel.add(createLine);
		linePanel.add(textStartingA);
		linePanel.add(textStartingB);
		linePanel.add(textStartingNoiseA);
		linePanel.add(textStartingNoiseB);
	}
	private void initChartPanle(){
		chartPanel= new JPanel();
		chartPanel.setLayout(new GridLayout(1, 2));
		isAlgorithmWorking=false;
		line=new Line(1,10,1000);
		data = createDataset(line.numberOfPoints,line.a,line.b);//tworzenie danych
		//tworzenie wykresu
		chartOfFitnessFunction = ChartFactory.createXYLineChart("Wykres",
                "x", "y", data, PlotOrientation.VERTICAL, true, true,
                false);
		PanelOfFitnessFunction= new ChartPanel(chartOfFitnessFunction);
		
		chartPanel.add(PanelOfFitnessFunction);
		XYDataset data1 = createNUllDataset();//tworzenie danych
		chartOfMeanFitness = ChartFactory.createXYLineChart("Wykres",
                "numer Generacji", "Funkcja przystosowania", data1, PlotOrientation.VERTICAL, true, true,
                false);
		PanelOfMeanFitness= new ChartPanel(chartOfMeanFitness);
		chartPanel.add(PanelOfMeanFitness);
		
	}
	
	
	public MainFrame(){
		initFrame();
		initParamsPanel();
		initChartPanle();
		initLinePanel();
		
		this.add(chartPanel, BorderLayout.CENTER);
		this.add(linePanel, BorderLayout.NORTH);
		this.add(paramsPanel, BorderLayout.SOUTH);
	}
	public void startLine(){
		if(!isAlgorithmWorking){
		int numberOfPoints;
		double  a;
		double b;
		try{
			numberOfPoints = Integer.parseInt(textNumberOfPoints.getText());
			a = Integer.parseInt(textParamA.getText());
			b = Integer.parseInt(textParamB.getText());
			
			data = createDataset(numberOfPoints,a,b);
			
			((XYPlot)chartOfFitnessFunction.getPlot()).setDataset(data);
			
			chartOfFitnessFunction.fireChartChanged();
			PanelOfFitnessFunction.repaint();
			linePanel.repaint();
			
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(new JFrame(),
				    "Nie wypełniłeś wszystkich pól.",
				    "Illegal Format",
				    JOptionPane.ERROR_MESSAGE);
		}
		
		isChartActual=true;
		}
	}
	public void startAction(){
		if(!isChartActual){ startLine();}
		if(!isAlgorithmWorking){
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
		     @Override
		     public Void doInBackground() {

		    	int numberOfPopulation=0;
		 		int numberOfChildren=0;
		 		int numberOfGenerations=0;
		 		double K=0;
		 		double sigma=0;
		 		double a=0;
		 		double b=0;
		 		double noiseA=0;
		 		double noiseB=0;
		 		Person result;
		 		int chosenAlgo = algos.getSelectedIndex();
		 		
		 		try{
		 			numberOfPopulation = Integer.parseInt(textPopulation.getText());
		 			numberOfChildren = Integer.parseInt(textChildren.getText());
		 			numberOfGenerations = Integer.parseInt(textGenerations.getText());
		 			
		 			K = Double.parseDouble(textK.getText());
		 			sigma = Double.parseDouble(textSigma.getText());
		 			a = Double.parseDouble(textStartingA.getText());
		 			b = Double.parseDouble(textStartingB.getText());
		 			noiseA = Double.parseDouble(textStartingNoiseA.getText());
		 			noiseB = Double.parseDouble(textStartingNoiseB.getText());
		 			
		 			Simulation simulation = new Simulation(line,numberOfGenerations,numberOfPopulation,numberOfChildren,K,sigma);
		 			if(chosenAlgo == 0){
		 				//System.out.println("Algorytm (1+1)");
		 				result =simulation.startAlgorithm(Algorithm.OnePlusOne,a,b,noiseA,noiseB);
		 				caltulatePoints(result.a,result.b,xfirst,xlast);
		 				createDatasetOfMeanFitness(AlgorithmOneOfFiveSucess.meanFitnessFunction);
		 			}
		 			else if(chosenAlgo == 1){
		 				//System.out.println("Algorytm (\u00B5 + \u03BB)");
		 				result =simulation.startAlgorithm(Algorithm.MuPlusLambda,a,b,noiseA,noiseB);
		 				caltulatePoints(result.a,result.b,xfirst,xlast);
		 				createDatasetOfMeanFitness(AlgorithmMuPlusLambda.meanFitnessFunction);
		 			}
		 			else if(chosenAlgo == 2){
		 				//System.out.println("Algorytm (\u00B5, \u03BB)");
		 				result = simulation.startAlgorithm(Algorithm.MuLambda,a,b,noiseA,noiseB);
		 				caltulatePoints(result.a,result.b,xfirst,xlast);
		 				createDatasetOfMeanFitness(AlgorithmMuLambda.meanFitnessFunction);
		 			}
		 			//System.out.println("prawdziwe a:"+line.a+" prawdzie b:"+line.b);
		 			
		 			isChartActual=false;
		 			
		 			
		 		}catch(NumberFormatException e){
		 			JOptionPane.showMessageDialog(new JFrame(),
		 				    "Nie wypełniłeś wszystkich pól.",
		 				    "Illegal Format",
		 				    JOptionPane.ERROR_MESSAGE);
		 		}
				return null;
		    	 
		    	 
		    	 
		      
		     }
		     @Override
		     public void done() {
		    	 JOptionPane.showMessageDialog(new JFrame(),
		 				    "Policzylem :) \n Autorzy:\n Kasia Kosek i Adam Szczepanski",
		 				    "Dziekuje ze poczekales",
		 				    JOptionPane.INFORMATION_MESSAGE);
		    	 isAlgorithmWorking=false;
		     }
		};
		//waitingBar.setText("Licze...");
		worker.execute();
		}
			
	}
	private void caltulatePoints(double a,double b ,double xfirst,double xlast){
		textEndingA.setText(Double.toString(a));
		textEndingB.setText(Double.toString(b));
		XYSeriesCollection ds = new XYSeriesCollection();
        XYSeries  resultSeries = new XYSeries("Wyliczona \"prosta\"");
        resultSeries.add(xfirst,a*xfirst+b);
		resultSeries.add(xlast,a*xlast+b);
		ds.addSeries(series1);
		ds.addSeries(resultSeries);
		
		
		((XYPlot)chartOfFitnessFunction.getPlot()).setDataset(ds);
		
		chartOfFitnessFunction.fireChartChanged();
		PanelOfFitnessFunction.repaint();
		linePanel.repaint();
		
		
	}
	private  XYDataset createNUllDataset() {
		
		XYSeriesCollection ds = new XYSeriesCollection();
		
		series2 = new XYSeries("");
         xlast=0;
         xfirst=0;
         series2.add(0,0);
        
        ds.addSeries(series2);

        return ds;
    }
	
	private  void createDatasetOfMeanFitness(List<Double> meanFittness) {

		XYSeriesCollection ds = new XYSeriesCollection();
		series2 = new XYSeries("Srednie przystosowanie");
		int i=0;
         for (double f:meanFittness){series2.add(i,f);i++;}
        
        ds.addSeries(series2);
        
        ((XYPlot)chartOfMeanFitness.getPlot()).setDataset(ds);
		
        chartOfMeanFitness.fireChartChanged();
		PanelOfFitnessFunction.repaint();
		linePanel.repaint();
      
    }
	private  XYDataset createDataset(int numberOfPoints,double a,double b ) {
		line=new Line(a,b,numberOfPoints);
		XYSeriesCollection ds = new XYSeriesCollection();
		
         series1 = new XYSeries("Wylosowana \"prosta\"");
         xlast=0;
         xfirst=0;
         
        line.measurements.forEach(p->{series1.add(p.x,p.y);if(xfirst>p.x)xfirst=p.x;else if(xlast<p.x)xlast=p.x;});
        
        ds.addSeries(series1);

        return ds;
    }
	static{
		textActualGeneration = new JTextField("0");
		textActualGeneration.setHorizontalAlignment(JTextField.CENTER);
	}
	
}
