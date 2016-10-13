package external_measures.purity;

import java.util.Collections;
import java.util.HashMap;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;
import basic_hierarchy.interfaces.Node;
import interfaces.DistanceMeasure;
import interfaces.QualityMeasure;

public class FlatClusterPurity implements QualityMeasure {

	@Override
	public double getMeasure(Hierarchy h) {
		int sumOfDominantGroupClasses = 0;
		for(Node n: h.getGroups())
		{
			HashMap<String, Integer> classesCount = new HashMap<>();
			for(Instance i: n.getNodeInstances())
			{
				if(classesCount.containsKey(i.getTrueClass()))
				{
					classesCount.put(i.getTrueClass(), classesCount.get(i.getTrueClass()) + 1);
				}
				else
				{
					classesCount.put(i.getTrueClass(), 1);
				}
			}
			
			sumOfDominantGroupClasses += Collections.max(classesCount.values());
		}
		return sumOfDominantGroupClasses/(double)h.getNumberOfInstances();
	}

}
