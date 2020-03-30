package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ProportionUniqueWordsInPartOfArticle implements Extractor {

    private double percentOfArticle = 0.20;

    @Override
    public Double extract(String text) {

        List<String> listOfWords = Extractor.tokenize(text);
        int numberOfWordsIncluded = (int) (percentOfArticle * listOfWords.size());
        return 0.0;
//        return (double) listOfWords.stream().limit(numberOfWordsIncluded).filter(uniqueWords::contains).count();

    }

}
