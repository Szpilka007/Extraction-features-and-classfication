package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

class ProportionUniqueKeywordsInPartOfArticleTest {

    // Proporcja unikalnych słów kluczowych do wszystkich słów w pierwszym procencie słów artykułu
    Extractor pukipoa = new ProportionUniqueKeywordsInPartOfArticle(new TextProcessor());

    ProportionUniqueKeywordsInPartOfArticleTest() throws IOException {
    }

    @Test
    void extractPukipoaWithTwoUniqueWords() throws IOException {
        String rawText = "We are moon be sun sun test test price price been cat dog cat dog";
        ReutersEntity reutersEntity = ReutersEntity.builder().body(rawText).build();
        List<String> words = Arrays.asList(rawText.split(" "));
        System.out.println("AMOUNT OF WORDS PARSED TEXT = " + 12); // po stemizacji been->be (dlatego mniej)
        System.out.println("PARSED TEXT (KEYWORDS) = " + words.toString());
        System.out.println("UNIQUE WORDS FROM PARSED TEXT = [ar, moon]");
        System.out.println("AMOUNT OF UNIQUE WORDS IN PARSED TEXT = " + 2);

        double expected = 2.0 / (12 * 0.2);
        System.out.println(reutersEntity.toString());
        FeatureEntity actual = pukipoa.extract(reutersEntity);
        Assert.assertEquals(expected, actual.getValue(), 0.1);
    }

}
