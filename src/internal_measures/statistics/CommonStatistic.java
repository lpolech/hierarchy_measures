package internal_measures.statistics;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Node;
import common.Utils;

import java.util.ArrayList;

public abstract class CommonStatistic {

    public abstract double calculate(Hierarchy hierarchy);

    protected abstract int rec(Node n, int value);

    public AvgWithStdev calculate(ArrayList<Hierarchy> hierarchies)
    {
        double[] values = new double[hierarchies.size()];
        for(int i = 0; i < hierarchies.size(); i++)
        {
            values[i] = this.calculate(hierarchies.get(i));
        }

        return new AvgWithStdev(Utils.populationMean(values), Utils.populationStdev(values));
    }
}
