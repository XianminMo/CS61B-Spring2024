import WordNet.WordNet;
import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TestWordNet {
    @Test
    public void testLoadFile() {
        String synsetdFilename = "./data/wordnet/synsets16.txt";
        String hyponymsFilename = "./data/wordnet/hyponyms16.txt";
        WordNet wn = new WordNet(synsetdFilename, hyponymsFilename);
        Set<String> hyponyms = wn.hyponyms("change");
        Set<String> trueHyponyms = Set.of("alteration", "change", "demotion", "increase", "jump", "leap", "modification", "saltation", "transition", "variation");
        assertThat(hyponyms).isEqualTo(trueHyponyms);
    }

    @Test
    public void testHyponymsSimple(){
        WordNet wn=new WordNet("./data/wordnet/synsets11.txt","./data/wordnet/hyponyms11.txt");
        assertThat(wn.hyponyms("antihistamine")).isEqualTo(Set.of("antihistamine","actifed"));
    }
}
