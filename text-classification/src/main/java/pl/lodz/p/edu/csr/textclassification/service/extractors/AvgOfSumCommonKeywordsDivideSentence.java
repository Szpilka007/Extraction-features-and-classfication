package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.util.Arrays;
import java.util.List;

@Component
public class AvgOfSumCommonKeywordsDivideSentence implements Extractor {

    private static final String SENTENCE_SPLIT_REGEX = "\\. ";

    private TextProcessor textProcessor;

    @Autowired
    AvgOfSumCommonKeywordsDivideSentence(TextProcessor textProcessor) {
        this.textProcessor = textProcessor;
    }

    @Override
    public Double extract(ReutersEntity reuters) {
        String fullText = StringUtils.normalizeSpace(reuters.getBody()); // skipping paragraphs
        List<String> sentences = Arrays.asList(fullText.split(SENTENCE_SPLIT_REGEX));
        double avgValue = 0.0;
        for (String sentence : sentences) {
            List<String> keywords = textProcessor.prepare(sentence);
            if (keywords.size() <= 0) continue;
            avgValue += (double) amountOfCommonWords(keywords) / keywords.size();
        }
        return avgValue / sentences.size();
    }

    @Override
    public FeatureType getFeatureTypeExtractor() {
        return FeatureType.AOSCKDS;
    }

}
