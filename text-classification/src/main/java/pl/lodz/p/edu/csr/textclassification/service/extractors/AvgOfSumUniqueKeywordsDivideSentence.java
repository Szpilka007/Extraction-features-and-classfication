package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.util.Arrays;
import java.util.List;

@Component
public class AvgOfSumUniqueKeywordsDivideSentence implements Extractor {

    TextProcessor textProcessor;

    @Autowired
    AvgOfSumUniqueKeywordsDivideSentence(TextProcessor textProcessor) {
        this.textProcessor = textProcessor;
    }

    // ile średnio na zdanie przypada w artykule słów kluczowych

    @Override
    public Double extract(ReutersEntity reuters) {
        String fullText = StringUtils.normalizeSpace(reuters.getBody()); // skipping paragraphs
        List<String> sentences = Arrays.asList(fullText.split("\\. "));
        Double avgValue = 0.0;
        for(String sentence : sentences){
            List<String> keywords = textProcessor.prepare(sentence);
            if(keywords.size() <= 1) continue;
            avgValue+=(double)amountOfUniqueWords(keywords) / keywords.size();
        }
        return avgValue / sentences.size();
    }

}
