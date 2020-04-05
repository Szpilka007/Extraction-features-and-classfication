package pl.lodz.p.edu.csr.textclassification.service.metrics;

import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;

import java.util.*;

public class ChebyshevDistance implements Metric {

    @Override
    public Double calculate(Vector<FeatureEntity> a, Vector<FeatureEntity> b) throws Exception {
        validatePoints(a, b);

        Iterator aIterator = a.iterator();
        Iterator bIterator = b.iterator();

        Double difference;
        FeatureEntity dimA, dimB;
        Collection<Double> distances = new ArrayList<>();

        while (aIterator.hasNext()) {
            dimA = (FeatureEntity) aIterator.next();
            dimB = (FeatureEntity) bIterator.next();
            difference = dimA.getValue() - dimB.getValue();
            distances.add(Math.abs(difference));
        }

        return Collections.max(distances);
    }

    @Override
    public MetricType getMetricsType() {
        return MetricType.CHEBYSHEV;
    }

}
