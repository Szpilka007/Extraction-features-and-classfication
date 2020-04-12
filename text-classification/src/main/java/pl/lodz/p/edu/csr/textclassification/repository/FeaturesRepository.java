package pl.lodz.p.edu.csr.textclassification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;

import java.util.UUID;


public interface FeaturesRepository extends JpaRepository<FeatureEntity, Long> {

    FeatureEntity findFeatureEntityByUuid(UUID uuid);

}
