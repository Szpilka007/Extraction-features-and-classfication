package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProportionUniqueKeywordsInPartOfArticle implements Extractor {

    private double percentOfArticle = 0.20;

    @Override
    public Double extract(List<String> textToExtract) {
        int numberOfWordsIncluded = (int) (percentOfArticle * textToExtract.size());
        Double quantity = Double.valueOf(this.amountOfUniqueWords(textToExtract
                .stream()
                .limit(numberOfWordsIncluded)
                .collect(Collectors.toList())));

        return quantity / (textToExtract.size() * percentOfArticle);
    }
}
