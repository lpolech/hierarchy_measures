package hierarchy_measures.distance_measures;

import basic_hierarchy.interfaces.Instance;
import hierarchy_measures.interfaces.DistanceMeasure;

public class Euclidean implements DistanceMeasure {
    @Override
    public double getDistance(Instance i1, Instance i2) {
        double[] i1Data = i1.getData();
        double[] i2Data = i2.getData();
        assert i1Data.length == i2Data.length;

        double measure = 0.0;
        for(int i = 0; i < i1Data.length; i++)
        {
            measure += Math.pow(i1Data[i] - i2Data[i], 2);
        }

        return Math.sqrt(measure);
    }
}
