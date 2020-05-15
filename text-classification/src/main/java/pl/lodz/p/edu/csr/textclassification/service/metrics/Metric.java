package pl.lodz.p.edu.csr.textclassification.service.metrics;

import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;

import java.util.Vector;

public interface Metric {

    Double calculate(Vector<FeatureEntity> a, Vector<FeatureEntity> b) throws Exception;

    default Boolean validatePoints(Vector a, Vector b) throws Exception {
        if (a.isEmpty() || b.isEmpty()) {
            throw new Exception("The point is dimensionless.");
        }
        if (a.size() != b.size()) {
            throw new Exception("Points are not specified in the same dimension.");
        }
        return true;
    }

    MetricType getMetricsType();
}
