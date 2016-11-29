package test.internal_measures;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import distance_measures.Euclidean;
import internal_measures.FlatReversedDunn2;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FlatReversedDunn2Test {
    private FlatReversedDunn2 measure;

    @Before
    public void setUp() throws Exception {
        this.measure = new FlatReversedDunn2(new Euclidean());
    }

    @Test
    public void getMeasure() throws Exception {
        Hierarchy h = TestCommon.getTwoGroupsHierarchy();
        assertEquals(4, this.measure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void testGetMeasureForHierarchyWithEmptyNodes()
    {
        Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
        assertEquals(4, this.measure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getDesiredValue() throws Exception {
        assertEquals(0.0, measure.getDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getNotDesiredValue() throws Exception {
        assertEquals(Double.MAX_VALUE, measure.getNotDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }
}