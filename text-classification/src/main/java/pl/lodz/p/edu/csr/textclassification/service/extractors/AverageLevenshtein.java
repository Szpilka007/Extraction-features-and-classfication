package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AverageLevenshtein implements Extractor {

    TextProcessor textProcessor;

    LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

    @Autowired
    AverageLevenshtein(TextProcessor textProcessor) {
        this.textProcessor = textProcessor;
    }

    @Override
    public Double extract(ReutersEntity reuters) {
        String fullText = StringUtils.normalizeSpace(reuters.getBody()); // skipping paragraphs
        List<String> keywords = textProcessor.prepare(fullText);
        List<String> uniqueWords = getOnlyUniqueWords(keywords);
        List<String> commonWordsWithoutDuplicates = getOnlyCommonWords(keywords).stream().distinct().collect(Collectors.toList());
        System.out.println(uniqueWords.toString());
        System.out.println(commonWordsWithoutDuplicates.toString());
        Integer distances = 0;
        for (String unique : uniqueWords) {
            distances += commonWordsWithoutDuplicates.stream()
                    .peek(i -> System.out.println(levenshteinDistance.apply(unique, i)))
                    .mapToInt(i -> levenshteinDistance.apply(unique, i))
                    .sum();

//            List<Integer> allIndexes = IntStream.range(0, commonWordsWithoutDuplicates.size())
//                    .boxed()
//                    .mapToInt(i -> levenshteinDistance.apply(unique,i))
//                    .collect(Collectors.toList()); // get indexes for words with prefix the
        }
        return (double) distances / commonWordsWithoutDuplicates.size();
    }

}
