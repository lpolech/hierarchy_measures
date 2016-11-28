package test.internal_measures.statistics;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import internal_measures.statistics.AvgWithStdev;
import internal_measures.statistics.NumberOfLeaves;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class NumberOfLeavesTest {
    private NumberOfLeaves measure;

    @Before
    public void setUp() throws Exception {
        this.measure = new NumberOfLeaves();
    }

    @Test
    public void singleHierarchyCalculate() throws Exception {
        Hierarchy h = TestCommon.getTwoGroupsHierarchy();
        assertEquals(1.0, this.measure.calculate(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void testGetMeasureForHierarchyWithEmptyNodes()
    {
        Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
        assertEquals(2.0, this.measure.calculate(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void multipleHierarchyCalculate() throws Exception {
        ArrayList<Hierarchy> hierarchies = new ArrayList<>();
        hierarchies.add(TestCommon.getTwoGroupsHierarchy());
        hierarchies.add(TestCommon.getFourGroupsHierarchy());

        AvgWithStdev avgResult = this.measure.calculate(hierarchies, true);
        assertEquals(1.5, avgResult.getAvg(), TestCommon.DOUBLE_COMPARISION_DELTA);
        assertEquals(0.5, avgResult.getStdev(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

}