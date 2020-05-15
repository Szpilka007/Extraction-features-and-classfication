package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.io.IOException;

public class AvgOfSumCommonKeywordsDivideSentenceTest {

    Extractor aosckds = new AvgOfSumCommonKeywordsDivideSentence(new TextProcessor());

    String text = "Sun dog cat cat sun moon. Test dance dance break broken. Been.";

    public AvgOfSumCommonKeywordsDivideSentenceTest() throws IOException {
    }

    @Test
    public void extract() {
        ReutersEntity reutersEntity = ReutersEntity.builder().body(text).build();
        FeatureEntity actual = aosckds.extract(reutersEntity);
        // 1 -> 2 common (cat, sun) / 6 words
        // 2 -> 2 common (dance, break) / 3 words
        // 3 -> canceled
        double firstSentence = (double) 2 / 6;
        double secondSentence = (double) 2 / 3;
        double expected = (firstSentence + secondSentence) / 3.0;
        Assert.assertEquals(expected, actual.getValue(), 0.1);
    }

}
