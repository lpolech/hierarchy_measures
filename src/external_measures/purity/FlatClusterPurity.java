package external_measures.purity;

import java.util.Collections;
import java.util.HashMap;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;
import basic_hierarchy.interfaces.Node;
import common.CommonQualityMeasure;

public class FlatClusterPurity extends CommonQualityMeasure {

	@Override
	public double getMeasure(Hierarchy h) {
		int sumOfDominantGroupClasses = 0;
		for(Node n: h.getGroups())
		{
			if(!n.getNodeInstances().isEmpty()) {
                HashMap<String, Integer> classesCount = new HashMap<>();
                for (Instance i : n.getNodeInstances()) {
                    if (classesCount.containsKey(i.getTrueClass())) {
                        classesCount.put(i.getTrueClass(), classesCount.get(i.getTrueClass()) + 1);
                    } else {
                        classesCount.put(i.getTrueClass(), 1);
                    }
                }

                sumOfDominantGroupClasses += Collections.max(classesCount.values());
            }
		}
		return sumOfDominantGroupClasses/(double)h.getOverallNumberOfInstances();
	}

	@Override
	public double getDesiredValue() {
		return 1;
	}

	@Override
	public double getNotDesiredValue() {
		return 0;
	}

}
