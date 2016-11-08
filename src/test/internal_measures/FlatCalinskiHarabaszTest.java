package test.internal_measures;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import distance_measures.Euclidean;
import internal_measures.FlatCalinskiHarabasz;
import org.junit.*;

import static org.junit.Assert.*;

public class FlatCalinskiHarabaszTest {
    private FlatCalinskiHarabasz measure;

    @Before
    public void setUp() throws Exception {
        measure = new FlatCalinskiHarabasz(new Euclidean());
    }

    @Test
    public void testCalculateMeasureForTwoGroupHierarchy()
    {
        Hierarchy h = TestCommon.getTwoGroupsHierarchy();
        assertEquals(0.125, this.measure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
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
