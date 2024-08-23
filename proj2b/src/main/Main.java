package main;

import wordnet.WordNet;
import browser.NgordnetServer;
import edu.berkeley.eecs.inst.cs61b.ngrams.StaffNGramMap;
import ngrams.NGramMap;
import org.slf4j.LoggerFactory;

public class Main {
    static {
        LoggerFactory.getLogger(Main.class).info("\033[1;38mChanging text color to white");
    }
    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();

        String wordsFile = "./data/ngrams/top_49887_words.csv";
        String countsFlie = "./data/ngrams/total_counts.csv";

        String synsetFile = "./data/wordnet/synsets.txt";
        String hyponymFile = "./data/wordnet/hyponyms.txt";

        NGramMap ngm = new NGramMap(wordsFile, countsFlie);
        WordNet wn = new WordNet(synsetFile, hyponymFile);

        hns.startUp();
        hns.register("history", new HistoryHandler(ngm));
        hns.register("historytext", new HistoryTextHandler(ngm));
        hns.register("hyponyms", new HyponymsHandler(wn));

        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet.html");

    }
}