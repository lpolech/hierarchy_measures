package internal_measures.statistics;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Node;
import common.Utils;

import java.util.ArrayList;
import java.util.LinkedList;

public class AvgPathLength {

    public AvgWithStdev calculate(Hierarchy hierarchy)
    {
        LinkedList<Integer> result = rec(hierarchy.getRoot(), -1);
        return Utils.populationMeanAndStdev(Utils.toPrimitiveDoubles(result), true);
    }

    public AvgWithStdev calculate(ArrayList<Hierarchy> hierarchies, boolean isPopulation)
    {
        double[] values = new double[hierarchies.size()];
        for(int i = 0; i < hierarchies.size(); i++)
        {
            values[i] = this.calculate(hierarchies.get(i)).getAvg();
        }

        return new AvgWithStdev(Utils.mean(values), Utils.stdev(values, isPopulation));
    }

    private LinkedList<Integer> rec(Node n, int height)
    {
        LinkedList<Integer> pathLengths = new LinkedList<>();
        height += 1;
        for (Node child : n.getChildren())
        {
            pathLengths.addAll(rec(child, height));
        }

        if(n.getChildren().isEmpty())
        {
            pathLengths.add(height);
        }

        return pathLengths;
    }

}
