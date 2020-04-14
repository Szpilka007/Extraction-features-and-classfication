package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.util.Arrays;
import java.util.List;

@Component
public class AvgOfSumUniqueKeywordsDivideSentence implements Extractor {

    private TextProcessor textProcessor;

    @Autowired
    AvgOfSumUniqueKeywordsDivideSentence(TextProcessor textProcessor) {
        this.textProcessor = textProcessor;
    }

    @Override
    public FeatureEntity extract(ReutersEntity reuters) {
        String fullText = StringUtils.normalizeSpace(reuters.getBody()); // skipping paragraphs
        List<String> sentences = Arrays.asList(fullText.split("\\. "));
        double avgValue = 0.0;
        for (String sentence : sentences) {
            List<String> keywords = textProcessor.prepare(sentence);
            if (keywords.size() <= 0) continue;
            avgValue += (double) amountOfUniqueWords(keywords) / keywords.size();
        }
        return FeatureEntity
                .builder()
                .value(avgValue / sentences.size())
                .featureType(FeatureType.AOSCKDS)
                .build();
    }

    @Override
    public FeatureType getFeatureTypeExtractor() {
        return FeatureType.AOSUKDS;
    }

}
