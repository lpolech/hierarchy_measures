package internal_measures;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;
import basic_hierarchy.interfaces.Node;
import interfaces.DistanceMeasure;
import interfaces.QualityMeasure;

import java.util.LinkedList;

public class FlatDunn3 implements QualityMeasure {
    private DistanceMeasure dist;

    private FlatDunn3() {}

    public FlatDunn3(DistanceMeasure dist)
    {
        this.dist = dist;
    }

    @Override
    public double getMeasure(Hierarchy h) {
        Node[] nodes = h.getGroups();
        double minDistanceBetweenPointsInDifferentClusters = Double.MAX_VALUE;
        double maxCumulativeDistanceWithinCluster = (-1)*Double.MAX_VALUE;
        for(int n1 = 0; n1 < nodes.length; n1++)
        {
            LinkedList<Instance> n1Instances = nodes[n1].getNodeInstances();
            for(int n2 = n1 + 1; n2 < nodes.length; n2++)
            {
                LinkedList<Instance> n2Instances = nodes[n2].getNodeInstances();
                for(int i1 = 0; i1 < n1Instances.size(); i1++)
                {
                    for(int i2 = i1 + 1; i2 < n2Instances.size(); i2++)
                    {
                        double distance = dist.getDistance(n1Instances.get(i1), n2Instances.get(i2));
                        minDistanceBetweenPointsInDifferentClusters = Math.min(distance,
                                minDistanceBetweenPointsInDifferentClusters);
                    }
                }
            }

            double cumulativeDistanceWithinCluster = 0.0;
            for(int i1 = 0; i1 < n1Instances.size(); i1++)
            {
                for(int i2 = 0; i2 < n1Instances.size(); i2++)
                {
                    if(i1 != i2)
                    {
                        cumulativeDistanceWithinCluster += dist.getDistance(n1Instances.get(i1), n1Instances.get(i2));
                    }
                }
            }
            cumulativeDistanceWithinCluster =
                    cumulativeDistanceWithinCluster/(double)(n1Instances.size() * (n1Instances.size() - 1));
            maxCumulativeDistanceWithinCluster =
                    Math.max(cumulativeDistanceWithinCluster, maxCumulativeDistanceWithinCluster);
        }

        return minDistanceBetweenPointsInDifferentClusters/maxCumulativeDistanceWithinCluster;
    }
}
