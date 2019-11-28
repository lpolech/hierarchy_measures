package external_measures.statistical_hypothesis;

import basic_hierarchy.interfaces.Hierarchy;
import common.CommonQualityMeasure;
import interfaces.Hypotheses;

public class JaccardIndex extends CommonQualityMeasure {
	private Hypotheses hypothesesCalculator;

	private JaccardIndex() {}
	
	public JaccardIndex(Hypotheses hypothesesCalculator)
	{
		this.hypothesesCalculator = hypothesesCalculator;
	}
	
	@Override
	public double getMeasure(Hierarchy h) {
		this.hypothesesCalculator.calculate(h);
		return this.hypothesesCalculator.getTP()/(double)(this.hypothesesCalculator.getTP() + this.hypothesesCalculator.getFP() + this.hypothesesCalculator.getFN());
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
