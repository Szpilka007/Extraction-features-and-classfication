package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class SumUniqueWordsDivideSentence implements Extractor {


    @Override
    public Double extract(String body, ArrayList<String> uniqueWords, ArrayList<String> commonWords) {

        ArrayList<String> sentences = new ArrayList<>(Arrays.asList(body.split(".")));
        ArrayList<Double> averages = new ArrayList<>();
        sentences.forEach(sentence -> {
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
