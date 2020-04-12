package pl.lodz.p.edu.csr.textclassification.repository.entities;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import pl.lodz.p.edu.csr.textclassification.model.enums.DataGroup;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Reuters Entity Model", description = "Reuters in JPA Entity structure")
@Table(name = "REUTERS")
@Embeddable
public class ReutersEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID uuid;
    private LocalDateTime date;

    @ElementCollection
    @CollectionTable(name = "R_TOPICS", joinColumns = @JoinColumn(name = "reuters_id"))
    @Column(name = "topic")
    private List<String> topics;

    @ElementCollection
    @CollectionTable(name = "R_PLACES", joinColumns = @JoinColumn(name = "reuters_id"))
    @Column(name = "place")
    private List<String> places;

    @ElementCollection
    @CollectionTable(name = "R_PEOPLE", joinColumns = @JoinColumn(name = "reuters_id"))
    @Column(name = "human")
    private List<String> people;

    @ElementCollection
    @CollectionTable(name = "R_ORGS", joinColumns = @JoinColumn(name = "reuters_id"))
    @Column(name = "organisation")
    private List<String> orgs;

    @ElementCollection
    @CollectionTable(name = "R_EXCHANGES", joinColumns = @JoinColumn(name = "reuters_id"))
    @Column(name = "exchange")
    private List<String> exchanges;

    @ElementCollection
    @CollectionTable(name = "R_COMPANIES", joinColumns = @JoinColumn(name = "reuters_id"))
    @Column(name = "company")
    private List<String> companies;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String dateline;

    @Column(columnDefinition = "TEXT")
    private String body;

    @ElementCollection
    @CollectionTable(name = "R_FEATURES", joinColumns = @JoinColumn(name = "reuters_id"))
    private List<FeatureEntity> features;

    @ElementCollection
    @CollectionTable(name = "R_CLASSIFIED", joinColumns = @JoinColumn(name = "reuters_id"))
    private List<ClassifiedEntity> classified;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_group")
    private DataGroup dataGroup;
}