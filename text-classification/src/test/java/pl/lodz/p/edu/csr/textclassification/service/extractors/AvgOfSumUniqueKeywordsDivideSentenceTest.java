package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.io.IOException;

public class AvgOfSumUniqueKeywordsDivideSentenceTest {

    Extractor aosukds = new AvgOfSumUniqueKeywordsDivideSentence(new TextProcessor());

    String text = "Sun dog cat cat sun moon. Test dance dance. Been.";

    public AvgOfSumUniqueKeywordsDivideSentenceTest() throws IOException {
    }

    @Test
    public void extractScore() {
        ReutersEntity reutersEntity = ReutersEntity.builder().body(text).build();
        Double actual = aosukds.extract(reutersEntity);
        // 1 -> 2 unique / 6 words
        // 2 -> 1 unique / 3 words
        // 3 -> canceled
        double firstSentence = (double) 2 / 6;
        double secondSentence = (double) 1 / 3;
        double expected = (firstSentence + secondSentence) / 3.0;
        Assert.assertEquals(expected, actual, 0.1);
    }

}
