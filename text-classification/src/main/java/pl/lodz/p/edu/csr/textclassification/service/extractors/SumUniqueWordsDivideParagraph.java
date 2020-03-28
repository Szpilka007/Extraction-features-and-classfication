package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class SumUniqueWordsDivideParagraph implements Extractor {

    private static final String PARAGRAPH_SPLIT_REGEX = "\n\n";

    @Override
    public Double extract(String body, ArrayList<String> uniqueWords, ArrayList<String> commonWords) {

        ArrayList<String> paragraphs = new ArrayList<>(Arrays.asList(body.split(PARAGRAPH_SPLIT_REGEX)));
        System.out.println(paragraphs.size());
        ArrayList<Double> averages = new ArrayList<>();
        paragraphs.forEach(sentence -> {
            int listOfUniqueWordsInText = 0;
            ArrayList<String> listOfWords = new ArrayList<>(Arrays.asList(sentence.split(" ")));
            for (String uniqueWord : uniqueWords) {
                for (String listOfWord : listOfWords) {
                    if (uniqueWord.equals(listOfWord)) {
                        listOfUniqueWordsInText++;
                    }
                }
            }
            averages.add((double) listOfUniqueWordsInText / listOfWords.size());
        });

        return averages.stream().reduce(0.0, Double::sum) / averages.size();

    }

}
