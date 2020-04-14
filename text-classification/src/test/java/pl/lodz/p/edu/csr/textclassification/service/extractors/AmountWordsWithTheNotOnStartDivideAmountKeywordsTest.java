package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AmountWordsWithTheNotOnStartDivideAmountKeywordsTest {

    Extractor awwtnosdak = new AmountWordsWithTheNotOnStartDivideAmountKeywords(new TextProcessor());

    String text = "The Sun The dog cat cat sun moon. The Test dance The be dance the break broken. Test glass cow.";

    AmountWordsWithTheNotOnStartDivideAmountKeywordsTest() throws IOException {
    }

    @Test
    public void extract() {
        ReutersEntity reutersEntity = ReutersEntity.builder().body(text).build();
        FeatureEntity actual = awwtnosdak.extract(reutersEntity);
        // dog, break (be skipped by stoplist)
        double expected = 2.0 / 15.0;
        Assert.assertEquals(expected, actual.getValue(), 0.01);
    }
}
