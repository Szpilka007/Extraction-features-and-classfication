package pl.lodz.p.edu.csr.textclassification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReutersRepository extends JpaRepository<ReutersEntity, Long> {

    @Query("SELECT DISTINCT p FROM ReutersEntity r JOIN r.places p")
    List<String> distinctAllPlaces();

    @Query("SELECT COUNT(p) FROM ReutersEntity r JOIN r.places p WHERE p LIKE :place")
    Long countReutersWithPlaces(@Param("place") String place);

    ReutersEntity findReutersEntityByUuid(UUID uuid);

    @Query("SELECT r.uuid FROM ReutersEntity r")
    List<UUID> getAllReutersUUID();
}
