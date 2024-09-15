package hierarchy_measures.internal_measures;

import basic_hierarchy.interfaces.Hierarchy;
import hierarchy_measures.common.CommonQualityMeasure;
import hierarchy_measures.interfaces.DistanceMeasure;

public class FlatDunn1 extends CommonQualityMeasure {
    private FlatWithinBetweenIndex fWBI;

    private FlatDunn1() {}

    public FlatDunn1(DistanceMeasure dist)
    {
        fWBI = new FlatWithinBetweenIndex(dist);
    }

    @Override
    public double getMeasure(Hierarchy h) {
        return 1/this.fWBI.getMeasure(h);
    }

    @Override
    public double getDesiredValue() {
        return Double.MAX_VALUE;
    }

    @Override
    public double getNotDesiredValue() {
        return 0;
    }
}
