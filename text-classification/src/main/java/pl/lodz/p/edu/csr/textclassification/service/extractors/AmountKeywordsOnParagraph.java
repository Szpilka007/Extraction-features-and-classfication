package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.util.Arrays;
import java.util.List;

@Component
public class AmountKeywordsOnParagraph implements Extractor {

    private static final String PARAGRAPH_SPLIT_REGEX = "     ";

    private TextProcessor textProcessor;

    @Autowired
    AmountKeywordsOnParagraph(TextProcessor textProcessor) {
        this.textProcessor = textProcessor;
    }

    @Override
    public FeatureEntity extract(ReutersEntity reuters) {
        List<String> paragraphs = Arrays.asList(reuters.getBody().split(PARAGRAPH_SPLIT_REGEX));
        int amountOfParagraphs = paragraphs.size();
        double avgValue = 0.0;
        for (String paragraph : paragraphs) {
            List<String> keywords = textProcessor.prepare(paragraph);
            if (keywords.size() <= 0) {
                amountOfParagraphs--;
                continue;
            }
            avgValue += keywords.size();
        }

        return FeatureEntity.builder().value(avgValue / amountOfParagraphs).featureType(FeatureType.AKOP).build();
    }

    @Override
    public FeatureType getFeatureTypeExtractor() {
        return FeatureType.AKOP;
    }
}
