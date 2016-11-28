package test.external_measures.statistical_hypothesis;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import external_measures.statistical_hypothesis.FlatHypotheses;
import external_measures.statistical_hypothesis.Fmeasure;
import external_measures.statistical_hypothesis.PartialOrderHypotheses;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FmeasureTest {
    private Fmeasure flatFmeasure;
    private Fmeasure POFmeasure;

    @Before
    public void setUp()
    {
        flatFmeasure = new Fmeasure(1.0f, new FlatHypotheses());
        POFmeasure = new Fmeasure(1.0f, new PartialOrderHypotheses());
    }

    @Test
    public void calculateFlatFmeasure()
    {
        Hierarchy h = TestCommon.getTwoGroupsHierarchy();
        assertEquals(0.4, flatFmeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void calculateFlatFmeasureForHierarchyWithEmptyNodes()
    {
        Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
        assertEquals(0.4, this.flatFmeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void calculatePOFmeasure()
    {
        Hierarchy h = TestCommon.getTwoGroupsHierarchy();
        assertEquals(0.823529412, POFmeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void calculatePOFmeasureForHierarchyWithEmptyNodes()
    {
        Hierarchy h = TestCommon.getTwoGroupsHierarchyWithEmptyNodes();
        assertEquals(0.823529412, this.POFmeasure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
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