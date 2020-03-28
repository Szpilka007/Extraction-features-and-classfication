package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class SumCommonWordsDivideSentence implements Extractor {


    @Override
    public Double extract(String body, ArrayList<String> uniqueWords, ArrayList<String> commonWords) {

        ArrayList<Double> averages = new ArrayList<>();
        ArrayList<String> sentences = new ArrayList<>(Arrays.asList(body.split(".")));

        sentences.forEach(sentence -> {
            int listOfCommonWordsInText = 0;
            ArrayList<String> listOfWords = new ArrayList<>(Arrays.asList(sentence.split(" ")));
            for (String commonWord : commonWords) {
                for (String listOfWord : listOfWords) {
                    if (commonWord.equals(listOfWord)) {
                        listOfCommonWordsInText++;
                    }
                }
            }
            averages.add((double) listOfCommonWordsInText / listOfWords.size());
        });

        return averages.stream().reduce(0.0, Double::sum) / averages.size();

    }
}
