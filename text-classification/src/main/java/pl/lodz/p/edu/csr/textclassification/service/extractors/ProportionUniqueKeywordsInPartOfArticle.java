package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProportionUniqueKeywordsInPartOfArticle implements Extractor {

    private double percentOfArticle = 0.20;

    @Override
    public Double extract(List<String> keywords) {
        int numberOfWordsIncluded = (int) (percentOfArticle * keywords.size());
        Double quantity = Double.valueOf(this.amountOfUniqueWords(keywords
                .stream()
                .limit(numberOfWordsIncluded)
                .collect(Collectors.toList())));

        return quantity / (keywords.size() * percentOfArticle);
    }
}
