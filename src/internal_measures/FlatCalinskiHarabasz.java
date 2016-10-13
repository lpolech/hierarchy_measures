package internal_measures;

import basic_hierarchy.implementation.BasicInstance;
import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;
import basic_hierarchy.interfaces.Node;
import interfaces.DistanceMeasure;
import interfaces.QualityMeasure;

import java.util.LinkedList;

public class FlatCalinskiHarabasz implements QualityMeasure { //inspired by
    // https://www.mathworks.com/help/stats/clustering.evaluation.calinskiharabaszevaluation-class.html?requestedDomain=www.mathworks.com
    private DistanceMeasure dist;

    private FlatCalinskiHarabasz() {}

    public FlatCalinskiHarabasz(DistanceMeasure dist)
    {
        this.dist = dist;
    }

    @Override
    public double getMeasure(Hierarchy h) {
        double betweenGroupsVariance = 0.0;
        double withinGroupsVariance = 0.0;
        double[] allObjectsMeanVect = new double[h.getRoot().getSubtreeInstances().getFirst().getData().length];

        Node root = h.getRoot();
        LinkedList<Instance> allObjects = root.getSubtreeInstances();
        for(Instance i: allObjects)
        {
            for(int d = 0; d < allObjectsMeanVect.length; d++)
            {
                allObjectsMeanVect[d] += i.getData()[d];
            }
        }
        for(int d = 0; d < allObjectsMeanVect.length; d++)
        {
            allObjectsMeanVect[d] /= allObjects.size();
        }
        Instance allObjectsMeanInstance = new BasicInstance("allObjectsMeanInstance", "allObjectsMeanInstance",
                allObjectsMeanVect, "allObjectsMeanInstance");

        Node[] nodes = h.getGroups();
        for(int n = 0; n < nodes.length; n++)
        {
            betweenGroupsVariance += nodes[n].getNodeInstances().size()
                    * Math.pow(this.dist.getDistance(allObjectsMeanInstance, nodes[n].getNodeRepresentation()), 2);

            Instance groupCenter = nodes[n].getNodeRepresentation();
            for(Instance i: nodes[n].getNodeInstances())
            {
                withinGroupsVariance += Math.pow(this.dist.getDistance(i, groupCenter), 2);
            }
        }

        return (betweenGroupsVariance * (allObjects.size() - nodes.length)) / (withinGroupsVariance * (nodes.length - 1));
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
