package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.util.Arrays;
import java.util.List;

@Component
public class AvgOfSumUniqueKeywordsDivideParagraph implements Extractor {

    private static final String PARAGRAPH_SPLIT_REGEX = "     ";

    private TextProcessor textProcessor;

    @Autowired
    AvgOfSumUniqueKeywordsDivideParagraph(TextProcessor textProcessor) {
        this.textProcessor = textProcessor;
    }

    @Override
    public Double extract(ReutersEntity reuters) {
        List<String> paragraphs = Arrays.asList(reuters.getBody().split(PARAGRAPH_SPLIT_REGEX));
        double avgValue = 0.0;
        for (String paragraph : paragraphs) {
            List<String> keywords = textProcessor.prepare(paragraph);
            if (keywords.size() <= 0) continue;
            avgValue += (double) amountOfUniqueWords(keywords) / keywords.size();
        }
        return avgValue / paragraphs.size();
    }

    @Override
    public FeatureType getFeatureTypeExtractor() {
        return FeatureType.AOSUKDP;
    }

}
