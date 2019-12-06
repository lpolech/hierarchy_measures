package hierarchy_measures.internal_measures;

import basic_hierarchy.interfaces.Hierarchy;
import hierarchy_measures.interfaces.DistanceMeasure;

/**
 * This class was created in order to be used with Hierarchical Internal Measure.
 */
public class FlatReversedDunn2 extends FlatDunn2 {

    public FlatReversedDunn2(DistanceMeasure dist)
    {
        super(dist);
    }

    @Override
    public double getMeasure(Hierarchy h) {
        return calculate(h, true);
    }

    @Override
    public double getDesiredValue() {
        return 0;
    }

    @Override
    public double getNotDesiredValue() {
        return Double.MAX_VALUE;
    }
}
