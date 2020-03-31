package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.io.IOException;
import java.util.List;

class ProportionUniqueKeywordsInPartOfArticleTest {

    ProportionUniqueKeywordsInPartOfArticle puwipoa = new ProportionUniqueKeywordsInPartOfArticle();

    @Test
    void extractPuwipoaWithTwoUniqueWords() throws IOException {
        String rawText = "We are moon be sun sun test test price price been cat dog cat dog";
        TextProcessor textProcessor = new TextProcessor();
        List<String> words = textProcessor.prepare(rawText);
        System.out.println("AMOUNT OF WORDS PARSED TEXT = " + words.size());
        System.out.println("PARSED TEXT (KEYWORDS) = " + words.toString());
        System.out.println("UNIQUE WORDS FROM PARSED TEXT = [ar, moon]");
        System.out.println("AMOUNT OF UNIQUE WORDS IN PARSED TEXT = 2");

        double expected = 2.0 / (words.size() * 0.2);
        double actual = puwipoa.extract(words);
        Assert.assertEquals(expected, actual, 0.1);
    }

}
