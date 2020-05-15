package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ProportionUniqueAndCommonKeywordsTest {

    Extractor puack = new ProportionUniqueAndCommonKeywords(new TextProcessor());

    ProportionUniqueAndCommonKeywordsTest() throws IOException {
    }

    @Test
    void extract() {
        String text = "Sun dog cat cat sun moon. Test dance broken dance break. Been.";
        ReutersEntity reutersEntity = ReutersEntity.builder().body(text).build();
        FeatureEntity actual = puack.extract(reutersEntity);
        // 3 unique words (dog, moon, test) / 4 common (sun, cat, dance, break)
        double expected = (double) 3/4;
        Assert.assertEquals(expected, actual.getValue(), 0.1);
    }

    @Test
    void extractUsingZeroDivisor() {
        String text = "Sun dog cat moon. Test broken dance. Been.";
        ReutersEntity reutersEntity = ReutersEntity.builder().body(text).build();
        FeatureEntity actual = puack.extract(reutersEntity);
        // 7 unique words / 0 common
        double expected = 0.0;
        Assert.assertEquals(expected, actual.getValue(), 0.1);
    }
}
