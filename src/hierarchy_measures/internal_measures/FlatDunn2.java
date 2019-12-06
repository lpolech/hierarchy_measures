package hierarchy_measures.internal_measures;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;
import basic_hierarchy.interfaces.Node;
import hierarchy_measures.common.CommonQualityMeasure;
import hierarchy_measures.interfaces.DistanceMeasure;

import java.util.ArrayList;

public class FlatDunn2 extends CommonQualityMeasure {
    private DistanceMeasure dist;

    protected FlatDunn2() {}

    public FlatDunn2(DistanceMeasure dist)
    {
        this.dist = dist;
    }

    @Override
    public double getMeasure(Hierarchy h) {
        return calculate(h, false);
    }

    @Override
    public double getDesiredValue() {
        return Double.MAX_VALUE;
    }

    @Override
    public double getNotDesiredValue() {
        return 0;
    }

    protected double calculate(Hierarchy h, boolean returnMeasureReversion) {
        Node[] nodes = h.getGroups();
        double minDistanceBetweenPointsInDifferentClusters = Double.MAX_VALUE;
        double maxDistanceBetweenPointsWithinCluster = (-1)*Double.MAX_VALUE;
        for(int n1 = 0; n1 < nodes.length; n1++)
        {
            ArrayList<Instance> n1Instances = new ArrayList<>(nodes[n1].getNodeInstances());
            for(int n2 = n1 + 1; n2 < nodes.length; n2++)
            {
                ArrayList<Instance> n2Instances = new ArrayList<>(nodes[n2].getNodeInstances());
                for(int i1 = 0; i1 < n1Instances.size(); i1++)
                {
                    for(int i2 = 0; i2 < n2Instances.size(); i2++)
                    {
                        double distance = dist.getDistance(n1Instances.get(i1), n2Instances.get(i2));//TODO: random access on LinkedList is slow
                        minDistanceBetweenPointsInDifferentClusters = Math.min(distance,
                                minDistanceBetweenPointsInDifferentClusters);
                    }
                }
            }

            for(int i1 = 0; i1 < n1Instances.size(); i1++)
            {
                for(int i2 = 0; i2 < n1Instances.size(); i2++)
                {
                    double distance = dist.getDistance(n1Instances.get(i1), n1Instances.get(i2));
                    maxDistanceBetweenPointsWithinCluster = Math.max(distance, maxDistanceBetweenPointsWithinCluster);
                }
            }
        }

        if(minDistanceBetweenPointsInDifferentClusters == Double.MAX_VALUE) {
            System.err.println("FlatDunn2.getMeasure minDistanceBetweenPointsInDifferentClusters didn't change! It is " +
                    "probably because every cluster contain at maximum 1 instance. Returning NaN.");

            return Double.NaN;
        }
        if(maxDistanceBetweenPointsWithinCluster == (-1)*Double.MAX_VALUE) {
            System.err.println("FlatDunn2.getMeasure maxDistanceBetweenPointsWithinCluster didn't change! It is probably " +
                    "because every cluster contain at maximum 1 instance. Returning NaN.");

            return Double.NaN;
        }
        return returnMeasureReversion?
                maxDistanceBetweenPointsWithinCluster/minDistanceBetweenPointsInDifferentClusters:
                minDistanceBetweenPointsInDifferentClusters/maxDistanceBetweenPointsWithinCluster;
    }
}
