package pl.lodz.p.edu.csr.textclassification.service.extractors;

import opennlp.tools.tokenize.SimpleTokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Extractor {

    SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
    Double extract(String extractionText);

    static List<String> tokenize(String rawArticleText){
        return Arrays.asList(tokenizer.tokenize(rawArticleText));
    }

    default List<String> getOnlyUniqueWords(String rawArticleText) {
        List<String> listOfUniqueWords = Arrays.asList(rawArticleText.split(" "));
//        listOfUniqueWords.stream().
        return new ArrayList<>();
    }

    default List<String> getOnlyCommonWords(String rawArticleText) {
        return new ArrayList<>();
    }

    default Integer amountUniqueWords(String rawArticleText) {
        return 0;
    }

    default Integer amountCommonWords(String rawArticleText) {
        return 0;
    }

}
