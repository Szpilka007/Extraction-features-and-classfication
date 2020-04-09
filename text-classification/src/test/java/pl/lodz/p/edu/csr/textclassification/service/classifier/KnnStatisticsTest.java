package pl.lodz.p.edu.csr.textclassification.service.classifier;

import org.junit.Assert;
import org.junit.Test;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KnnStatisticsTest {

    KnnStatistics knnStatistics = new KnnStatistics();
    Map<ReutersEntity, List<String>> processedReutersEntity= new HashMap<>();

    public KnnStatisticsTest() {

        processedReutersEntity.put(ReutersEntity.builder()
                .places(Collections.singletonList("france")).build(), Collections.singletonList("france"));
        processedReutersEntity.put(ReutersEntity.builder()
                .places(Collections.singletonList("west-germany")).build(), Collections.singletonList("france"));
        processedReutersEntity.put(ReutersEntity.builder()
                .places(Collections.singletonList("usa")).build(), Collections.singletonList("west-germany"));
        processedReutersEntity.put(ReutersEntity.builder()
                .places(Collections.singletonList("canada")).build(), Collections.singletonList("canada"));
        processedReutersEntity.put(ReutersEntity.builder()
                .places(Collections.singletonList("uk")).build(), Collections.singletonList("uk"));
        processedReutersEntity.put(ReutersEntity.builder()
                .places(Collections.singletonList("japan")).build(), Collections.singletonList("canada"));
    }


    @Test
    public void testAccuracy() {
        double actual = knnStatistics.getAccuracy(processedReutersEntity);
        double expected = 0.5;
        // poprawne etykiety na liczbe wszystkich artykułów
        Assert.assertEquals(expected,actual,0.1);
    }

    @Test
    public void testPrecision() {
        double actual = knnStatistics.getPrecision(processedReutersEntity);
        double expected = (0.5 + 0 + 0 + 0.5 + 1+ 0) / 6;
        // średnia poprawnych etykiet
        // na liczbe artykułów przewidzianych
        // z tą etykietą
        Assert.assertEquals(expected,actual,0.1);
    }

    @Test
    public void testRecall() {
        double actual = knnStatistics.getRecall(processedReutersEntity);
        double expected = (double) (1 + 1 + 1) / 6;
        // średnia poprawnych etykiet na liczbe artykułów z tą etykietą
        Assert.assertEquals(expected,actual,0.1);
    }
}
