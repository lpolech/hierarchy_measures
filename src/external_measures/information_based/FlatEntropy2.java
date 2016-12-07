package external_measures.information_based;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Node;
import common.Utils;

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
    public double getMeasure(Hierarchy h) {
        double measure = 0.0;
        for(String c: h.getClasses())
        {
            int numberOfClassObjects = h.getParticularClassCount(c, false);
            double classToAllObjectsRatio = numberOfClassObjects/(double)h.getOverallNumberOfInstances();
            double particularClassCumulativeRecall = 0.0;
            for(Node n: h.getGroups())
            {
                int numberOfClassInstancesWithinNode = Utils.getClassInstancesWithinNode(n, c, false, false).size();
                double nodePrecision = numberOfClassInstancesWithinNode/(double)numberOfClassObjects;
                particularClassCumulativeRecall += numberOfClassInstancesWithinNode == 0 || numberOfClassObjects == 0?
                        0.0 : (nodePrecision * Math.log(nodePrecision)/this.baseLogarithm);
            }
            measure += (classToAllObjectsRatio*particularClassCumulativeRecall);
        }
        return (-1)*measure;
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
