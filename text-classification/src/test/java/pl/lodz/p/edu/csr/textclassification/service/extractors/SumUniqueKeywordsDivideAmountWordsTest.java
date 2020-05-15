package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.io.IOException;

public class SumUniqueKeywordsDivideAmountWordsTest {

    Extractor sukdaw = new SumUniqueKeywordsDivideAmountWords(new TextProcessor());

    String text = "Sun dog cat cat sun moon. Test dance dance. Been.";

    public SumUniqueKeywordsDivideAmountWordsTest() throws IOException {
    }

    @Test
    public void extract() {
        ReutersEntity reutersEntity = ReutersEntity.builder().body(text).build();
        FeatureEntity actual = sukdaw.extract(reutersEntity);
        // 3 unique words, 10 words
        double expected = (double) 3 / 10;
        Assert.assertEquals(expected, actual.getValue(), 0.1);
    }

}
