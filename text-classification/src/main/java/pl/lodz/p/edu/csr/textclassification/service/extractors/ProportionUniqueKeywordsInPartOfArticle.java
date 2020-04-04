package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProportionUniqueKeywordsInPartOfArticle implements Extractor {

    TextProcessor textProcessor;

    @Autowired
    ProportionUniqueKeywordsInPartOfArticle(TextProcessor textProcessor){
        this.textProcessor = textProcessor;
    }

    private double percentOfArticle = 0.20;

    @Override
    public Double extract(ReutersEntity reuters) {
        String fullText = StringUtils.normalizeSpace(reuters.getBody()); // skipping paragraphs
        List<String> keywords = textProcessor.prepare(fullText); // text to keywords
        int numberOfWordsIncluded = (int) (percentOfArticle * keywords.size());
        Double quantity = Double.valueOf(this.amountOfUniqueWords(keywords
                .stream()
                .limit(numberOfWordsIncluded)
                .collect(Collectors.toList())));
        System.out.println(quantity.toString()+" "+keywords.size()+" "+percentOfArticle);
        return quantity / (keywords.size() * percentOfArticle);
    }
}
