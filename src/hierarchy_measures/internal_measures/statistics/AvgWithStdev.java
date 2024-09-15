package hierarchy_measures.internal_measures.statistics;

public class AvgWithStdev {
    private double avg;
    private double stdev;

    public AvgWithStdev(double avg, double stdev)
    {
        this.avg = avg;
        this.stdev = stdev;
    }

    public AvgWithStdev()
    {
        this(0.0, 0.0);
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getStdev() {
        return stdev;
    }

    public void setStdev(double stdev) {
        this.stdev = stdev;
    }
}

