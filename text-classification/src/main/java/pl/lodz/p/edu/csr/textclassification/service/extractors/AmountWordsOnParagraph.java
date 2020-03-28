package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class AmountWordsOnParagraph implements Extractor {

    private static final String PARAGRAPH_SPLIT_REGEX = "\n\n";

    @Override
    public Double extract(String body, ArrayList<String> uniqueWords, ArrayList<String> commonWords) {

        ArrayList<String> paragraphs = new ArrayList<>(Arrays.asList(body.split(PARAGRAPH_SPLIT_REGEX)));
        ArrayList<Integer> sumWordsInParagraphs = new ArrayList<>();

        paragraphs.forEach(paragraph -> {
            ArrayList<String> wordsOnParagraph = new ArrayList<>(Arrays.asList(paragraph.split(" ")));
            sumWordsInParagraphs.add(wordsOnParagraph.size());
        });
        return (double) sumWordsInParagraphs.stream().reduce(0, Integer::sum) / sumWordsInParagraphs.size();
    }
}
