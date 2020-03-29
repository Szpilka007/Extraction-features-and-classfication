package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AmountUniqueWordsInPartOfArticle implements Extractor {

    private double percentOfArticle = 0.20;

    @Override
    public Double extract(String body, ArrayList<String> uniqueWords, ArrayList<String> commonWords) {

        String[] listOfWords = body.split(" ");
        int listOfUniqueWords = 0;
        for(int i=0; i < percentOfArticle * listOfWords.length; i++){
            for (String uniqueWord : uniqueWords) {
                if (uniqueWord.equals(listOfWords[i])) {
                    listOfUniqueWords++;
                }
            }
        }

        for(int i= (int)(listOfWords.length - percentOfArticle * listOfWords.length); i <listOfWords.length; i++){
            for (String uniqueWord : uniqueWords) {
                if (uniqueWord.equals(listOfWords[i])) {
                    listOfUniqueWords++;
                }
            }
        }

        return (double) listOfUniqueWords;

    }

}
