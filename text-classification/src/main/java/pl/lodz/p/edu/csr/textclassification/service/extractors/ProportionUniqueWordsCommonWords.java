package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ProportionUniqueWordsCommonWords implements Extractor {


    @Override
    public Double extract(String body, ArrayList<String> uniqueWords, ArrayList<String> commonWords) {
        return (double) uniqueWords.size() / commonWords.size();
    }
}
