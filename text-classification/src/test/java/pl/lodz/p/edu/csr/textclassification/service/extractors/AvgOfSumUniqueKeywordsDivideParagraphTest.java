package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AvgOfSumUniqueKeywordsDivideParagraphTest {

    Extractor aosukdp = new AvgOfSumUniqueKeywordsDivideParagraph(new TextProcessor());

    String text = "Sun dog cat cat sun moon. Test dance dance break broken.     Test cow glass cow.     Reuters.";

    AvgOfSumUniqueKeywordsDivideParagraphTest() throws IOException {
    }

    @Test
    public void extract() {
        ReutersEntity reutersEntity = ReutersEntity.builder().body(text).build();
        FeatureEntity actual = aosukdp.extract(reutersEntity);
        // 1 -> 3 unique (moon, dog, test) / 11 words
        // 2 -> 2 unique (test, glass) / 4 words
        // 3 -> 1 unique (reuters - but in stoplist) / 1 words
        double firstSentence = (double) 3 / 11;
        double secondSentence = (double) 2 / 4;
        double thirdSentence = (double) 0 / 1;
        double expected = (firstSentence + secondSentence + thirdSentence) / 3.0;
        Assert.assertEquals(expected, actual.getValue(), 0.1);
    }
}
