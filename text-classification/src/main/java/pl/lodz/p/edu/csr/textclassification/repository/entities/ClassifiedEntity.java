package pl.lodz.p.edu.csr.textclassification.repository.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import pl.lodz.p.edu.csr.textclassification.model.enums.DataBreakdown;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
import pl.lodz.p.edu.csr.textclassification.service.metrics.MetricType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CLASSIFIED")
public class ClassifiedEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    @Column(name = "parameter_k")
    private Double k;

    @Column(name = "data_proportion")
    @Enumerated(EnumType.STRING)
    private DataBreakdown dataBreakdown;

    @Column(name = "features_used")
    private String featuresUsed;

    @Column(name = "metric_type")
    @Enumerated(EnumType.STRING)
    private MetricType metricType;

    @Column(name = "classification_name")
    private String name;

    @Column(name = "classified_label")
    private String label;

    @Column(name = "duration_sec")
    private long duration;
}
