package external_measures.information_based_measures;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Node;
import common.Utils;
import interfaces.DistanceMeasure;
import interfaces.QualityMeasure;

public class FlatEntropy2 extends FlatEntropy {
    public FlatEntropy2()
    {
        super(2.0);
    }

    public FlatEntropy2(double logBase)
    {
        super(logBase);
    }

    @Override
    public double getMeasure(Hierarchy h, DistanceMeasure dist) {
        double measure = 0.0;
        for(String c: h.getClasses())
        {
            int numberOfClassObjects = h.getClassCount(c, false);
            double classToAllObjectsRatio = numberOfClassObjects/(double)h.getNumberOfInstances();
            double particularClassCumulativeRecall = 0.0;
            for(Node n: h.getGroups())
            {
                int numberOfClassInstancesWithinNode = Utils.getClassInstancesWithinNode(n, c, false, false).size();
                double nodePrecision = numberOfClassInstancesWithinNode/(double)numberOfClassObjects;
                particularClassCumulativeRecall += (nodePrecision * Math.log(nodePrecision)/this.baseLogarithm);
            }
            measure += (classToAllObjectsRatio*particularClassCumulativeRecall);
        }
        return (-1)*measure;
    }
}
