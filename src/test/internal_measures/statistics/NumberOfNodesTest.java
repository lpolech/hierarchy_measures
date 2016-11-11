package test.internal_measures.statistics;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import internal_measures.statistics.AvgWithStdev;
import internal_measures.statistics.NumberOfNodes;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class NumberOfNodesTest {
    private NumberOfNodes measure;

    @Before
    public void setUp() throws Exception {
        this.measure = new NumberOfNodes();
    }

    @Test
    public void singleHierarchyCalculate() throws Exception {
        Hierarchy h = TestCommon.getTwoGroupsHierarchy();
        assertEquals(2.0, this.measure.calculate(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void multipleHierarchyCalculate() throws Exception {
        ArrayList<Hierarchy> hierarchies = new ArrayList<>();
        hierarchies.add(TestCommon.getTwoGroupsHierarchy());
        hierarchies.add(TestCommon.getFourGroupsHierarchy());

        AvgWithStdev avgResult = this.measure.calculate(hierarchies);
        assertEquals(3.0, avgResult.getAvg(), TestCommon.DOUBLE_COMPARISION_DELTA);
        assertEquals(1.0, avgResult.getStdev(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }
}