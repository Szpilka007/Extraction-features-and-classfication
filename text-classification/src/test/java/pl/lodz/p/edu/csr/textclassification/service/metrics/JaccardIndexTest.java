package pl.lodz.p.edu.csr.textclassification.service.metrics;

import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class JaccardIndexTest {

    @Test
    void calculate() throws Exception {
        Vector pointA = new Vector();
        pointA.add(FeatureEntity.builder().value(2.0).build());
        pointA.add(FeatureEntity.builder().value(2.0).build());

        Vector pointB = new Vector();
        pointB.add(FeatureEntity.builder().value(2.0).build());
        pointB.add(FeatureEntity.builder().value(4.0).build());

        Metric metrics = new JaccardIndex();

        Double result = metrics.calculate(pointA, pointB);

        assertEquals(0.5, result);
    }
}
