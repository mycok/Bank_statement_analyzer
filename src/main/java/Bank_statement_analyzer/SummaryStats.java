package Bank_statement_analyzer;

// this serves a domain object
// a domain object is simply an instance of a class that is related to your domain
// perfect encapsulation example
public class SummaryStats {
    private final double sum;
    private final double max;
    private final double min;
    private final double average;

    /**
     * 
     * @param sum
     * @param max
     * @param min
     * @param average
     */
    public SummaryStats(final double sum, final double max, final double min, final double average) {
        this.sum = sum;
        this.max = max;
        this.min = min;
        this.average = average;
    }

    public double getSum() {
        return sum;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public double getAverage() {
        return average;
    }

}