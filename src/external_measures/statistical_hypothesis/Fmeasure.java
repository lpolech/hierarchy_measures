package external_measures.statistical_hypothesis;

import basic_hierarchy.interfaces.Hierarchy;
import interfaces.DistanceMeasure;
import interfaces.Hypotheses;
import interfaces.QualityMeasure;

public class Fmeasure implements QualityMeasure {
	private float beta = 1.0f;
	private Hypotheses hypothesesCalculator;
	
	private Fmeasure() {};
	
	public Fmeasure(float beta, Hypotheses hypothesesCalculator)
	{
		this.beta = beta;
		this.hypothesesCalculator = hypothesesCalculator;
	}

	@Override
	public double getMeasure(Hierarchy h) {
		this.hypothesesCalculator.calculate(h);
		double numerator = (1.0d + this.beta*this.beta) * this.hypothesesCalculator.getTP();
		double denominator = numerator + this.beta*this.beta*this.hypothesesCalculator.getFN() + this.hypothesesCalculator.getFP();
		return numerator/denominator;
	}
}
