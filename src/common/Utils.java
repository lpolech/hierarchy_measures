package common;

import basic_hierarchy.interfaces.Instance;
import basic_hierarchy.interfaces.Node;

import java.util.ArrayList;

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
        return (baseClass + basic_hierarchy.common.Constants.HIERARCHY_BRANCH_SEPARATOR)
                .startsWith(queryClass +basic_hierarchy.common.Constants.HIERARCHY_BRANCH_SEPARATOR);
    }
}
