package internal_measures;

import basic_hierarchy.interfaces.Hierarchy;
import interfaces.QualityMeasure;
import interfaces.DistanceMeasure;

public class FlatDunn1 implements QualityMeasure {
    private final FlatWithinBetweenIndex FWBI = new FlatWithinBetweenIndex();

    @Override
    public double getMeasure(Hierarchy h, DistanceMeasure dist) {
        return 1/this.FWBI.getMeasure(h, dist);
    }
}
