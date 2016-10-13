package internal_measures;

import basic_hierarchy.interfaces.Hierarchy;
import interfaces.QualityMeasure;
import interfaces.DistanceMeasure;

public class FlatDunn1 implements QualityMeasure {
    private DistanceMeasure dist;
    private FlatWithinBetweenIndex FWBI;

    private FlatDunn1() {}

    public FlatDunn1(DistanceMeasure dist)
    {
        this.dist = dist;
        FWBI = new FlatWithinBetweenIndex(dist);
    }

    @Override
    public double getMeasure(Hierarchy h) {
        return 1/this.FWBI.getMeasure(h);
    }
}
