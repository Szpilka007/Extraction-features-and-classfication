package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class AmountWordsWithTheNotOnStart implements Extractor {


    @Override
    public Integer extract(String body) {

        Integer amountOwnNames = 0;

        ArrayList<String> listOfWords = new ArrayList<>(Arrays.asList(body.split(" ")));

        for (int i = 0; i < listOfWords.size(); i++) {
            if (listOfWords.get(i).equals("The") || listOfWords.get(i).equals("the")) {
                String testObject = listOfWords.get(i + 1);
                if (testObject.charAt(0) >= 65 && testObject.charAt(0) <= 90) {
                    amountOwnNames++;
                }
            }
        }
        return amountOwnNames;
    }

}
