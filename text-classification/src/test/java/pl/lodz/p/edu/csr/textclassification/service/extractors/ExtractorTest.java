package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ExtractorTest {

    Extractor extractor = new ProportionUniqueKeywordsInPartOfArticle();
    TextProcessor textProcessor = new TextProcessor();

    ExtractorTest() throws IOException {
    }

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
    void getOnlyUniqueWords() {
        String rawText = "Test test test. cat, dog, sun moon sun. Test two, three cat and dogs.";
        List<String> toTest = textProcessor.prepare(rawText); // after stopList only 'moon' is correct result
        List<String> expected = Collections.singletonList("moon");
        List<String> actual = extractor.getOnlyUniqueWords(toTest);
        assertEquals(expected, actual);
    }

    @Test
    void getOnlyCommonWords() {
        String rawText = "Test. cat, dog. Test test two, three cat and dogs.";
        List<String> toTest = textProcessor.prepare(rawText);
        List<String> expected = Stream.of("Test", "Test", "test", "cat", "dog", "cat", "dog")
                .map(String::toLowerCase).sorted()
                .collect(Collectors.toList());
        List<String> actual = extractor.getOnlyCommonWords(toTest);
        assertEquals(expected, actual);
    }

    @Test
    void amountUniqueWords() {
    }

    @Test
    void amountCommonWords() {
    }
}