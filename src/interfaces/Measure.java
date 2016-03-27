package interfaces;

import basic_hierarchy.interfaces.Hierarchy;

public interface Measure {
	public double getMeasure(Hierarchy h);
	/*
	 * Which value indicates the best hierarchy
	 * If measure should be as close to 1 as possible, then this method should return 1.0
	 * If measure should be maximized/minimized, then method should return Double.POSITIVE_INFINITY/Double.NEGATIVE_INFINITY
	 */
	public double desiredValue();
	/*
	 * Opposite to Measure#desiredValue(), should indicate which value means the worst hierarchy
	 * @see Measure#desiredValue()
	 */
	public double notDesiredValue();
}
