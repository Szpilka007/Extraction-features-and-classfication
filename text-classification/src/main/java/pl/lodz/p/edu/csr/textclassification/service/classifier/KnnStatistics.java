package pl.lodz.p.edu.csr.textclassification.service.classifier;


import org.springframework.stereotype.Service;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class KnnStatistics {

    ArrayList<String> labels = new ArrayList<>(Arrays.asList("west-germany", "usa", "france", "uk", "canada", "japan"));

    public double getAccuracy(Map<ReutersEntity, List<String>> processedReuters) {
        AtomicInteger correctReuters = new AtomicInteger();
        processedReuters.forEach(((reutersEntity, strings) -> {
            if (reutersEntity.getPlaces().equals(strings)) {
                correctReuters.addAndGet(1);
            }
        }));
        return (double) correctReuters.get() / processedReuters.size();
    }

    public double getPrecision(Map<ReutersEntity, List<String>> processedReuters) {
        AtomicInteger correctReuters = new AtomicInteger();
        AtomicInteger labelsAmount = new AtomicInteger();
        ArrayList<Double> precisions = new ArrayList<>();
        labels.forEach(label -> processedReuters.forEach(((reutersEntity, strings) -> {
            if (strings.toString().equals(label)) {
                labelsAmount.addAndGet(1);
                if (reutersEntity.getPlaces().equals(strings)) {
                    correctReuters.addAndGet(1);
                }
            }
            precisions.add(correctReuters.doubleValue() / labelsAmount.doubleValue());
        })));

        return precisions.stream().reduce(Double::sum).get() / precisions.size();;
    }

    public double getRecall(Map<ReutersEntity, List<String>> processedReuters) {
        AtomicInteger correctReuters = new AtomicInteger();
        AtomicInteger labelsAmount = new AtomicInteger();
        ArrayList<Double> recalls = new ArrayList<>();
        labels.forEach(label -> processedReuters.forEach(((reutersEntity, strings) -> {
            if (reutersEntity.getPlaces().toString().equals(label)) {
                labelsAmount.addAndGet(1);
                if (reutersEntity.getPlaces().equals(strings)) {
                    correctReuters.addAndGet(1);
                }
            }
            recalls.add(correctReuters.doubleValue() / labelsAmount.doubleValue());
        })));

        return recalls.stream().reduce(Double::sum).get() / recalls.size();
    }

}
