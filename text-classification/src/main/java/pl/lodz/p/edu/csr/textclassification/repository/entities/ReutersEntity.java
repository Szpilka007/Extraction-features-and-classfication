package pl.lodz.p.edu.csr.textclassification.repository.entities;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Reuters Entity Model", description = "Reuters in JPA Entity structure")
@Table(name="REUTERS")
public class ReutersEntity {
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
    @CollectionTable(name="R_TOPICS", joinColumns = @JoinColumn(name="reuters_id"))
    @Column(name = "topic")
    private List<String> topics;

    @ElementCollection
    @CollectionTable(name="R_PLACES", joinColumns = @JoinColumn(name="reuters_id"))
    @Column(name = "place")
    private List<String> places;

    @ElementCollection
    @CollectionTable(name="R_PEOPLE", joinColumns = @JoinColumn(name="reuters_id"))
    @Column(name = "human")
    private List<String> people;

    @ElementCollection
    @CollectionTable(name="R_ORGS", joinColumns = @JoinColumn(name="reuters_id"))
    @Column(name = "organisation")
    private List<String> orgs;

    @ElementCollection
    @CollectionTable(name="R_EXCHANGES", joinColumns = @JoinColumn(name="reuters_id"))
    @Column(name = "exchange")
    private List<String> exchanges;

    @ElementCollection
    @CollectionTable(name="R_COMPANIES", joinColumns = @JoinColumn(name="reuters_id"))
    @Column(name = "company")
    private List<String> companies;

    private String title;
    private String author;
    private String dateline;

    @Column(columnDefinition = "TEXT")
    private String body;

    @OneToMany(mappedBy="reuters")
    private Set<FeatureEntity> features;

}