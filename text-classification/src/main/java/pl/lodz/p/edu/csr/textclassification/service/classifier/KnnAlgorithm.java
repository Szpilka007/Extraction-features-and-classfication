package pl.lodz.p.edu.csr.textclassification.service.classifier;

import org.springframework.stereotype.Component;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.metrics.Metric;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class KnnAlgorithm {

    static <T> T mostCommonLabel(List<T> list) {
        Map<T, Integer> map = new HashMap<>();

        for (T t : list) {
            Integer val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }

        Integer max = map.values().stream().mapToInt(integer -> integer).max().orElse(0);
        List<T> winnerLabels = map.entrySet().stream()
                .filter(i -> i.getValue().equals(max))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        Collections.shuffle(winnerLabels);

        return winnerLabels.get(0);
    }

    public String classifyReuters(Double k, List<ReutersEntity> learningData, ReutersEntity toClassify, List<FeatureType> usingFeaturesType, Metric metric) throws Exception {
        Map<String, Double> metricsScoresForFeatures = new HashMap<>();
        // Calculate distances between learningData and reuters to classify
        for (ReutersEntity reuters : learningData) {
            Vector<FeatureEntity> learningFeatures = reuters.getFeatures().stream()
                    .filter(i -> usingFeaturesType.contains(i.getFeatureType()))
                    .collect(Collectors.toCollection(Vector::new));
            Vector<FeatureEntity> toClassifyFeatures = toClassify.getFeatures().stream()
                    .filter(i -> usingFeaturesType.contains(i.getFeatureType()))
                    .collect(Collectors.toCollection(Vector::new));
            metricsScoresForFeatures.put(reuters.getPlaces().get(0), metric.calculate(learningFeatures, toClassifyFeatures));
        }

        List<String> labels = metricsScoresForFeatures.entrySet().stream()
                .filter(score -> score.getValue() <= k)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        try {
            return mostCommonLabel(labels);
        } catch (IndexOutOfBoundsException e){
            return "unknown";
        }
    }


}
