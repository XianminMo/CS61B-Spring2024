import WordNet.WordNet;
import org.junit.Test;

public class testWordNet {
    @Test
    public void testLoadFile() {
        String synsetdFilename = "./data/wordnet/synsets16.txt";
        String hyponymsFilename = "./data/wordnet/hyponyms16.txt";
        WordNet wn = new WordNet(synsetdFilename, hyponymsFilename);
        System.out.println("hello!");
    }
}
