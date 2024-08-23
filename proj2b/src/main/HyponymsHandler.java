package main;

import wordnet.WordNet;
import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wn;

    public HyponymsHandler(WordNet wn) {
        this.wn = wn;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        Set<String> hyponyms0 = wn.hyponyms(words.get(0));
        if (words.size() == 2) {
            Set<String> hyponyms1 = wn.hyponyms(words.get(1));
            hyponyms0.retainAll(hyponyms1);
        }
        return convertSetToSortedString(hyponyms0);
    }

    public static String convertSetToSortedString(Set<String> set) {
        List<String> sortedList = new ArrayList<>(set);
        Collections.sort(sortedList);
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (String word : sortedList) {
            joiner.add(word);
        }
        return joiner.toString();
    }
}
