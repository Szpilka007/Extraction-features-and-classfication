package pl.lodz.p.edu.csr.textclassification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureType;


public interface FeaturesTypeRepository extends JpaRepository<FeatureType, Long> {

}
