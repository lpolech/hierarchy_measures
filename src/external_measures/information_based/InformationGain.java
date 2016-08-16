package external_measures.information_based;

import basic_hierarchy.interfaces.Hierarchy;
import interfaces.DistanceMeasure;
import interfaces.QualityMeasure;

public class InformationGain extends FlatEntropy {
    private FlatEntropy entropyMeasure = null;

    /*This constructor is private because user needs to specify which entropy measure to use*/
    private InformationGain()
    {
        super(2.0);
    }

    public InformationGain(FlatEntropy entropyMeasure)
    {
        super(entropyMeasure.baseLogarithm);
        this.entropyMeasure = entropyMeasure;
    }

    @Override
    public double getMeasure(Hierarchy h, DistanceMeasure dist) {
        double cumulativeClassRatio = 0.0;
        for(String c: h.getClasses())
        {
            double classRatio = h.getClassCount(c, false)/(double)h.getNumberOfInstances();
            cumulativeClassRatio += (classRatio * Math.log(classRatio)/this.baseLogarithm);
        }
        cumulativeClassRatio *= (-1);
        return cumulativeClassRatio - this.entropyMeasure.getMeasure(h, dist);
    }
}
