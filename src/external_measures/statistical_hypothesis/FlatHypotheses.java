package external_measures.statistical_hypothesis;

import java.util.LinkedList;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;
import interfaces.Hypotheses;

public class FlatHypotheses implements Hypotheses {
	private int TP = Integer.MIN_VALUE;
	private int FP = Integer.MIN_VALUE;
	private int TN = Integer.MIN_VALUE;
	private int FN = Integer.MIN_VALUE;

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
					
					if(firstTrueClass.equals(secondTrueClass))
					{
						if(firstAssignClass.equals(secondAssignClass))
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
						if(firstAssignClass.equals(secondAssignClass))
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

	public int getTP() {
		return TP;
	}

	public int getFP() {
		return FP;
	}

	public int getTN() {
		return TN;
	}

	public int getFN() {
		return FN;
	}
}
