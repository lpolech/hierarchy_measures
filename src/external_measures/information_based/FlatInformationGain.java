package external_measures.information_based;

import basic_hierarchy.interfaces.Hierarchy;

public class FlatInformationGain extends FlatEntropy {
    private FlatEntropy entropyMeasure = null;

    /*This constructor is private because user needs to specify which entropy measure to use*/
    private FlatInformationGain()
    {
        super(2.0);
    }

    public FlatInformationGain(double logBase, FlatEntropy entropyMeasure)
    {
        super(logBase);
        this.entropyMeasure = entropyMeasure;
    }

    @Override
    public double getMeasure(Hierarchy h) {
        double cumulativeClassRatio = 0.0;
        for(String c: h.getClasses())
        {
            double classRatio = h.getClassCount(c, false)/(double)h.getNumberOfInstances();
            cumulativeClassRatio += (classRatio * Math.log(classRatio)/this.baseLogarithm);
        }
        cumulativeClassRatio *= (-1);
        return cumulativeClassRatio - this.entropyMeasure.getMeasure(h);
    }

    @Override
    public double getDesiredValue() {
        return 1.0;
    }

    @Override
    public double getNotDesiredValue() {
        return 0;
    }
}
