package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.util.ArrayList;
import java.util.List;

@Component
public class AmountWordsBetweenUniqueKeywords implements Extractor {

    private TextProcessor textProcessor;

    @Autowired
    AmountWordsBetweenUniqueKeywords(TextProcessor textProcessor) {
        this.textProcessor = textProcessor;
    }

    @Override
    public FeatureEntity extract(ReutersEntity reuters) {
        String fullText = StringUtils.normalizeSpace(reuters.getBody()); // skipping paragraphs
        List<String> keywords = textProcessor.prepare(fullText); // text to keywords
        List<String> uniqueWords = getOnlyUniqueWords(keywords);
        List<Integer> lengths = new ArrayList<>();

        int wordIndex = 0;
        for (int i = 0; i < keywords.size(); i++) {
            if (uniqueWords.contains(keywords.get(i))) {
                lengths.add(i - wordIndex);
                wordIndex = i;
            }

        }
        lengths.remove(0);
        return FeatureEntity.builder()
                .value((double) lengths.stream().reduce((Integer::sum)).get() / (double) lengths.size())
                .featureType(FeatureType.AWBUK)
                .build();
    }

    @Override
    public FeatureType getFeatureTypeExtractor() {
        return FeatureType.AWBUK;
    }
}
