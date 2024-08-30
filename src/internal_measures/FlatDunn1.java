package internal_measures;

import basic_hierarchy.interfaces.Hierarchy;
import common.CommonQualityMeasure;
import interfaces.QualityMeasure;
import interfaces.DistanceMeasure;

public class FlatDunn1 extends CommonQualityMeasure {
    private FlatWithinBetweenIndex FWBI;

    private FlatDunn1() {}

    public FlatDunn1(DistanceMeasure dist)
    {
        FWBI = new FlatWithinBetweenIndex(dist);
    }

    @Override
    public double getMeasure(Hierarchy h) {
        return 1/this.FWBI.getMeasure(h);
    }

    @Override
    public double getDesiredValue() {
        return Double.MAX_VALUE;
    }

    @Override
    public double getNotDesiredValue() {
        return 0;
    }

    @Override
    public boolean isFirstMeasureBetterThanSecond(double firstMeasure, double secondMeasure) {
        return firstMeasure > secondMeasure;
    }

    @Override
    public boolean shouldMeasureBeMaximised() {
        return true;
    }

    @Override
    public String getName() {
        return "FD1";
    }
}
