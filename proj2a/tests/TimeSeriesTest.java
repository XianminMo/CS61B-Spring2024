import ngrams.TimeSeries;

import org.dom4j.tree.BaseElement;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }

    @Test
    public void testConstructorAndYear() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);
        catPopulation.put(1995, 300.0);
        catPopulation.put(1996, 400.0);

        TimeSeries dogPopulation = new TimeSeries(catPopulation, 1992, 1995);

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1992, 1994, 1995));

        assertThat(dogPopulation.years()).isEqualTo(expectedYears);
    }

    @Test
    public void testData() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);
        catPopulation.put(1995, 300.0);
        catPopulation.put(1996, 400.0);

        List<Double> expectedData = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 200.0, 300.0, 400.0));

        for (int i = 0; i < expectedData.size(); i += 1) {
            assertThat(catPopulation.data().get(i)).isWithin(1E-10).of(expectedData.get(i));
        }
    }

    @Test
    public void testDividedBy() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);
        catPopulation.put(1993, 200.0); // test missing year
        catPopulation.put(1995, 300.0);
        catPopulation.put(1996, 400.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1991, 1.0);
        dogPopulation.put(1992, 50.0);
        dogPopulation.put(1994, 100.0);
        dogPopulation.put(1995, 150.0);
        dogPopulation.put(1996, 200.0);
        dogPopulation.put(1997, 1000.0);

        TimeSeries dividedPopulation = catPopulation.dividedBy(dogPopulation);

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995, 1996));

        assertThat(dividedPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedData = new ArrayList<>
                (Arrays.asList(0.0, 2.0, 2.0, 2.0, 2.0));

        for (int i = 0; i < expectedData.size(); i += 1) {
            assertThat(dividedPopulation.data().get(i)).isWithin(1E-10).of(expectedData.get(i));
        }
    }
} 