package pl.lodz.p.edu.csr.textclassification.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

@Data
public class Article implements Comparable<Article> {

    private Long id;
    private LocalDateTime date;
    private ArrayList<String> topics;
    private ArrayList<String> places;
    private ArrayList<String> people;
    private ArrayList<String> orgs;
    private ArrayList<String> exchanges;
    private ArrayList<String> companies;
    private String title;
    private String body;
    private String author;
    private String dateline;

    public Article(Long id, LocalDateTime date, ArrayList<String> topics, ArrayList<String> places,
                   ArrayList<String> people, ArrayList<String> orgs, ArrayList<String> exchanges,
                   ArrayList<String> companies, String title, String body, String author, String dateline) {
        this.id = id;
        this.date = date;
        this.topics = topics;
        this.places = places;
        this.people = people;
        this.orgs = orgs;
        this.exchanges = exchanges;
        this.companies = companies;
        this.title = title;
        this.body = body;
        this.author = author;
        this.dateline = dateline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id) &&
                Objects.equals(date, article.date) &&
                Objects.equals(topics, article.topics) &&
                Objects.equals(places, article.places) &&
                Objects.equals(people, article.people) &&
                Objects.equals(orgs, article.orgs) &&
                Objects.equals(exchanges, article.exchanges) &&
                Objects.equals(companies, article.companies) &&
                Objects.equals(title, article.title) &&
                Objects.equals(body, article.body) &&
                Objects.equals(author, article.author) &&
                Objects.equals(dateline, article.dateline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, topics, places, people, orgs, exchanges, companies, title, body, author, dateline);
    }

    @Override
    public int compareTo(Article o) {
        if (this.hashCode() == o.hashCode()) {
            if (this.equals(o)) {
                return 0;
            }
        }
        return -1;
    }
}