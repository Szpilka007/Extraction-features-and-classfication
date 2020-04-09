package pl.lodz.p.edu.csr.textclassification.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
import pl.lodz.p.edu.csr.textclassification.repository.FeaturesRepository;
import pl.lodz.p.edu.csr.textclassification.repository.ReutersRepository;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.extractors.Extractor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
            FeatureEntity fe = FeatureEntity.builder()
                    .featureType(extractor.getFeatureTypeExtractor())
                    .value(extractor.extract(reuters))
                    .build();
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
            } else {
                extractFeature(UUID.fromString(allReutersUUID.get(i).toString()));
            }
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

}
