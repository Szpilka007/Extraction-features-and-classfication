package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.io.IOException;

public class AmountWordsBetweenUniqueKeywordsTest {

    Extractor aosckdp = new AmountWordsBetweenUniqueKeywords(new TextProcessor());

    String text = "Black sun dog cat cat sun moon. Test dance dance. Been. dance flower";

    @Test
    public void extract(){
        ReutersEntity reutersEntity = ReutersEntity.builder().body(text).build();
        Double actual = aosckdp.extract(reutersEntity);
        // 4 unique words - > Black, dog, moon, Test, flower
        double expected = (double) (2+ 4 + 1 + 4) / 4;

        Assert.assertEquals(expected, actual, 0.1);
    }

    public AmountWordsBetweenUniqueKeywordsTest() throws IOException {
    }
}
