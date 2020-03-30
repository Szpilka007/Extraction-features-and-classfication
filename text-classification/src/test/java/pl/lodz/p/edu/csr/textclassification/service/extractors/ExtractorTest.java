package pl.lodz.p.edu.csr.textclassification.service.extractors;

import opennlp.tools.lemmatizer.Lemmatizer;
import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.stemmer.PorterStemmer;
import opennlp.tools.tokenize.SimpleTokenizer;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExtractorTest {



//    @Test
//    void getOnlyUniqueWordsFromTextWhenCharSizeNotMatter() {
//        String rawText = "Cat cat kid, test and a lot of tests.";
//        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(tokenizer.tokenize(rawText)));
//        System.out.println(tokens.toString());
//        ArrayList<String> expected = new ArrayList<>(Arrays.asList("kid", "test"));
//        System.out.println(porterStemmer.stem("tests"));
//    }

    @Test
    void tokenizeExampleSentences() {
        String rawText = "Test test test. cat, dog, sun moon sun. Test two, three cat and dogs.";
        ArrayList<String> expected = new ArrayList<>(
                Arrays.asList("Test", "test", "test", ".", "cat", ",", "dog", ",", "sun", "moon",
                "sun", ".", "Test", "two", ",", "three", "cat", "and", "dogs", "."));
        List<String> actual = Extractor.tokenize(rawText);
        assertEquals(expected, actual);
    }

    @Test
    void getOnlyCommonWords() {
    }

    @Test
    void amountUniqueWords() {
    }

    @Test
    void amountCommonWords() {
    }
}