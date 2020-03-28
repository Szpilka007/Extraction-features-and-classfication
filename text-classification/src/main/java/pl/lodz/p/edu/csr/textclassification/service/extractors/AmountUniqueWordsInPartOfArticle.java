package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
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
