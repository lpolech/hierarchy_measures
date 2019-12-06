package hierarchy_measures.external_measures.information_based;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Node;
import hierarchy_measures.common.Utils;

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
            double nodeToAllObjectsRatio = numberOfNodeObjects/(double)h.getOverallNumberOfInstances();
            double particularNodeCumulativePrecision = 0.0;
            for(String c: h.getClasses())
            {
                int numberOfClassInstancesWithinNode = Utils.getClassInstancesWithinNode(n, c, false, false).size();
                double classPrecision = numberOfClassInstancesWithinNode/(double)numberOfNodeObjects;
                particularNodeCumulativePrecision += numberOfClassInstancesWithinNode == 0 || numberOfNodeObjects == 0?
                        0.0: (classPrecision * Math.log(classPrecision)/this.baseLogarithm);
            }
            measure += (nodeToAllObjectsRatio*particularNodeCumulativePrecision);
        }
        return (-1)*measure;
    }

    @Override
    public double getDesiredValue() {
        return 0.0;
    }

    @Override
    public double getNotDesiredValue() {
        return Double.MAX_VALUE;
    }
}
