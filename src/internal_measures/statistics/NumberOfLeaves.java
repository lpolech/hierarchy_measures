package internal_measures.statistics;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Node;

public class NumberOfLeaves extends CommonStatistic {

    @Override
    public double calculate(Hierarchy hierarchy)
    {
        return rec(hierarchy.getRoot(), 0);
    }

    @Override
    protected int rec(Node n, int numberOfLeaves)
    {
        if(n.getChildren().isEmpty())
        {
            numberOfLeaves += 1;
        }

        for (Node child : n.getChildren())
        {
            numberOfLeaves += rec(child, 0);
        }

        return numberOfLeaves;
    }
}
