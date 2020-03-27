package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.springframework.stereotype.Component;

@Component
public class AmountUniqueWordsInPartOfArticle implements Extractor {


    @Override
    public Integer extract(String body) {
        return 0;

    }
}
