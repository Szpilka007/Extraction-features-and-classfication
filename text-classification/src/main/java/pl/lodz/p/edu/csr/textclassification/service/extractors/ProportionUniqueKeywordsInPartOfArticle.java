package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProportionUniqueKeywordsInPartOfArticle implements Extractor {

    private final double percentOfArticle = 0.20;
    private TextProcessor textProcessor;

    @Autowired
    ProportionUniqueKeywordsInPartOfArticle(TextProcessor textProcessor) {
        this.textProcessor = textProcessor;
    }

    @Override
    public FeatureEntity extract(ReutersEntity reuters) {
        String fullText = StringUtils.normalizeSpace(reuters.getBody()); // skipping paragraphs
        List<String> keywords = textProcessor.prepare(fullText); // text to keywords
        int numberOfWordsIncluded = (int) (percentOfArticle * keywords.size());
        Double quantity = Double.valueOf(this.amountOfUniqueWords(keywords
                .stream()
                .limit(numberOfWordsIncluded)
                .collect(Collectors.toList())));
        return FeatureEntity
                .builder()
                .value(quantity / (keywords.size() * percentOfArticle))
                .featureType(FeatureType.PUKIPOA)
                .build();
    }

    @Override
    public FeatureType getFeatureTypeExtractor() {
        return FeatureType.PUKIPOA;
    }
}
