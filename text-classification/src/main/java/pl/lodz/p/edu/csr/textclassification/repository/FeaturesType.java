package pl.lodz.p.edu.csr.textclassification.repository;

import lombok.Data;
import pl.lodz.p.edu.csr.textclassification.model.FeatureType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class FeaturesType implements Comparable<FeatureType> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime date;


    @Override
    public int compareTo(FeatureType o) {
        if (this.hashCode() == o.hashCode()) {
            if (this.equals(o)) {
                return 0;
            }
        }
        return -1;
    }
}
