package common;

import basic_hierarchy.interfaces.Instance;
import basic_hierarchy.interfaces.Node;
import internal_measures.statistics.AvgWithStdev;

import java.util.ArrayList;
import java.util.LinkedList;

public class Utils {

    public static ArrayList<Instance> getClassInstancesWithinNode(Node n, String className, boolean useSubtree,
                                                                  boolean withClassHierarchy)
    {
        ArrayList<Instance> instances = new ArrayList<>();
        for(Instance i: (useSubtree? n.getSubtreeInstances(): n.getNodeInstances()))
        {
            if(!withClassHierarchy && i.getTrueClass().equals(className))
            {
                instances.add(i);
            }
            else if(withClassHierarchy && isTheSameOrSubclass(i.getTrueClass(), className))
            {
                instances.add(i);
            }
        }
        return instances;
    }

    public static boolean isTheSameOrSubclass(String baseClass, String queryClass) {
        return (queryClass + basic_hierarchy.common.Constants.HIERARCHY_BRANCH_SEPARATOR)
                .startsWith(baseClass + basic_hierarchy.common.Constants.HIERARCHY_BRANCH_SEPARATOR);
    }
    
    public static boolean isSubclass(String baseClass, String queryClass) {
    	return (queryClass + basic_hierarchy.common.Constants.HIERARCHY_BRANCH_SEPARATOR)
                .startsWith(baseClass + basic_hierarchy.common.Constants.HIERARCHY_BRANCH_SEPARATOR)
                && queryClass.length() > baseClass.length();
    }
    
    public static Double[] nodeSubtreeVariance(Node n)
    {
    	LinkedList<Instance> instances = n.getSubtreeInstances();
    	assert !instances.isEmpty();
    	
    	int dim = instances.getFirst().getData().length;
    	Double[] variance = new Double[dim];
		for(int i = 0; i < dim; i++)
		{
			variance[i] = 0.0;
		}

    	double[] mean = new double[dim];
    	
    	for(Instance i: instances)
    	{
    		for(int d = 0; d < dim; d++)
    		{
    			mean[d] += i.getData()[d];
    		}
    	}
    	
    	for(int d = 0; d < dim; d++)
		{
			mean[d] /= instances.size();
		}
    	
    	for(Instance i: instances)
    	{
    		for(int d = 0; d < dim; d++)
    		{
    			variance[d] += Math.pow(i.getData()[d] - mean[d], 2);
    		}
    	}
    	
    	for(int d = 0; d < dim; d++)
		{
    		variance[d] /= instances.size();
		}
    	
    	return variance;
    }

    public static double populationMean(double[] samples)
	{
		double mean = 0.0;
		for(double s: samples)
		{
			mean += s;
		}

		return mean/samples.length;
	}

    public static double populationMean(int[] samples)
    {
        double mean = 0.0;
        for(double s: samples)
        {
            mean += s;
        }

        return mean/samples.length;
    }

    public static double populationVariance(double[] samples)
    {
        double mean = populationMean(samples);
        return populationVariance(samples, mean);
    }

	public static double populationVariance(double[] samples, double mean)
    {
        double var = 0.0;
        for(double s: samples)
        {
            var += Math.pow(s - mean, 2);
        }
        return var/samples.length;
    }

    public static double populationVariance(int[] samples, double mean)
    {
        double var = 0.0;
        for(double s: samples)
        {
            var += Math.pow(s - mean, 2);
        }
        return var/samples.length;
    }

    public static double populationStdev(double[] samples)
    {
        return Math.sqrt(populationVariance(samples));
    }

    public static double populationStdev(double[] samples, double mean)
    {
        return Math.sqrt(populationVariance(samples, mean));
    }

    public static double populationStdev(int[] samples, double mean)
    {
        return Math.sqrt(populationVariance(samples, mean));
    }

    public static double[] toPrimitiveDoubles(LinkedList<Integer> array) {
        if (array == null)
        {
            return null;
        }

        final double[] result = new double[array.size()];
        for (int i = 0; i < array.size(); i++)
        {
            result[i] = array.get(i).doubleValue();
        }

        return result;
    }

    public static AvgWithStdev populationMeanAndStdev(double[] samples)
    {
        AvgWithStdev result = new AvgWithStdev();
        result.setAvg(populationMean(samples));
        result.setStdev(populationStdev(samples, result.getAvg()));

        return result;
    }
}

