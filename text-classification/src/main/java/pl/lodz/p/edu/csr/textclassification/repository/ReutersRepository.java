package pl.lodz.p.edu.csr.textclassification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;

@Repository
public interface ReutersRepository extends JpaRepository<ReutersEntity, Long> {

}
