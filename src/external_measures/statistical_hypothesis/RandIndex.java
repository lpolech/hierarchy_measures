package external_measures.statistical_hypothesis;

import basic_hierarchy.interfaces.Hierarchy;
import common.CommonQualityMeasure;
import interfaces.Hypotheses;

public class RandIndex extends CommonQualityMeasure {
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

	@Override
	public double getDesiredValue() {
		return 1;
	}

	@Override
	public double getNotDesiredValue() {
		return 0;
	}

}
