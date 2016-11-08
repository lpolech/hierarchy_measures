package test.external_measures.statistical_hypothesis;

import basic_hierarchy.interfaces.Hierarchy;
import external_measures.statistical_hypothesis.PartialOrderHypotheses;
import org.junit.Test;
import basic_hierarchy.test.TestCommon;

import static org.junit.Assert.*;

public class PartialOrderHypothesesTest {
    @Test
    public void calculate() throws Exception {
        Hierarchy h = TestCommon.getTwoGroupsHierarchy();
        PartialOrderHypotheses hypotheses = new PartialOrderHypotheses();
        hypotheses.calculate(h);
        assertEquals(7, hypotheses.getTP());
        assertEquals(2, hypotheses.getFN());
        assertEquals(1, hypotheses.getFP());
        assertEquals(2, hypotheses.getTN());
    }

}