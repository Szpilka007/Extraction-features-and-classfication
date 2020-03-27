package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.springframework.stereotype.Component;

@Component
public class AmountWordsOnParagraph implements Extractor {


    @Override
    public Integer extract(String body) {
        return 0;
    }
}
