package pl.lodz.p.edu.csr.textclassification.service.metrics;

import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.csr.textclassification.model.Feature;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class ManhattanMetricTest {

    @Test
    void calculate() throws Exception {
        Vector<FeatureEntity> pointA = new Vector<>();
        pointA.add(FeatureEntity.builder().value(5.0).build());
        pointA.add(FeatureEntity.builder().value(7.0).build());

        Vector pointB = new Vector();
        pointB.add(FeatureEntity.builder().value(2.0).build());
        pointB.add(FeatureEntity.builder().value(2.0).build());

        Metric metrics = new ManhattanMetric();

        Double result = metrics.calculate(pointA, pointB);

        assertEquals(Math.sqrt(8.0), result);
    }
}