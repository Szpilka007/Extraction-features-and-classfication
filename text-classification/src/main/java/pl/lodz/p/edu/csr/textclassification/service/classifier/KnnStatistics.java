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
        ArrayList<Double> precisions = new ArrayList<>();
        labels.forEach(label -> {
            Integer[] correctReuters = {0};
            Integer[] labelsAmount= {0};
            processedReuters.forEach((reutersEntity, strings) -> {
                        if (strings.get(0).equals(label)) {
                            labelsAmount[0]+=1;
                            if (reutersEntity.getPlaces().get(0).equals(strings.get(0))) {
                                correctReuters[0]+=1;
                            }
                        }
                    });
            if(labelsAmount[0] == 0){
                precisions.add(0.0);
            }else {
                precisions.add((double) correctReuters[0] / labelsAmount[0]);
            }
        });
        return precisions.stream().reduce(Double::sum).get() / precisions.size();
    }

    public double getRecall(Map<ReutersEntity, List<String>> processedReuters) {
        ArrayList<Double> recalls = new ArrayList<>();
        labels.forEach(label -> {
            Integer[] correctReuters = {0};
            Integer[] labelsAmount = {0};
            processedReuters.forEach((reutersEntity, strings) -> {
                if (reutersEntity.getPlaces().get(0).equals(label)) {
                    labelsAmount[0]++;
                    if (reutersEntity.getPlaces().get(0).equals(strings.get(0))) {
                        correctReuters[0]++;
                    }
                }
            });
            if(labelsAmount[0] == 0){
                recalls.add(0.0);
            }else {
                recalls.add((double) correctReuters[0] / labelsAmount[0]);
            }
        });

        return recalls.stream().reduce(Double::sum).get() / recalls.size();
    }

}
