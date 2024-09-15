package hierarchy_measures.external_measures.information_based;

import hierarchy_measures.common.CommonQualityMeasure;

public abstract class FlatEntropy extends CommonQualityMeasure {
	protected double baseLogarithm;

	public FlatEntropy(double logBase) {
		this.baseLogarithm = Math.log(logBase);
	}
}
