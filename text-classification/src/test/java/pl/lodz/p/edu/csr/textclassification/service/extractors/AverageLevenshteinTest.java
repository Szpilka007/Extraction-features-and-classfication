package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.io.IOException;

class AverageLevenshteinTest {

    private Extractor al = new AverageLevenshtein(new TextProcessor());

    private String text = "Alice has big cat and fish. This cat is big as Alice's dog.";

    /*
        unique: dog, fish
        common: alice, big, cat

        dog -> alice    (5)         fish -> alice   (4)
        dog -> big      (2)         fish -> big     (3)
        dog -> cat      (3)         fish -> cat     (4)

        dog ->      avg(10/3)       fish ->     avg(11/3)

        result -> avg(21/3 / 2)
     */

    AverageLevenshteinTest() throws IOException {
    }

    @Test
    void extract() {
        ReutersEntity reutersEntity = ReutersEntity.builder().body(text).build();
        FeatureEntity actual = al.extract(reutersEntity);
        double expected = (10.0 / 3.0 + 11.0 / 3.0) / 2.0;
        Assert.assertEquals(expected, actual.getValue(), 0.001);
    }
}