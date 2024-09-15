package hierarchy_measures.external_measures.statistical_hypothesis;

import basic_hierarchy.interfaces.Hierarchy;
import hierarchy_measures.common.CommonQualityMeasure;
import hierarchy_measures.interfaces.Hypotheses;

public class Fmeasure extends CommonQualityMeasure {
	private float beta = 1.0f;
	private Hypotheses hypothesesCalculator;

	private Fmeasure() {
	}

	public Fmeasure(float beta, Hypotheses hypothesesCalculator) {
		this.beta = beta;
		this.hypothesesCalculator = hypothesesCalculator;
	}

	@Override
	public double getMeasure(Hierarchy h) {
		this.hypothesesCalculator.calculate(h);
		double numerator = (1.0d + this.beta * this.beta) * this.hypothesesCalculator.getTP();
		double denominator = numerator + this.beta * this.beta * this.hypothesesCalculator.getFN()
				+ this.hypothesesCalculator.getFP();
		return numerator / denominator;
	}

	@Override
	public double getDesiredValue() {
		return 1.0;
	}

	@Override
	public double getNotDesiredValue() {
		return 0;
	}

	public Hypotheses getHypotheses() {
		return hypothesesCalculator;
	}

}
