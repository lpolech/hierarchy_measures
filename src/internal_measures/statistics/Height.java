package internal_measures.statistics;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.interfaces.Node;

public class Height extends CommonStatistic {

    @Override
    public double calculate(Hierarchy hierarchy)
    {
        return rec(hierarchy.getRoot(), -1);
    }

    @Override
    protected int rec(Node n, int height)
    {
        height = height + 1;
        int maxHeight = height;
        for (Node child : n.getChildren()) {
            maxHeight = Math.max(rec(child, height), maxHeight);
        }
        return maxHeight;
    }
}
