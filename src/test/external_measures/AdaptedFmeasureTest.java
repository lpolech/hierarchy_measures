package test.external_measures;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import external_measures.AdaptedFmeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdaptedFmeasureTest {
    private AdaptedFmeasure withOUTInheritanceMeasure;
    private AdaptedFmeasure withInheritanceMeasure;

    @Before
    public void setUp()
    {
        withOUTInheritanceMeasure = new AdaptedFmeasure(false);
        withInheritanceMeasure = new AdaptedFmeasure(true);
    }

    @Test
    public void getMeasureWithInheritance() throws Exception {
        Hierarchy h = TestCommon.getTwoGroupsHierarchy();
        assertEquals(0.9333333333333333, withInheritanceMeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getMeasureWithOUTInheritance() throws Exception {
        Hierarchy h = TestCommon.getTwoGroupsHierarchy();
        assertEquals(0.8095238095238094, withOUTInheritanceMeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getDesiredValue() throws Exception {
        assertEquals(1.0, withOUTInheritanceMeasure.getDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
        assertEquals(1.0, withInheritanceMeasure.getDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getNotDesiredValue() throws Exception {
        assertEquals(0.0, withOUTInheritanceMeasure.getNotDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
        assertEquals(0.0, withInheritanceMeasure.getNotDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

}