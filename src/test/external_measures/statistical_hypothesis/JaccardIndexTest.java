package test.external_measures.statistical_hypothesis;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import external_measures.statistical_hypothesis.FlatHypotheses;
import external_measures.statistical_hypothesis.JaccardIndex;
import external_measures.statistical_hypothesis.PartialOrderHypotheses;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JaccardIndexTest {
    private JaccardIndex flatFmeasure;
    private JaccardIndex POFmeasure;

    @Before
    public void setUp() throws Exception {
        flatFmeasure = new JaccardIndex(new FlatHypotheses());
        POFmeasure = new JaccardIndex(new PartialOrderHypotheses());
    }

    @Test
    public void getFlatMeasure() throws Exception {
        Hierarchy h = TestCommon.getTwoGroupsHierarchy();
        assertEquals(0.25, flatFmeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getFlatMeasureForHierarchyWithEmptyNodes()
    {
        Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
        assertEquals(0.25, this.flatFmeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getPOMeasure() throws Exception {
        Hierarchy h = TestCommon.getTwoGroupsHierarchy();
        assertEquals(0.7, POFmeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getPOMeasureForHierarchyWithEmptyNodes()
    {
        Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
        assertEquals(0.7, this.POFmeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getDesiredValue() throws Exception {
        assertEquals(1.0, flatFmeasure.getDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
        assertEquals(1.0, POFmeasure.getDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getNotDesiredValue() throws Exception {
        assertEquals(0.0, flatFmeasure.getNotDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
        assertEquals(0.0, POFmeasure.getNotDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }
}