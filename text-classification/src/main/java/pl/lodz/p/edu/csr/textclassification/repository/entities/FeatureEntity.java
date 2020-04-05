package pl.lodz.p.edu.csr.textclassification.repository.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="FEATURES")
public class FeatureEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID uuid;

    @OneToOne
    @JoinColumn(name = "reuters_id", nullable = false)
    private ReutersEntity reuters;

    private FeatureType featureType;
    private Double value;

}
