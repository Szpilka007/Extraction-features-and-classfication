package pl.lodz.p.edu.csr.textclassification.service.classifier;

import org.junit.Assert;
import org.junit.Test;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;

import java.util.*;

public class KnnStatisticsTest {

    KnnStatistics knnStatistics = new KnnStatistics();
    Map<ReutersEntity, List<String>> processedReutersEntity= new HashMap<>();

    public KnnStatisticsTest() {

        processedReutersEntity.put(ReutersEntity.builder()
                .places(Collections.singletonList("france")).build(), Arrays.asList("france"));
        processedReutersEntity.put(ReutersEntity.builder()
                .places(Collections.singletonList("west-germany")).build(), Arrays.asList("france"));
        processedReutersEntity.put(ReutersEntity.builder()
                .places(Collections.singletonList("usa")).build(), Arrays.asList("west-germany"));
        processedReutersEntity.put(ReutersEntity.builder()
                .places(Collections.singletonList("canada")).build(), Arrays.asList("canada"));
        processedReutersEntity.put(ReutersEntity.builder()
                .places(Collections.singletonList("uk")).build(), Arrays.asList("uk"));
        processedReutersEntity.put(ReutersEntity.builder()
                .places(Collections.singletonList("japan")).build(), Arrays.asList("canada"));
    }


    @Test
    public void testAccuracy() {
        double actual = knnStatistics.getAccuracy(processedReutersEntity);
        double expected = 0.5; // poprawne etykiety na liczbe wszystkich artykułów
        Assert.assertEquals(expected,actual,0.1);
    }

    @Test
    public void testPrecision() {
        double actual = knnStatistics.getPrecision(processedReutersEntity);
        double expected = (0.5 + 0 + 0 + 0.5 + 1+ 0) / 6; // średnia poprawnych etykiet na liczbe artykułów przewidzianych z tą etykietą
        Assert.assertEquals(expected,actual,0.1);
    }

    @Test
    public void testRecall() {
        double actual = knnStatistics.getRecall(processedReutersEntity);
        double expected = (double) (1 + 1 + 1) / 6; // średnia poprawnych etykiet na liczbe artykułów z tą etykietą
        Assert.assertEquals(expected,actual,0.1);
    }
}
