package pl.lodz.p.edu.csr.textclassification.service.classifier;

import org.springframework.stereotype.Service;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.extractors.Extractor;
import pl.lodz.p.edu.csr.textclassification.service.metrics.Metric;

import java.lang.reflect.Array;
import java.util.*;

@Service
public class KnnAlgorithm {

    public void calculate(Double k, Double percentOfTestReuters, List<ReutersEntity> groupsToTest, List<Extractor> extractors, Metric metric){
        Map<ReutersEntity, Vector<Double>> reutersEntityFeaturesMap = new HashMap<>();

        for(ReutersEntity reuterEntity: groupsToTest){
            Vector<Double> features = new Vector<>();
            extractors.forEach(extractor -> features.add(extractor.extract(reuterEntity)));
            reutersEntityFeaturesMap.put(reuterEntity,features);
        }

        ArrayList<Map<ReutersEntity, Vector<Double>>> treningSet = new ArrayList<>();
        for(int i=0; i < percentOfTestReuters * reutersEntityFeaturesMap.size(); i++){
            treningSet.add(reutersEntityFeaturesMap.entrySet());
        }






    }

}
