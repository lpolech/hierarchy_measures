package test.internal_measures;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import internal_measures.VarianceDeviation2;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VarianceDeviation2Test {
    private VarianceDeviation2 measure;

    @Before
    public void setUp() throws Exception {
        measure = new VarianceDeviation2();
    }

    @Test
    public void getMeasure() throws Exception {
        Hierarchy h = TestCommon.getTwoGroupsHierarchy();
        assertEquals(1.0, this.measure.getMeasure(h), 0.1);
    }

    
    @Test
    public void getDesiredValue() throws Exception {
        assertEquals(Double.NaN, measure.getDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getNotDesiredValue() throws Exception {
        assertEquals(Double.MAX_VALUE, measure.getNotDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }
}