package internal_measures;

import basic_hierarchy.interfaces.Hierarchy;
import interfaces.DistanceMeasure;

/**
 * This class was created in order to be used with Hierarchical Internal Measure.
 */
public class FlatReversedDunn3 extends FlatDunn3 {

    public FlatReversedDunn3(DistanceMeasure dist)
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
