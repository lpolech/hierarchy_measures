package test.internal_measures;

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

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateMeasureObject()
    {
        assertTrue(measure != null);
    }
}
