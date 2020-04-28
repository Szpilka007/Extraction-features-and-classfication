package pl.lodz.p.edu.csr.textclassification.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.edu.csr.textclassification.model.Feature;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
import pl.lodz.p.edu.csr.textclassification.repository.FeaturesRepository;
import pl.lodz.p.edu.csr.textclassification.repository.ReutersRepository;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.extractors.Extractor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Predicate;

@Service
@Data
public class ExtractionService {

    private final List<Extractor> extractorList;

    @Autowired
    private ReutersRepository reutersRepository;

    @Autowired
    private FeaturesRepository featuresRepository;

    @Autowired
    public ExtractionService(List<Extractor> extractorList) {
        this.extractorList = Collections.unmodifiableList(extractorList);
    }

    @Transactional()
    public String extractFeature(UUID reuters_uuid) {
        LocalDateTime now = LocalDateTime.now();
        ReutersEntity reuters = reutersRepository.findReutersEntityByUuid(reuters_uuid);
        reuters.getFeatures().clear();
        for (Extractor extractor : extractorList) {
            FeatureEntity fe = extractor.extract(reuters);
            reuters.getFeatures().add(fe);
            featuresRepository.save(fe);
        }
        reutersRepository.save(reuters);
        return reuters_uuid.toString() + " | " + now.until(LocalDateTime.now(), ChronoUnit.SECONDS) + " seconds.";
    }

    @Transactional
    public String extractAllFeatures() {
        LocalDateTime now = LocalDateTime.now();
        List<UUID> allReutersUUID = reutersRepository.getAllReutersUUID();
        System.out.println("Extraction of article features for the entire database began.");
        System.out.println("This may take a while ...");
        for (int i = 0; i < allReutersUUID.size(); i++) {
            if (i % 100 == 0) {
                System.out.println(String.format("PROGRESS %6.2f %% | DURATION %05d sec",
                        (double) i / (double) allReutersUUID.size() * 100.0,
                        now.until(LocalDateTime.now(), ChronoUnit.SECONDS)
                ));
            }
            extractFeature(UUID.fromString(allReutersUUID.get(i).toString()));
        }
        System.out.println(String.format("PROGRESS %6.2f %% | DURATION %05d sec",
                100.0,
                now.until(LocalDateTime.now(), ChronoUnit.SECONDS)
        ));
        return "The extraction of the features was successful! It lasted " +
                now.until(LocalDateTime.now(), ChronoUnit.SECONDS) + " seconds!";
    }

    public String stats() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Amount of reuters: ").append(reutersRepository.count()).append("\n");
        stringBuilder.append("=============================================\n");
        List<String> listOfPlaces = reutersRepository.distinctAllPlaces();
        listOfPlaces.forEach(i -> stringBuilder.append(i).append(" = ")
                .append(reutersRepository.countReutersWithPlaces(i)).append("\n"));
        return stringBuilder.toString();
    }

    public void dropFeatures() {
        featuresRepository.deleteAll();
    }

    public String checkStatusForFeatures() {
        // size / quantity reuters with this size
        Map<Integer, Integer> map = new HashMap<>();
        for (ReutersEntity t : reutersRepository.findAll()) {
            Integer val = map.get(t.getFeatures().size());
            map.put(t.getFeatures().size(), val == null ? 1 : val + 1);
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            sb.append("SIZE: ").append(entry.getKey()).append(" | QUANTITY: ").append(entry.getValue()).append("\n");
        }
        sb.append("==============================\n").append("BROKEN REUTERS: \n\n");
        reutersRepository.findAll().stream()
                .filter(i -> i.getFeatures().size() == 0)
                .forEach(i -> sb.append(i.getUuid()).append("\n\n").append(i.getBody()).append("\n\n\n"));
        return sb.toString();
    }

    @Transactional
    public String normalizeAllFeatures(){
        LocalDateTime now = LocalDateTime.now();
        List<FeatureEntity> features = featuresRepository.findAll();
        List<FeatureType> featureTypes = Arrays.asList(FeatureType.values());
        for(FeatureType featureType : featureTypes){
            Double max = getMaxValueForFeatures(featureType);
            Double min = getMinValueForFeatures(featureType);
            System.out.println(featureType.toString()+" = ["+min+","+max+"]");
            features.stream()
                    .filter(i -> i.getFeatureType().equals(featureType))
                    .forEach(i -> i.setValue((i.getValue()-min)/(max-min)));
            System.out.println((double)featureTypes.indexOf(featureType)/(double)featureTypes.size()+" %");
        }
        featuresRepository.saveAll(features);
        return "Data normalization completed! It took ["+now.until(LocalDateTime.now(),ChronoUnit.SECONDS)+"] seconds.";
    }

    private Double getMaxValueForFeatures(FeatureType featureType){
        return featuresRepository.findAll().stream()
                .filter(i -> i.getFeatureType().equals(featureType))
                .map(FeatureEntity::getValue)
                .max(Double::compare).orElse(0.0);
    }

    private Double getMinValueForFeatures(FeatureType featureType){
        return featuresRepository.findAll().stream()
                .filter(i -> i.getFeatureType().equals(featureType))
                .map(FeatureEntity::getValue)
                .min(Double::compare).orElse(0.0);
    }

}
