package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class SumCommonWordsDivideSentence implements Extractor {


    @Override
    public Double extract(String body, ArrayList<String> uniqueWords, ArrayList<String> commonWords) {

        ArrayList<String> sentences = new ArrayList<>(Arrays.asList(body.split(".")));
        ArrayList<String> wordsInText = new ArrayList<>(Arrays.asList(body.split(" ")));
        final int[] commonWordsInText = {0};

        wordsInText.forEach(word ->{
            commonWords.forEach(commonWord -> {
                if(word.equals(commonWord)){
                    commonWordsInText[0]++;
                }
            });
        });

        return (double) commonWordsInText[0] / sentences.size();


    }
}
