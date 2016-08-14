package external_measures;

import common.Utils;
import interfaces.DistanceMeasure;
import interfaces.QualityMeasure;

import java.util.LinkedList;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;

public class PartialOrderFmeasure implements QualityMeasure {
	public int TP = 0;
	public int FP = 0;
	public int TN = 0;
	public int FN = 0;

	@Override
	public double getMeasure(Hierarchy h, DistanceMeasure dist) {
		TP = 0;
		FP = 0;
		TN = 0;
		FN = 0;
		LinkedList<Instance> allInstances = h.getRoot().getSubtreeInstances();
		Instance[] allInstancesArr = allInstances.toArray(new Instance[allInstances.size()]);
		
		for(int i = 0; i < allInstancesArr.length; i++)
		{
			String firstTrueClass = allInstancesArr[i].getTrueClass();
			String firstAssignClass = allInstancesArr[i].getNodeId();
			for(int j = 0; j < allInstancesArr.length; j++)
			{
				if(i != j)
				{
					String secondTrueClass = allInstancesArr[j].getTrueClass();
					String secondAssignClass = allInstancesArr[j].getNodeId();
					
					if(Utils.isTheSameOrSubclass(firstTrueClass, secondTrueClass))
					{
						if(Utils.isTheSameOrSubclass(firstAssignClass, secondAssignClass))
						{
							TP++;
						}
						else
						{
							FN++;
						}
					}
					else
					{
						if(Utils.isTheSameOrSubclass(firstAssignClass, secondAssignClass))
						{
							FP++;
						}
						else
						{
							TN++;
						}
					}
				}
			}
		}
		double numerator = 2*TP;
		double denominator = 2*TP + FP + FN;
		return numerator/denominator;
	}

//	@Override
//	public double desiredValue() {
//		return 1;
//	}
//
//	@Override
//	public double notDesiredValue() {
//		return 0;
//	}

}
