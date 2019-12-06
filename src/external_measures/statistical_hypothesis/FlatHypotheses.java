package external_measures.statistical_hypothesis;

import java.util.LinkedList;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Instance;
import interfaces.Hypotheses;

public class FlatHypotheses implements Hypotheses {
	private long tp = Integer.MIN_VALUE;
	private long fp = Integer.MIN_VALUE;
	private long tn = Integer.MIN_VALUE;
	private long fn = Integer.MIN_VALUE;

	public void calculate(Hierarchy h)
	{
		tp = 0;
		fp = 0;
		tn = 0;
		fn = 0;
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
							tp++;
						}
						else
						{
							fn++;
						}
					}
					else
					{
						if(firstAssignClass.equals(secondAssignClass))
						{
							fp++;
						}
						else
						{
							tn++;
						}
					}
				}
			}
		}
	}

	public long getTP() {
		return tp;
	}

	public long getFP() {
		return fp;
	}

	public long getTN() {
		return tn;
	}

	public long getFN() {
		return fn;
	}
}
