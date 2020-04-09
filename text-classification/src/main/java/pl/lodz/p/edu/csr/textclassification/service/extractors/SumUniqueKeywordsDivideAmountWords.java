package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.util.List;

@Component
public class SumUniqueKeywordsDivideAmountWords implements Extractor {

    private TextProcessor textProcessor;

    @Autowired
    SumUniqueKeywordsDivideAmountWords(TextProcessor textProcessor) {
        this.textProcessor = textProcessor;
    }

    @Override
    public Double extract(ReutersEntity reuters) {
        String fullText = StringUtils.normalizeSpace(reuters.getBody()); // skipping paragraphs
        List<String> keywords = textProcessor.prepare(fullText);
        int amountOfWords = fullText.trim().split(" ").length;
        return (double) amountOfUniqueWords(keywords) / amountOfWords;
    }

    @Override
    public FeatureType getFeatureTypeExtractor() {
        return FeatureType.SUKDAW;
    }

}
