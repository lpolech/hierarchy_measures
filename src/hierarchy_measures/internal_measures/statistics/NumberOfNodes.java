package hierarchy_measures.internal_measures.statistics;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Node;

public class NumberOfNodes extends CommonStatistic {

    @Override
    public double calculate(Hierarchy hierarchy)
    {
        return hierarchy.getNumberOfGroups();
    }

    @Override
    protected int rec(Node n, int value) {
        return 0;
    }

}
