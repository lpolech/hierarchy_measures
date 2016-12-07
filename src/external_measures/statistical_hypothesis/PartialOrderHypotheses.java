package external_measures.statistical_hypothesis;

import java.util.LinkedList;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;
import common.Utils;
import interfaces.Hypotheses;

public class PartialOrderHypotheses implements Hypotheses {//TODO: mozna z tej klasy i FlatHypotheses zrobic jedna klase przyjmujaca lambde czy tam funkcje, ktora bedzie odpalana w tych ifach
	private long TP = Integer.MIN_VALUE;
	private long FP = Integer.MIN_VALUE;
	private long TN = Integer.MIN_VALUE;
	private long FN = Integer.MIN_VALUE;

	public void calculate(Hierarchy h) 
	{
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
	}

	public long getTP() {
		return TP;
	}

	public long getFP() {
		return FP;
	}

	public long getTN() {
		return TN;
	}

	public long getFN() {
		return FN;
	}
}
