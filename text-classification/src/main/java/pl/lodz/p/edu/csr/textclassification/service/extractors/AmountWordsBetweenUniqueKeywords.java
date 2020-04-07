package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.util.ArrayList;
import java.util.List;

public class AmountWordsBetweenUniqueKeywords implements Extractor {

    TextProcessor textProcessor;

    @Autowired
    AmountWordsBetweenUniqueKeywords(TextProcessor textProcessor){
        this.textProcessor = textProcessor;
    }

    @Override
    public Double extract(ReutersEntity reuters) {
        String fullText = StringUtils.normalizeSpace(reuters.getBody()); // skipping paragraphs
        List<String> keywords = textProcessor.prepare(fullText); // text to keywords
        List<String> uniqueWords = getOnlyUniqueWords(keywords);
        List<Integer> lengths = new ArrayList<>();

        int wordIndex = 0;
        for(int i=0; i< keywords.size(); i++){
            if(uniqueWords.contains(keywords.get(i))){
                lengths.add(i-wordIndex);
                wordIndex = i;
            }

        }
        lengths.remove(0);

        return (double) lengths.stream().reduce((Integer::sum)).get() / (double) lengths.size();
    }

    @Override
    public FeatureType getFeatureTypeExtractor() {
        return FeatureType.AWBUK;
    }
}
