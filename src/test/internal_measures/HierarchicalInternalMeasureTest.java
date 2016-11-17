package test.internal_measures;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import distance_measures.Euclidean;
import internal_measures.FlatCalinskiHarabasz;
import internal_measures.FlatDaviesBouldin;
import internal_measures.HierarchicalInternalMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HierarchicalInternalMeasureTest {
    private HierarchicalInternalMeasure measureWithCHI;
    private HierarchicalInternalMeasure measureWithDBI;

    @Before
    public void setUp() throws Exception {
        this.measureWithCHI = new HierarchicalInternalMeasure(new FlatCalinskiHarabasz(new Euclidean()));
        this.measureWithDBI = new HierarchicalInternalMeasure(new FlatDaviesBouldin(new Euclidean()));
    }

    @Test
    public void getMeasureWithCHI() throws Exception {
        Hierarchy h = TestCommon.getFourGroupsHierarchy();
        h.printTree();
        assertEquals(1.849591145062808, this.measureWithCHI.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getDesiredValueWithCHI() throws Exception {
        assertEquals(Double.MAX_VALUE, measureWithCHI.getDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getNotDesiredValueWithCHI() throws Exception {
        assertEquals(0.0, measureWithCHI.getNotDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getMeasureWithDBI() throws Exception {
        Hierarchy h = TestCommon.getFourGroupsHierarchy();
        assertEquals(0.25, this.measureWithDBI.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getDesiredValueWithDBI() throws Exception {
        assertEquals(0.0, measureWithDBI.getDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getNotDesiredValueWithDBI() throws Exception {
        assertEquals(Double.MAX_VALUE, measureWithDBI.getNotDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }
}