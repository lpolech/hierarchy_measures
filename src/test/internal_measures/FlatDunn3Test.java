package test.internal_measures;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import distance_measures.Euclidean;
import internal_measures.FlatDunn3;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FlatDunn3Test {
    private FlatDunn3 measure;

    @Before
    public void setUp() throws Exception {
        this.measure = new FlatDunn3(new Euclidean());
    }

    @Test
    public void getMeasure() throws Exception {
        Hierarchy h = TestCommon.getTwoGroupsHierarchy();
        assertEquals(0.25, this.measure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getDesiredValue() throws Exception {
        assertEquals(Double.MAX_VALUE, measure.getDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getNotDesiredValue() throws Exception {
        assertEquals(0.0, measure.getNotDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }
}