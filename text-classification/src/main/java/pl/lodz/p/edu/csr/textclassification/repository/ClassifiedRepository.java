package pl.lodz.p.edu.csr.textclassification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ClassifiedEntity;

public interface ClassifiedRepository extends JpaRepository<ClassifiedEntity, Long> {


}
