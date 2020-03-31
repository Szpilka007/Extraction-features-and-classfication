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
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Reuters Entity Model", description = "Reuters in JPA Entity structure")
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
    private List<String> topics;
    @ElementCollection
    private List<String> places;
    @ElementCollection
    private List<String> people;
    @ElementCollection
    private List<String> orgs;
    @ElementCollection
    private List<String> exchanges;
    @ElementCollection
    private List<String> companies;
    private String title;
    private String author;
    private String dateline;

    @Column(columnDefinition = "TEXT")
    private String body;

}