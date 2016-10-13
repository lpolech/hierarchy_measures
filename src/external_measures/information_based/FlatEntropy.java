package external_measures.information_based;

import basic_hierarchy.interfaces.Hierarchy;
import interfaces.DistanceMeasure;
import interfaces.QualityMeasure;

public abstract class FlatEntropy implements QualityMeasure {
    protected double baseLogarithm;

    public FlatEntropy(double logBase)
    {
        this.baseLogarithm = Math.log(logBase);
    }

    @Override
    public abstract double getMeasure(Hierarchy h);
}
