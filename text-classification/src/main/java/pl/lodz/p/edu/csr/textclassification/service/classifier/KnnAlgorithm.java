package pl.lodz.p.edu.csr.textclassification.service.classifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.extractors.Extractor;
import pl.lodz.p.edu.csr.textclassification.service.metrics.Metric;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class KnnAlgorithm {

    private final List<Extractor> extractorList;

    @Autowired
    KnnAlgorithm(List<Extractor> extractors) {
        this.extractorList = extractors;
    }

    public void calculate(Double k, Double percentOfTestReuters, List<ReutersEntity> groupsToTest, Metric metric) {
        Map<ReutersEntity, Vector<FeatureEntity>> trainingReutersEntityFeaturesMap = new HashMap<>();
        Map<ReutersEntity, Vector<FeatureEntity>> learningReutersEntityFeaturesMap = new HashMap<>();

        //OTRZYMUJEMY ZBIOR TESTOWY I WYLICZAMY DLA NIEGO CECHY
        groupsToTest.stream()
                .limit((int) (percentOfTestReuters * groupsToTest.size()))
                .forEach(reutersEntity -> {
                    Vector<FeatureEntity> features = new Vector<>();
                    this.extractorList.forEach(extractor -> features.add(extractor.extract(reutersEntity)));
                    trainingReutersEntityFeaturesMap.put(reutersEntity, features);
                });

        //OTRZYMUJEMY ZBIOR UCZACY I WYLICZAMY DLA NIEGO CECHY
        groupsToTest.stream()
                .skip((int) (percentOfTestReuters * groupsToTest.size()))
                .forEach(reutersEntity -> {
                    Vector<FeatureEntity> features = new Vector<>();
                    this.extractorList.forEach(extractor -> features.add(extractor.extract(reutersEntity)));
                    learningReutersEntityFeaturesMap.put(reutersEntity, features);
                });

        //POROWNUJEMY OBIEKTY Z ZBIORU UCZACEGO POKOLEI Z KAZDYMI CECHAMI Z ZBIORU TESTOWEGO
        learningReutersEntityFeaturesMap.forEach(((reutersEntity, features) -> {
            ArrayList<Double> metricScoresForFeautres = new ArrayList<>();
            trainingReutersEntityFeaturesMap.forEach((testReutersEntity, testFeatures) -> {
                try {
                    metricScoresForFeautres.add(metric.calculate(features, testFeatures));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            //ODRZUCAMY TE METRYKI KTORE NIE SPELNIAJA WARTOSCI
            //I OTRZYMUJEMY METRYKE KTROA NAJCZESCNIEJ SIE POWTARZA
            Double filteredMetrics = metricScoresForFeautres
                    .stream()
                    .filter(score -> score < k)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet().stream().max((o1, o2) -> o1.getValue().compareTo(o2.getValue()))
                    .map(Map.Entry::getKey).orElse(null);


        }));


    }

}
