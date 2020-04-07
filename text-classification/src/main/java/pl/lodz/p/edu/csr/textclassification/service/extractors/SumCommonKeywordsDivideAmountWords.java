package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.edu.csr.textclassification.model.Feature;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.util.List;

@Component
public class SumCommonKeywordsDivideAmountWords implements Extractor {

    TextProcessor textProcessor;

    @Autowired
    SumCommonKeywordsDivideAmountWords(TextProcessor textProcessor) {
        this.textProcessor = textProcessor;
    }

    @Override
    public FeatureEntity extract(ReutersEntity reuters) {
        String fullText = StringUtils.normalizeSpace(reuters.getBody()); // skipping paragraphs
        List<String> keywords = textProcessor.prepare(fullText);
        int amountOfWords = fullText.trim().split(" ").length;
        return FeatureEntity
                .builder()
                .value((double) amountOfCommonWords(keywords) / amountOfWords)
                .featureType(FeatureType.SCKDAW)
                .build();
    }

}
