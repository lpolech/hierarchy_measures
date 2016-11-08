package external_measures.statistical_hypothesis;

import basic_hierarchy.interfaces.Hierarchy;
import interfaces.Hypotheses;
import interfaces.QualityMeasure;

public class FowlkesMallowsIndex implements QualityMeasure {
	private Hypotheses hypothesesCalculator;

	private FowlkesMallowsIndex() {}
	
	public FowlkesMallowsIndex(Hypotheses hypothesesCalculator)
	{
		this.hypothesesCalculator = hypothesesCalculator;
	}
	
	@Override
	public double getMeasure(Hierarchy h) {
		this.hypothesesCalculator.calculate(h);
		double numerator = this.hypothesesCalculator.getTP()*this.hypothesesCalculator.getTP();
		double denominator = (this.hypothesesCalculator.getTP() + this.hypothesesCalculator.getFP()) * (this.hypothesesCalculator.getTP() + this.hypothesesCalculator.getFN());
		return Math.sqrt(numerator/denominator);
	}

	@Override
	public double getDesiredValue() {
		return 1;
	}

	@Override
	public double getNotDesiredValue() {
		return 0;
	}

}
