package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Component
public class AverageLevenshtein implements Extractor {

    private TextProcessor textProcessor;

    private LevenshteinDistance levenshteinDistance;

    @Autowired
    public AverageLevenshtein(TextProcessor textProcessor) {
        this.textProcessor = textProcessor;
        this.levenshteinDistance = new LevenshteinDistance();
    }

    @Override
    public FeatureEntity extract(ReutersEntity reuters) {
        String fullText = StringUtils.normalizeSpace(reuters.getBody()); // skipping paragraphs
        List<String> keywords = textProcessor.prepare(fullText);
        List<String> uniqueWords = getOnlyUniqueWords(keywords);
        List<String> commonWordsWithoutDuplicates = getOnlyCommonWords(keywords).stream().distinct().collect(Collectors.toList());
        List<Double> uniqueDistances = new ArrayList<>();
        for (String unique : uniqueWords) {
            uniqueDistances.add(commonWordsWithoutDuplicates.stream()
                    .mapToInt(i -> levenshteinDistance.apply(unique, i))
                    .average()
                    .orElse(0.0));
        }
        Double result = uniqueDistances.stream()
                .mapToDouble(i -> i)
                .average()
                .orElse(0.0);
        return FeatureEntity.builder().featureType(FeatureType.AL).value(result).build();
    }

    @Override
    public FeatureType getFeatureTypeExtractor() {
        return FeatureType.AL;
    }

}
