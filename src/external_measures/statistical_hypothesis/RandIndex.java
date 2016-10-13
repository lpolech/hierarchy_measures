package external_measures.statistical_hypothesis;

import basic_hierarchy.interfaces.Hierarchy;
import interfaces.DistanceMeasure;
import interfaces.Hypotheses;
import interfaces.QualityMeasure;

public class RandIndex implements QualityMeasure {
	private Hypotheses hypothesesCalculator;

	private RandIndex() {}
	
	public RandIndex(Hypotheses hypothesesCalculator)
	{
		this.hypothesesCalculator = hypothesesCalculator;
	}
	
	@Override
	public double getMeasure(Hierarchy h) {
		this.hypothesesCalculator.calculate(h);
		return (this.hypothesesCalculator.getTP() + this.hypothesesCalculator.getTN())
				/(double)(this.hypothesesCalculator.getTP() + this.hypothesesCalculator.getTN() + this.hypothesesCalculator.getFP() + this.hypothesesCalculator.getFN());
	}

}
