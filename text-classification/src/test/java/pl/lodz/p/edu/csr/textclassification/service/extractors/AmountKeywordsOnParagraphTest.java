package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.io.IOException;

class AmountKeywordsOnParagraphTest {

    Extractor akop = new AmountKeywordsOnParagraph(new TextProcessor());

    String text = "Sun dog cat cat sun moon. Test dance dance break broken.     Test cow glass cow.     Reuters.";

    AmountKeywordsOnParagraphTest() throws IOException {
    }

    @Test
    public void extract() {
        ReutersEntity reutersEntity = ReutersEntity.builder().body(text).build();
        FeatureEntity actual = akop.extract(reutersEntity);
        // 1 -> 11 words
        // 2 -> 4 words
        // 3 -> 1 word (reuters - but in stoplist - skipped)
        double firstSentence = 11;
        double secondSentence = 4;
        double expected = (firstSentence + secondSentence) / 2.0;
        Assert.assertEquals(expected, actual.getValue(), 0.1);
    }
}
