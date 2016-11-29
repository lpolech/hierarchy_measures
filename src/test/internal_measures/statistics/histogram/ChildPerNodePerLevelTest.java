package test.internal_measures.statistics.histogram;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import internal_measures.statistics.AvgWithStdev;
import internal_measures.statistics.histogram.ChildPerNodePerLevel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ChildPerNodePerLevelTest {
    private ChildPerNodePerLevel measure;

    @Before
    public void setUp() throws Exception {
        this.measure = new ChildPerNodePerLevel();
    }

    @Test
    public void singleHierarchyCalculate() throws Exception {
        Hierarchy h = TestCommon.getFourGroupsHierarchy();
        assertArrayEquals(new double[]{2, 0.5, 0}, this.measure.calculate(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void testGetMeasureForHierarchyWithEmptyNodes()
    {
        Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
        assertArrayEquals(new double[]{1.0, 1.0, 1.0, 0.0}, this.measure.calculate(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void multipleHierarchyCalculate() throws Exception {
        ArrayList<Hierarchy> hierarchies = new ArrayList<>();
        hierarchies.add(TestCommon.getTwoGroupsHierarchy());
        hierarchies.add(TestCommon.getFourGroupsHierarchy());

        AvgWithStdev[] perLevelAvgResult = this.measure.calculate(hierarchies, true);
        assertEquals(3, perLevelAvgResult.length);
        assertEquals(1.5, perLevelAvgResult[0].getAvg(), TestCommon.DOUBLE_COMPARISION_DELTA);
        assertEquals(0.5, perLevelAvgResult[0].getStdev(), TestCommon.DOUBLE_COMPARISION_DELTA);
        assertEquals(0.25, perLevelAvgResult[1].getAvg(), TestCommon.DOUBLE_COMPARISION_DELTA);
        assertEquals(0.25, perLevelAvgResult[1].getStdev(), TestCommon.DOUBLE_COMPARISION_DELTA);
        assertEquals(0.0, perLevelAvgResult[2].getAvg(), TestCommon.DOUBLE_COMPARISION_DELTA);
        assertEquals(0.0, perLevelAvgResult[2].getStdev(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }
}