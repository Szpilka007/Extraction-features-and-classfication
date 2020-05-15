package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.util.List;

@Component
public class ProportionUniqueAndCommonKeywords implements Extractor {

    private TextProcessor textProcessor;

    @Autowired
    ProportionUniqueAndCommonKeywords(TextProcessor textProcessor) {
        this.textProcessor = textProcessor;
    }

    @Override
    public FeatureEntity extract(ReutersEntity reuters) {
        String fullText = StringUtils.normalizeSpace(reuters.getBody()); // skipping paragraphs
        List<String> keywords = textProcessor.prepare(fullText); // text to keywords
        Double result = getOnlyCommonWords(keywords).isEmpty() ?
                0.0 : (double) getOnlyUniqueWords(keywords).size() / getOnlyCommonWords(keywords).size();
        return FeatureEntity.builder()
                .value(result)
                .featureType(FeatureType.PUACK)
                .build();
    }

    @Override
    public FeatureType getFeatureTypeExtractor() {
        return FeatureType.PUACK;
    }
}
