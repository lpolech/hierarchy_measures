package external_measures.information_based;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Node;
import common.Utils;

/**
 * Implemented based on http://nlp.stanford.edu/IR-book/html/htmledition/evaluation-of-clustering-1.html
 */
public class FlatMutualInformation extends FlatEntropy {
    public FlatMutualInformation()
    {
        super(2.0);
    }

    public FlatMutualInformation(double logBase)
    {
        super(logBase);
    }

    @Override
    public double getMeasure(Hierarchy h) {
        int overallNumberOfObjects = h.getOverallNumberOfInstances();
        double measure = 0.0;
        for(Node n: h.getGroups())
        {
            int nodeInstancesCount = n.getNodeInstances().size();
            if(nodeInstancesCount != 0) {
                for (String c : h.getClasses()) {
                    int classInstancesWithinNodeCount = Utils.getClassInstancesWithinNode(n, c, false, false).size();
                    int classCount = h.getParticularClassCount(c, false);
                    if (classInstancesWithinNodeCount != 0) {
                        measure += (
                                (classInstancesWithinNodeCount / (double) overallNumberOfObjects)
                                        * (
                                        Math.log(
                                                (overallNumberOfObjects * classInstancesWithinNodeCount)
                                                        / (double) (nodeInstancesCount * classCount)
                                        ) / this.baseLogarithm
                                )
                        );
                    }
                }
            }
        }
        return measure;
    }

    @Override
    public double getDesiredValue() {
        return Double.MAX_VALUE;
    }

    @Override
    public double getNotDesiredValue() {
        return 0;
    }
}
