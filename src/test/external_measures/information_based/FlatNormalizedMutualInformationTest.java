package test.external_measures.information_based;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import external_measures.information_based.FlatNormalizedMutualInformation;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FlatNormalizedMutualInformationTest {
    private FlatNormalizedMutualInformation measure;

    @Before
    public void setUp() throws Exception {
        this.measure = new FlatNormalizedMutualInformation(2.0);
    }

    @Test
    public void getMeasure() throws Exception {
        Hierarchy h = TestCommon.getTwoGroupsHierarchy();
        assertEquals(0.3437110184854507, measure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getDesiredValue() throws Exception {
        assertEquals(1.0, measure.getDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getNotDesiredValue() throws Exception {
        assertEquals(0.0, measure.getNotDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }
}