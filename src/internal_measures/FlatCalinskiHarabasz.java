package internal_measures;

import basic_hierarchy.implementation.BasicInstance;
import basic_hierarchy.implementation.BasicNode;
import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;
import basic_hierarchy.interfaces.Node;
import common.CommonQualityMeasure;
import interfaces.DistanceMeasure;
import interfaces.QualityMeasure;

import java.util.LinkedList;

public class FlatCalinskiHarabasz extends CommonQualityMeasure { //inspired by
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

        Instance[] oldRepr = new Instance[nodes.length];
        for(int n = 0; n < nodes.length; n++) {
            oldRepr[n] = ((BasicNode)nodes[n]).recalculateCentroid(false);
        }
        
        int numberOfSkippedEmptyNodes = 0;
        for(int n = 0; n < nodes.length; n++)
        {
            if(nodes[n].getNodeInstances().isEmpty()) {
                numberOfSkippedEmptyNodes += 1;
            }
            else {
                betweenGroupsVariance += nodes[n].getNodeInstances().size()
                        * Math.pow(this.dist.getDistance(allObjectsMeanInstance, nodes[n].getNodeRepresentation()), 2);

                Instance groupCenter = nodes[n].getNodeRepresentation();
                for (Instance i : nodes[n].getNodeInstances()) {
                    withinGroupsVariance += Math.pow(this.dist.getDistance(i, groupCenter), 2);
                }
            }
        }

        for(int n = 0; n < nodes.length; n++) {
            nodes[n].setRepresentation(oldRepr[n]);
        }

        int denominatorNormalizingFactor = (nodes.length - numberOfSkippedEmptyNodes - 1);
        if(denominatorNormalizingFactor <= 0) {
            System.err.println("FlatCalinskiHarabasz.getMeasure - the denominatorNormalizingFactor is equal or below 0, " +
                    "so there should be something wrong with the input hierarchy probably the number of non-empty clusters" +
                    "is equal to 1. Returning NaN.");

            return Double.NaN;
        }

        if(withinGroupsVariance <= 0) {
            System.err.println("FlatCalinskiHarabasz.getMeasure - the withinGroupsVariance is equal or below 0, " +
                    "so there should be something wrong with the input hierarchy probably there are only one-element" +
                    "clusters. Returning NaN.");

            return Double.NaN;
        }

        return (betweenGroupsVariance * (allObjects.size() - (nodes.length - numberOfSkippedEmptyNodes)))
                / (withinGroupsVariance * denominatorNormalizingFactor);// what if there are 1-object clusters and withinGroupVariance is 0?
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
