package main;

import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import wordnet.WordNet;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
        WordNet wn = new WordNet(synsetFile, hyponymFile);
        NGramMap ngm = new NGramMap(wordFile, countFile);

        return new HyponymsHandler(wn, ngm);
    }
}
