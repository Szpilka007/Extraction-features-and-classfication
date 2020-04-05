package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.TextProcessor;

import java.io.IOException;

class AverageLevenshteinTest {

    Extractor al = new AverageLevenshtein(new TextProcessor());

    String text = "The Sun The dog cat cat sun moon. The Test dance The be dance the broke breaking brake broken.";

    AverageLevenshteinTest() throws IOException {
    }

    @Test
    void extract() {
        ReutersEntity reutersEntity = ReutersEntity.builder().body(text).build();
        Double actual = al.extract(reutersEntity);
        // unique: dog, moon, glass, cow
        // notUnique:
//        int alDog =
    }
}