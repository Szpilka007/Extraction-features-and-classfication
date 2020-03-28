package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class SumCommonWordsDivideAmountWords implements Extractor {

    @Override
    public Double extract(String body, ArrayList<String> uniqueWords, ArrayList<String> commonWords) {
        ArrayList<String> listOfWords = new ArrayList<>(Arrays.asList(body.split(" ")));
        return (double) (commonWords.size() / listOfWords.size());
    }
}
