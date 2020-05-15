package pl.lodz.p.edu.csr.textclassification.service.metrics;

import org.springframework.stereotype.Component;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;

import java.util.Iterator;
import java.util.Vector;

@Component
public class ManhattanMetric implements Metric {

    @Override
    public Double calculate(Vector<FeatureEntity> a, Vector<FeatureEntity> b) throws Exception {
        validatePoints(a, b);

        Iterator aIterator = a.iterator();
        Iterator bIterator = b.iterator();

        Double sum = 0.0, difference;
        FeatureEntity dimA, dimB;

        while (aIterator.hasNext()) {
            dimA = (FeatureEntity) aIterator.next();
            dimB = (FeatureEntity) bIterator.next();
            difference = dimA.getValue() - dimB.getValue();
            sum += Math.abs(difference);
        }

        return Math.sqrt(sum);
    }

    @Override
    public MetricType getMetricsType() {
        return MetricType.MANHATTAN;
    }

}
