package pl.lodz.p.edu.csr.textclassification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;


public interface FeaturesRepository extends JpaRepository<FeatureEntity, Long> {

}
