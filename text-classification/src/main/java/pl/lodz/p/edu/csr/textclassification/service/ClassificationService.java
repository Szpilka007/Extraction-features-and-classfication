package pl.lodz.p.edu.csr.textclassification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.edu.csr.textclassification.model.enums.DataBreakdown;
import pl.lodz.p.edu.csr.textclassification.model.enums.DataGroup;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
import pl.lodz.p.edu.csr.textclassification.repository.ClassifiedRepository;
import pl.lodz.p.edu.csr.textclassification.repository.ReutersRepository;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ClassifiedEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.classifier.KnnAlgorithm;
import pl.lodz.p.edu.csr.textclassification.service.metrics.Metric;
import pl.lodz.p.edu.csr.textclassification.service.metrics.MetricType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClassificationService {

    private final List<Metric> metricList;
    @Autowired
    KnnAlgorithm knnAlgorithm;
    @Autowired
    ReutersRepository reutersRepository;

    @Autowired
    ClassifiedRepository classifiedRepository;

    @Autowired
    public ClassificationService(List<Metric> metricsList) {
        this.metricList = Collections.unmodifiableList(metricsList);
    }

    public String classifyReutersByReutersUUID(UUID uuid, Double k, DataBreakdown dataBreakdown,
                                               List<FeatureType> usedFeatures, MetricType metric) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        ReutersEntity reuters = reutersRepository.findReutersEntityByUuid(uuid);
        Metric usingMetric = metricList.stream()
                .filter(i -> i.getMetricsType().equals(metric))
                .findFirst().orElse(null);
        List<DataGroup> dataGroupsToLearning = DataBreakdown.getLearningGroups(dataBreakdown);
        List<ReutersEntity> learningData = reutersRepository.findAll().stream()
                .filter(i -> dataGroupsToLearning.contains(i.getDataGroup()))
                .collect(Collectors.toList());
        String result = knnAlgorithm.classifyReuters(k, learningData, reuters, usedFeatures, usingMetric);
        long duration = now.until(LocalDateTime.now(), ChronoUnit.SECONDS);
        return "Reuters [UUID: " + uuid.toString() + "] classified as [" + result + "] in [" + duration + "] seconds.";
    }

    @Transactional
    public String classifyAllReuters(Double k, DataBreakdown dataBreakdown,
                                     List<FeatureType> usedFeatures, MetricType metric, String customProcessName) throws Exception {
        String processName = customProcessName;
        if(customProcessName.equals("")){
            processName = "K[" + k + "]" +
                    "_DataBreakdown[" + DataBreakdown.valueOf(dataBreakdown.toString()) + "]" +
                    "_Metric[" + MetricType.valueOf(metric.toString()) + "]" +
                    "_Features[" + FeatureType.packFeatures(usedFeatures) + "]";
        }
        LocalDateTime start = LocalDateTime.now();
        List<DataGroup> dataGroupsToTesting = DataBreakdown.getTestingGroups(dataBreakdown);
        List<DataGroup> dataGroupsToLearning = DataBreakdown.getLearningGroups(dataBreakdown);
        List<ReutersEntity> learningData = reutersRepository.findAll().stream()
                .filter(i -> dataGroupsToLearning.contains(i.getDataGroup()))
                .collect(Collectors.toList());
        List<UUID> reutersToClassify = reutersRepository.findAll().stream()
                .filter(i -> dataGroupsToTesting.contains(i.getDataGroup()))
                .map(ReutersEntity::getUuid)
                .collect(Collectors.toList());
        Metric usedMetric = metricList.stream()
                .filter(i -> i.getMetricsType() == metric)
                .findFirst().orElse(null);
        for (UUID uuid : reutersToClassify) {
            ReutersEntity reutersEntity = reutersRepository.findReutersEntityByUuid(uuid);
            LocalDateTime now = LocalDateTime.now();
            String label = knnAlgorithm.classifyReuters(k, learningData, reutersEntity, usedFeatures, usedMetric);
            ClassifiedEntity classified = ClassifiedEntity.builder()
                    .dataBreakdown(dataBreakdown)
                    .k(k).metricType(metric)
                    .name(processName)
                    .label(label)
                    .featuresUsed(FeatureType.packFeatures(usedFeatures))
                    .duration(now.until(LocalDateTime.now(), ChronoUnit.SECONDS))
                    .build();
            reutersEntity.getClassified().add(classified);
            classifiedRepository.save(classified);
            reutersRepository.save(reutersEntity);
//            if (reutersToClassify.indexOf(uuid) % 50 == 0) {
//                System.out.println(String.format("PROGRESS %6.2f %% | DURATION %05d sec",
//                        (double) reutersToClassify.indexOf(uuid) / (double) reutersToClassify.size(),
//                        start.until(LocalDateTime.now(), ChronoUnit.SECONDS)
//                ));
//            }
        }
        long duration = start.until(LocalDateTime.now(), ChronoUnit.SECONDS);
        return "Classified [" + reutersToClassify.size() + "] reuters successful in [" + duration + "].";
    }

    @Transactional
    public String deleteAll() {
        LocalDateTime now = LocalDateTime.now();
        List<ReutersEntity> toDelete = reutersRepository.findAll().stream()
                .filter(i -> !i.getClassified().isEmpty())
                .collect(Collectors.toList());
        for (ReutersEntity reuters : toDelete) {
            List<ClassifiedEntity> classified = reuters.getClassified();
            reuters.getClassified().clear();
            classifiedRepository.deleteAll(classified);
        }
        reutersRepository.saveAll(toDelete);
        return "Deleted all classification data in [" + now.until(LocalDateTime.now(), ChronoUnit.SECONDS) + "] sec.";
    }

    @Transactional
    public String deleteClassificationDataByName(String processName) {
        LocalDateTime now = LocalDateTime.now();
        List<ReutersEntity> toDelete = reutersRepository.findAll();
        for (ReutersEntity reuters : toDelete) {
            List<ClassifiedEntity> classified = reuters.getClassified().stream()
                    .filter(i -> i.getName().equals(processName))
                    .collect(Collectors.toList());
            reuters.getClassified().removeAll(classified);
            classifiedRepository.deleteAll(classified);
        }
        reutersRepository.saveAll(toDelete);
        return "Deleted all classification data in [" + now.until(LocalDateTime.now(), ChronoUnit.SECONDS) + "] sec.";
    }

    @Transactional(readOnly = true)
    public String theBestK(){
        List<String> listOfTests = classifiedRepository.findAll().stream()
                .map(ClassifiedEntity::getName)
                .distinct()
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        List<ReutersEntity> allReuters = reutersRepository.findAll();
        for(String name : listOfTests){
            Map<String, String> orgClass = new HashMap<>();
            for(ReutersEntity reuters : allReuters){
                if(reuters.getClassified().isEmpty()) continue;
                Optional<String> classed = reuters.getClassified().stream()
                        .filter(i -> i.getName().equals(name))
                        .map(ClassifiedEntity::getLabel)
                        .findAny();
                classed.ifPresent(s -> orgClass.put(reuters.getPlaces().get(0), s));

            }
//            sb.append(orgClass.values()).append("\n");
            long correct = orgClass.entrySet().stream().filter(i-> i.getKey().equals(i.getValue())).count();
            double percent = (double) correct / (double) orgClass.entrySet().size() * 100.0;
//            if(percent > 33.0){
                sb.append(name).append(" | Effectiveness = ").append(percent).append("\n");
//            }
        }
//        sb.append(classifiedRepository.findAll().stream().map(i -> i.getLabel()).collect(Collectors.toList()).toString());
        return sb.toString();
    }

}
