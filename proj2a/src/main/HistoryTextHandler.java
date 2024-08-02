package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {

    private final NGramMap Map;

    public HistoryTextHandler(NGramMap Map) {
        this.Map = Map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        StringBuilder response = new StringBuilder();

        for (String word : words) {
            TimeSeries weightHistory = Map.weightHistory(word, startYear, endYear);
            if (weightHistory.isEmpty()) {
                continue;
            }
            response.append(word).append(": ").append(weightHistory.toString()).append("\n");
        }

        return response.toString();
    }
}
