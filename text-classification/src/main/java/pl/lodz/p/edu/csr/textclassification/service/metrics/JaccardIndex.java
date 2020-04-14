package pl.lodz.p.edu.csr.textclassification.service.metrics;

import org.springframework.stereotype.Component;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;

import java.util.Iterator;
import java.util.Vector;
import java.util.stream.Stream;

@Component
public class JaccardIndex implements Metric {
    @Override
    public Double calculate(Vector<FeatureEntity> a, Vector<FeatureEntity> b) throws Exception {
        validatePoints(a, b);
        long commonElements = a.stream().filter(b::contains).distinct().count();
        long allNotDuplicatedElements = Stream.concat(a.stream(), b.stream()).distinct().count();
        return (double) commonElements / (double) allNotDuplicatedElements;
    }

    @Override
    public MetricType getMetricsType() {
        return MetricType.JACCARD_INDEX;
    }
}
