package ngrams;
import edu.princeton.cs.algs4.In;

import java.sql.Time;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */

public class NGramMap {

    private static class WordFrequency {
        private final Map<String, TimeSeries> wordToYearCount;

        private WordFrequency() {
            this.wordToYearCount = new HashMap<>();
        }

        private void addWordCount(String word, int year, double count) {
            wordToYearCount.computeIfAbsent(word, k -> new TimeSeries()).put(year, count); // if the word does not exist, create a new TimeSeries which is empty(Lambda expression), then put the key and the value
        }

        private TimeSeries getWordCount(String word) {
            TimeSeries original = wordToYearCount.getOrDefault(word, new TimeSeries()); // if the word has already existed, return its value, or return a new TimeSeries which is empty
            if (original.isEmpty()) {
                return new TimeSeries();
            }else {
                int startYear = original.firstKey();
                int endYear = original.lastKey();
                return new TimeSeries(original, startYear, endYear);  // defensive copy
            }
        }


    }

    private WordFrequency wordFrequency;
    private TimeSeries totalCounts;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        this.wordFrequency = new WordFrequency();
        this.totalCounts = new TimeSeries();


    }

    private WordFrequency loadWordsFile(String wordsFilename, WordFrequency wordFrequency) {
          return null;
    }

    private TimeSeries loadCountsFile(String countsFilename, TimeSeries totalCounts) {
        In in = new In(countsFilename);
        int i = 0;

        while (!in.isEmpty()) {
            i += 1;
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            int year = Integer.parseInt(splitLine[0]);
            double count = Double.parseDouble(splitLine[1]);
            totalCounts.put(year, count);
        }
        return totalCounts;
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        return null;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        return null;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this m ethod.
        return null;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        return null;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        return null;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        return null;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        return null;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
