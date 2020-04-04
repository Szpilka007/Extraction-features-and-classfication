package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SumCommonKeywordsDivideAmountWordsTest {

    Extractor sckdaw = new SumCommonKeywordsDivideAmountWords(new TextProcessor());

    String text = "Sun dog cat cat sun moon. Test dance dance. Been.";

    public SumCommonKeywordsDivideAmountWordsTest() throws IOException {
    }

    @Test
    public void extractScore() {
        ReutersEntity reutersEntity = ReutersEntity.builder().body(text).build();
        Double actual = sckdaw.extract(reutersEntity);
        // 6 common words, 10 words
        double expected = (double) 6 / 10;
        Assert.assertEquals(expected, actual, 0.1);
    }
}