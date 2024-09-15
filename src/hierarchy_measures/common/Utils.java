package hierarchy_measures.common;

import java.util.ArrayList;
import java.util.LinkedList;

import basic_hierarchy.interfaces.Instance;
import basic_hierarchy.interfaces.Node;
import hierarchy_measures.internal_measures.statistics.AvgWithStdev;

public class Utils {

	private Utils() {
		throw new AssertionError("Static class");
	}

	public static ArrayList<Instance> getClassInstancesWithinNode(Node n, String className, boolean useSubtree,
			boolean withClassHierarchy) {
		ArrayList<Instance> instances = new ArrayList<>();
		for (Instance i : (useSubtree ? n.getSubtreeInstances() : n.getNodeInstances())) {
			if ((!withClassHierarchy && i.getTrueClass().equals(className))
					|| (withClassHierarchy && isTheSameOrSubclass(i.getTrueClass(), className))) {
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

	public static Double[] nodeSubtreeVariance(Node n, boolean isPopulation) {
		LinkedList<Instance> instances = n.getSubtreeInstances();
		return nodeSubtreeVariance(instances, isPopulation);
	}

	public static Double[] nodeSubtreeVariance(LinkedList<Instance> instances, boolean isPopulation) {
		if (instances.isEmpty())
			throw new AssertionError("Node instances empty");

		int dim = instances.getFirst().getData().length;
		Double[] variance = new Double[dim];
		for (int i = 0; i < dim; i++) {
			variance[i] = 0.0;
		}

		double[] mean = new double[dim];

		for (Instance i : instances) {
			for (int d = 0; d < dim; d++) {
				mean[d] += i.getData()[d];
			}
		}

		for (int d = 0; d < dim; d++) {
			mean[d] /= instances.size();
		}

		for (Instance i : instances) {
			for (int d = 0; d < dim; d++) {
				variance[d] += Math.pow(i.getData()[d] - mean[d], 2);
			}
		}

		for (int d = 0; d < dim; d++) {
			variance[d] /= (instances.size() - (isPopulation ? 0 : 1));
		}

		return variance;
	}

	public static double mean(double[] samples) {
		double mean = 0.0;
		for (double s : samples) {
			mean += s;
		}

		return mean / samples.length;
	}

	public static double mean(int[] samples) {
		double mean = 0.0;
		for (double s : samples) {
			mean += s;
		}

		return mean / samples.length;
	}

	public static double variance(double[] samples, boolean isPopulation) {
		double mean = mean(samples);
		return variance(samples, mean, isPopulation);
	}

	public static double variance(double[] samples, double mean, boolean isPopulation) {
		double var = 0.0;
		for (double s : samples) {
			var += Math.pow(s - mean, 2);
		}
		return var / (samples.length - (isPopulation ? 0 : 1));
	}

	public static double variance(int[] samples, double mean, boolean isPopulation) {
		double var = 0.0;
		for (double s : samples) {
			var += Math.pow(s - mean, 2);
		}
		return var / (samples.length - (isPopulation ? 0 : 1));
	}

	public static double stdev(double[] samples, boolean isPopulation) {
		return Math.sqrt(variance(samples, isPopulation));
	}

	public static double stdev(double[] samples, double mean, boolean isPopulation) {
		return Math.sqrt(variance(samples, mean, isPopulation));
	}

	public static double stdev(int[] samples, double mean, boolean isPopulation) {
		return Math.sqrt(variance(samples, mean, isPopulation));
	}

	public static double[] toPrimitiveDoubles(LinkedList<Integer> array) {
		if (array == null) {
			return new double[0];
		}

		final double[] result = new double[array.size()];
		for (int i = 0; i < array.size(); i++) {
			result[i] = array.get(i).doubleValue();
		}

		return result;
	}

	public static AvgWithStdev populationMeanAndStdev(double[] samples, boolean isPopulation) {
		AvgWithStdev result = new AvgWithStdev();
		result.setAvg(mean(samples));
		result.setStdev(stdev(samples, result.getAvg(), isPopulation));

		return result;
	}
}
