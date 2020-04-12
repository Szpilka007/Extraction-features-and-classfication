package pl.lodz.p.edu.csr.textclassification.service.classifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lodz.p.edu.csr.textclassification.model.enums.DataBreakdown;
import pl.lodz.p.edu.csr.textclassification.model.enums.DataGroup;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
import pl.lodz.p.edu.csr.textclassification.repository.ReutersRepository;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.extractors.Extractor;
import pl.lodz.p.edu.csr.textclassification.service.metrics.Metric;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class KnnAlgorithm {

    @Autowired
    private ReutersRepository reutersRepository;

    @Autowired
    KnnAlgorithm(ReutersRepository reutersRepository) {
        this.reutersRepository = reutersRepository;
    }

    public static <T> T mostCommonLabel(List<T> list) {
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

    public String classifyReuters(Double k, DataBreakdown dataBreakdown, ReutersEntity toClassify, List<FeatureType> usingFeatureType, Metric metric) throws Exception {

        List<DataGroup> dataGroups = DataBreakdown.getLearningGroups(dataBreakdown);
        List<ReutersEntity> learningData = reutersRepository.findAll().stream()
                .filter(i -> dataGroups.contains(i.getDataGroup()))
                .collect(Collectors.toList());

        Map<ReutersEntity, Double> metricsScoresForFeatures = new HashMap<>();
        // Calculate distances between learningData and reuters to classify
        for (ReutersEntity reuters : learningData) {
            Vector<FeatureEntity> learningFeatures = reuters.getFeatures().stream()
                    .filter(i -> usingFeatureType.contains(i.getFeatureType()))
                    .collect(Collectors.toCollection(Vector::new));
            Vector<FeatureEntity> toClassifyFeatures = toClassify.getFeatures().stream()
                    .filter(i -> usingFeatureType.contains(i.getFeatureType()))
                    .collect(Collectors.toCollection(Vector::new));
//            System.out.println(reuters.toString());
//            System.out.println(learningFeatures.toString());
//            System.out.println(toClassifyFeatures.toString());
//            System.out.println("===================================");
            metricsScoresForFeatures.put(reuters, metric.calculate(learningFeatures, toClassifyFeatures));
        }

        List<String> labels = metricsScoresForFeatures.entrySet().stream()
                .filter(score -> score.getValue() <= k)
                .map(i -> i.getKey().getPlaces().get(0))
                .collect(Collectors.toList());

        return mostCommonLabel(labels);
    }


}
