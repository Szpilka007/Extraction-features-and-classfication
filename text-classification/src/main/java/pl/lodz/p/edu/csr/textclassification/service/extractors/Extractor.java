package pl.lodz.p.edu.csr.textclassification.service.extractors;

import opennlp.tools.tokenize.SimpleTokenizer;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public interface Extractor {

    SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;

    FeatureEntity extract(ReutersEntity reuters);

    static List<String> tokenize(String rawArticleText) {
        return Arrays.asList(tokenizer.tokenize(rawArticleText));
    }

    default List<String> getOnlyUniqueWords(List<String> words) {
        return words.stream()
                .filter(i -> Collections.frequency(words, i) == 1)
                .sorted()
                .collect(Collectors.toList());
    }

    default List<String> getOnlyCommonWords(List<String> words) {
        return words.stream()
                .filter(i -> Collections.frequency(words, i) > 1)
                .sorted()
                .collect(Collectors.toList());
    }

    default Integer amountOfUniqueWords(List<String> words) {
        return Math.toIntExact(words.stream()
                .filter(i -> Collections.frequency(words, i) == 1)
                .count());
    }

    default Integer amountOfCommonWords(List<String> words) {
        return Math.toIntExact(words.stream()
                .filter(i -> Collections.frequency(words, i) > 1)
                .count());
    }

    default Integer amountOfWords(String text) {
        return text.split(" ").length;
    }

    FeatureType getFeatureTypeExtractor();
}
