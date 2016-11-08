package test.external_measures.statistical_hypothesis;

import basic_hierarchy.interfaces.Hierarchy;
import basic_hierarchy.test.TestCommon;
import external_measures.information_based.FlatEntropy1;
import external_measures.information_based.FlatInformationGain;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FlatInformationGainTest {
    private FlatInformationGain measure;

    @Before
    public void setUp() throws Exception {
        this.measure = new FlatInformationGain(2.0, new FlatEntropy1(2.0));
    }

    @Test
    public void getMeasure() throws Exception {
        Hierarchy h = TestCommon.getTwoGroupsHierarchy();
        assertEquals(0.8112781244591328 - 0.5, measure.getMeasure(h), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getDesiredValue() throws Exception {
        assertEquals(1234, measure.getDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }

    @Test
    public void getNotDesiredValue() throws Exception {
        assertEquals(3245, measure.getNotDesiredValue(), TestCommon.DOUBLE_COMPARISION_DELTA);
    }
}