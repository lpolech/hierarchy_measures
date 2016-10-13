package internal_measures;

import basic_hierarchy.interfaces.Instance;
import basic_hierarchy.interfaces.Node;
import interfaces.DistanceMeasure;
import interfaces.QualityMeasure;
import basic_hierarchy.interfaces.Hierarchy;

import java.util.LinkedList;

public class FlatWithinBetweenIndex implements QualityMeasure {
    private DistanceMeasure dist;

    private FlatWithinBetweenIndex() {}

    public FlatWithinBetweenIndex(DistanceMeasure dist)
    {
        this.dist = dist;
    }

    @Override
	public double getMeasure(Hierarchy h) {
		double maxWithinNodeDistance = (-1)*Double.MAX_VALUE;
        double minNodesCentersDistance = Double.MAX_VALUE;

        Node[] groups = h.getGroups();
        for(int n1 = 0; n1 < groups.length; n1++)
		{
            LinkedList<Instance> n1Instances = groups[n1].getNodeInstances();
            for(int i1 = 0; i1 < n1Instances.size(); i1++)
            {
                for(int i2 = i1 + 1; i2 < n1Instances.size(); i2++)
                {
                    double distance = dist.getDistance(n1Instances.get(i1), n1Instances.get(i2));//TODO: random index access is slow on linked lists
                    maxWithinNodeDistance = Math.max(distance, maxWithinNodeDistance);
                }
            }

            for(int n2 = n1 + 1; n2 < groups.length; n2++)
            {
                double distance = dist.getDistance(groups[n1].getNodeRepresentation(), groups[n2].getNodeRepresentation());
                minNodesCentersDistance = Math.min(distance, minNodesCentersDistance);
            }
		}
		return maxWithinNodeDistance/minNodesCentersDistance;
	}

}
