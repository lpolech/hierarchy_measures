package test.external_measures.purity;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import external_measures.purity.HierarchicalClassPurity;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HierarchicalClassPurityTest {
    private HierarchicalClassPurity measure;

    @Before
    public void setUp()
    {
        this.measure = new HierarchicalClassPurity();
    }

    @Test
    public void getMeasure() throws Exception {
        Hierarchy h = TestCommon.getTwoGroupsHierarchy();
        assertEquals(0.75, measure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getDesiredValue() throws Exception {
        assertEquals(1.0, measure.getDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getNotDesiredValue() throws Exception {
        assertEquals(0.06, measure.getNotDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }
}