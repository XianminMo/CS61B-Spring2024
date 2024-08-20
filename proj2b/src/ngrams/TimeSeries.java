package ngrams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        Map<Integer, Double> subMap = ts.subMap(startYear, endYear + 1); // endYear isn't inclusive, so plus 1
        this.putAll(subMap);
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        return new ArrayList<>(this.keySet());
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        List<Double> returnList = new ArrayList<>();
        for (Integer key : this.keySet()) {
            returnList.add(this.get(key));
        }
        return returnList;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries returnTs = new TimeSeries();
        for (Integer key : this.keySet()) {
            if (ts.containsKey(key)) {
                returnTs.put(key, this.get(key) + ts.get(key));
            }
            else {
                returnTs.put(key, this.get(key));
            }
        }
        for (Integer key : ts.keySet()) {
            if (!this.containsKey(key)) {
                returnTs.put(key, ts.get(key));
            }
        }
        return returnTs;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries returnTs = new TimeSeries();
        for (Integer key : this.keySet()) {
            if (!ts.containsKey(key)) {
                throw new IllegalArgumentException("Key " + key + " is not present in the provided TimeSeries");
            }
            Double divisor = ts.get(key);
            if (divisor == 0) {
                throw new IllegalArgumentException("Division by zero for year " + key);
            }
            returnTs.put(key, this.get(key) / divisor);
        }
        return returnTs;
    }
}
