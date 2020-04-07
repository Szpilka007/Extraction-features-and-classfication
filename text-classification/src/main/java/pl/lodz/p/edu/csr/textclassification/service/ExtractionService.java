package pl.lodz.p.edu.csr.textclassification.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
import pl.lodz.p.edu.csr.textclassification.repository.FeaturesRepository;
import pl.lodz.p.edu.csr.textclassification.repository.ReutersRepository;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.extractors.Extractor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Data
public class ExtractionService {

    private final List<Extractor> extractorList;
    private List<String> whitelistPlaces = Arrays.asList("west-germany", "usa", "france", "uk", "canada", "japan");

    @Autowired
    private ReutersRepository reutersRepository;

    @Autowired
    private FeaturesRepository featuresRepository;

    @Autowired
    public ExtractionService(List<Extractor> extractorList) {
        this.extractorList = Collections.unmodifiableList(extractorList);
    }

    @Transactional
    public void test() {
        featuresRepository.save(FeatureEntity.builder()
                .uuid(UUID.randomUUID())
                .featureType(FeatureType.AKOP)
                .value(5.0).build());
    }

    @Transactional
    public void saveFeature(FeatureEntity featureEntity) {
        featuresRepository.save(featureEntity);
    }

    @Transactional()
    public String extractFeature(UUID reuters_uuid) {
        StopWatch timer = new StopWatch(reuters_uuid.toString());
        timer.start();
        ReutersEntity reuters = reutersRepository.findReutersEntityByUuid(reuters_uuid);
        reuters.getFeatures().clear();
        for (Extractor extractor : extractorList) {
            FeatureEntity fe = FeatureEntity.builder()
                    .featureType(extractor.getFeatureTypeExtractor())
                    .value(extractor.extract(reuters))
                    .build();
            reuters.getFeatures().add(fe);
            featuresRepository.save(fe);
        }
        reutersRepository.save(reuters);
        timer.stop();
        return timer.getId() + " | " + timer.getTotalTimeSeconds() + "seconds.";
    }

    @Transactional
    public String extractAllFeatures() {
        StopWatch timer = new StopWatch("Extractor_Timer");
        timer.start();
        List<UUID> allReutersUUID = reutersRepository.getAllReutersUUID();
//        for(UUID uuid : allReutersUUID){
//            System.out.println(uuid.toString());
//        }
        System.out.println("Extraction of article features for the entire database began.");
        System.out.println("This may take a while ...");
        for (int i = 0; i < allReutersUUID.size(); i++) {
            System.out.println(allReutersUUID.get(i));
//            if((i/allReutersUUID.size()) % 5 == 0){
//                System.out.println("=============================================================");
//                System.out.println("Extraction progress: "+(i/allReutersUUID.size())+" %");
//                System.out.println("=============================================================");
//            }
            System.out.println(extractFeature(UUID.fromString(allReutersUUID.get(i).toString())) + " | " + i + "/" + allReutersUUID.size());
        }
        timer.stop();
        return "The extraction of the features was successful! It lasted " + timer.getTotalTimeSeconds() + " seconds!";
    }

    public String prepareDatabase() {
        Predicate<ReutersEntity> bodyIsNull = i -> i.getBody() == null;
        Predicate<ReutersEntity> placesSizeIsNotOne = i -> i.getPlaces().size() != 1;
        Predicate<ReutersEntity> notWhitelist = i -> i.getPlaces().size() == 1 &&
                !whitelistPlaces.contains(i.getPlaces().get(0));

        List<ReutersEntity> reutersToDelete = reutersRepository.findAll().stream()
                .filter(bodyIsNull.or(placesSizeIsNotOne).or(notWhitelist))
                .collect(Collectors.toList());
        reutersRepository.deleteAll(reutersToDelete);
        return "Removed " + reutersToDelete.size() + " reuters from database.";
    }

    public String stats() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Amount of reuters: " + reutersRepository.count() + "\n");
        stringBuilder.append("=============================================\n");
        List<String> listOfPlaces = reutersRepository.distinctAllPlaces();
        listOfPlaces.forEach(i -> stringBuilder.append(i + " = " + reutersRepository.countReutersWithPlaces(i) + "\n"));
        return stringBuilder.toString();
    }

    public void dropFeatures() {
        featuresRepository.deleteAll();
    }

}
