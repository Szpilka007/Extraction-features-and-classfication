package pl.lodz.p.edu.csr.textclassification.service.metrics;

import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;

import java.util.Vector;

public class CosineAmplitude implements Metric {
    @Override
    public Double calculate(Vector<FeatureEntity> a, Vector<FeatureEntity> b) throws Exception {
        return null;
    }

    @Override
    public MetricType getMetricsType() {
        return null;
    }
}
