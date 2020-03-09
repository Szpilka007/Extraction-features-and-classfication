package pl.lodz.p.edu.csr.textclassification.repository;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Reuters {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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
    private String body;
    private String author;
    private String dateline;

}