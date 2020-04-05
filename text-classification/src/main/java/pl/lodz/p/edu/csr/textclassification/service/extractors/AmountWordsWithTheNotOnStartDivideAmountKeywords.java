package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class AmountWordsWithTheNotOnStartDivideAmountKeywords implements Extractor {

    TextProcessor textProcessor;

    @Autowired
    AmountWordsWithTheNotOnStartDivideAmountKeywords(TextProcessor textProcessor) {
        this.textProcessor = textProcessor;
    }

    @Override
    public Double extract(ReutersEntity reuters) {
        String fullText = StringUtils.normalizeSpace(reuters.getBody()); // skipping paragraphs
        List<String> keywordsWithStopList = textProcessor.prepare(fullText);
        List<String> keywords = textProcessor.stem(textProcessor.tokenize(fullText)); // skipping stoplist
        List<Integer> allIndexes = IntStream.range(0, keywords.size())
                .boxed()
                .skip(2)
                .filter(i -> keywords.get(i-1).toLowerCase().equals("the"))
                .filter(i -> !keywords.get(i-2).equals("."))
                .collect(Collectors.toList()); // get indexes for words with prefix the
        List<String> wordWithPrefixThe = allIndexes.stream().map(keywords::get).collect(Collectors.toList());
        List<String> wordsWithPrefixTheAfterStoplist = textProcessor.useStopWords(wordWithPrefixThe);
        return (double) wordsWithPrefixTheAfterStoplist.size() / keywordsWithStopList.size();
    }

}
