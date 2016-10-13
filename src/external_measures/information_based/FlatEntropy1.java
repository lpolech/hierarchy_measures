package external_measures.information_based;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Node;
import common.Utils;
import interfaces.DistanceMeasure;
import interfaces.QualityMeasure;

public class FlatEntropy1 extends FlatEntropy {
    public FlatEntropy1()
    {
        super(2.0);
    }

    public FlatEntropy1(double logBase)
    {
        super(logBase);
    }

    @Override
    public double getMeasure(Hierarchy h) {
        double measure = 0.0;
        for(Node n: h.getGroups())
        {
            int numberOfNodeObjects = n.getNodeInstances().size();
            double nodeToAllObjectsRatio = numberOfNodeObjects/(double)h.getNumberOfInstances();
            double particularNodeCumulativePrecision = 0.0;
            for(String c: h.getClasses())
            {
                int numberOfClassInstancesWithinNode = Utils.getClassInstancesWithinNode(n, c, false, false).size();
                double classPrecision = numberOfClassInstancesWithinNode/(double)numberOfNodeObjects;
                particularNodeCumulativePrecision += (classPrecision * Math.log(classPrecision)/this.baseLogarithm);
            }
            measure += (nodeToAllObjectsRatio*particularNodeCumulativePrecision);
        }
        return (-1)*measure;
    }
}
