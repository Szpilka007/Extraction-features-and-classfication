package pl.lodz.p.edu.csr.textclassification.repository.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
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

    public LocalDateTime getDate() {
        return date;
    }

    public ReutersEntity setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ReutersEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getBody() {
        return body;
    }

    public ReutersEntity setBody(String body) {
        this.body = body;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ReutersEntity setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getDateline() {
        return dateline;
    }

    public ReutersEntity setDateline(String dateline) {
        this.dateline = dateline;
        return this;
    }

    public List<String> getTopics() {
        return topics;
    }

    public ReutersEntity setTopics(List<String> topics) {
        this.topics = topics;
        return this;
    }

    public List<String> getPlaces() {
        return places;
    }

    public ReutersEntity setPlaces(List<String> places) {
        this.places = places;
        return this;
    }

    public List<String> getPeople() {
        return people;
    }

    public ReutersEntity setPeople(List<String> people) {
        this.people = people;
        return this;
    }

    public List<String> getOrgs() {
        return orgs;
    }

    public ReutersEntity setOrgs(List<String> orgs) {
        this.orgs = orgs;
        return this;
    }

    public List<String> getExchanges() {
        return exchanges;
    }

    public ReutersEntity setExchanges(List<String> exchanges) {
        this.exchanges = exchanges;
        return this;
    }

    public List<String> getCompanies() {
        return companies;
    }

    public ReutersEntity setCompanies(List<String> companies) {
        this.companies = companies;
        return this;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ReutersEntity setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }
}