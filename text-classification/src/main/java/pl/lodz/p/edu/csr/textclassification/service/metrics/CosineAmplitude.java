package pl.lodz.p.edu.csr.textclassification.service.metrics;

import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;

import java.util.Iterator;
import java.util.Vector;

public class CosineAmplitude implements Metric {
    @Override
    public Double calculate(Vector<FeatureEntity> a, Vector<FeatureEntity> b) throws Exception {
        validatePoints(a,b);

        Iterator aIterator = a.iterator();
        Iterator bIterator = b.iterator();

        double counter =0.0, dimASum =0.0, dimBSum =0.0;
        FeatureEntity dimA, dimB;

        while(aIterator.hasNext()){
            dimA = (FeatureEntity) aIterator.next();
            dimB = (FeatureEntity) bIterator.next();
            counter += dimA.getValue() * dimB.getValue();
            dimASum += dimA.getValue()*dimA.getValue();
            dimBSum += dimB.getValue()*dimB.getValue();
        }

        return Math.abs(counter) / Math.sqrt(dimASum*dimBSum);
    }

    @Override
    public MetricType getMetricsType() {
        return MetricType.COSINE_AMPLITUDE;
    }
}
