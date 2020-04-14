package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.io.IOException;

class AvgOfSumCommonKeywordsDivideParagraphTest {

    Extractor aosckdp = new AvgOfSumCommonKeywordsDivideParagraph(new TextProcessor());

    String text = "Sun dog cat cat sun moon. Test dance dance break broken.     Test cow glass cow.     Reuters.";

    AvgOfSumCommonKeywordsDivideParagraphTest() throws IOException {
    }

    @Test
    public void extract() {
        ReutersEntity reutersEntity = ReutersEntity.builder().body(text).build();
        FeatureEntity actual = aosckdp.extract(reutersEntity);
        // 1 -> 4 common (sun, cat, dance, break) / 11 words
        // 2 -> 1 common (cow) / 4 words
        // 3 -> 0 common / 1 words (but in stop-list, so ignore paragraph)
        double firstSentence = (double) 4 / 11;
        double secondSentence = (double) 1 / 4;
        double thirdSentence = (double) 0 / 1;
        double expected = (firstSentence + secondSentence + thirdSentence) / 2.0;
        Assert.assertEquals(expected, actual.getValue(), 0.1);
    }
}
