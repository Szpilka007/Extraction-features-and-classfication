package pl.lodz.p.edu.csr.textclassification.service.utils;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.edu.csr.textclassification.model.ElementType;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.repository.ReutersRepository;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

@Data
@Component
public class XmlParser {

    @Autowired
    public ReutersRepository reutersRepository;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    DateTimeFormatter dtFormatter = new DateTimeFormatterBuilder().parseCaseInsensitive().
            appendPattern("d-MMM-yyyy HH:mm:ss.SS").toFormatter();
//    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("d-MMM-yyyy HH:mm:ss.SS");


    public InputStream loadFileFromResources(String path) {
        InputStream inputStream = getClass().getResourceAsStream(path);
        return inputStream;
    }

    @Transactional
    public void deleteAllReutersFromDB() {
        reutersRepository.deleteAll();
    }

    @Transactional
    public void migrateReutersToDatabase(String relatedPath) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(loadFileFromResources(relatedPath), StandardCharsets.ISO_8859_1))) {
            StringBuilder buffer = new StringBuilder(1024);
            String line = null;

            while ((line = reader.readLine()) != null) { // when we see a closing reuters tag, flush the file
                if (!line.contains("</REUTERS")) {
                    // Replace the SGM escape sequences
                    buffer.append(line).append(' '); // accumulate the strings for now,
                    // then apply regular expression to
                    // get the pieces,
                } else {

                    // decode the metacharacters
                    buffer = new StringBuilder(StringEscapeUtils.unescapeHtml4(buffer.toString()));

                    // prepare one reuters from buffer string
                    ReutersEntity reutersEntity = prepareReuters(buffer.toString());

                    // transaction (save one reuters in db)
                    reutersRepository.save(reutersEntity);

                    // Clear
                    buffer.setLength(0);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ReutersEntity prepareReuters(String rawText) {
        // clear text
        rawText = StringEscapeUtils.unescapeHtml4(rawText); // decode special characters
        String xmlText = rawText.replace("\n", " "); // delete enters

        // declare empty variables when element do not exist
        LocalDateTime date = null;
        List<String> topics = new ArrayList<>(), places = new ArrayList<>(), people = new ArrayList<>();
        List<String> orgs = new ArrayList<>(), exchanges = new ArrayList<>(), companies = new ArrayList<>();
        String title = null, author = null, dateline = null, body = null;

        // extract and prepare date reuters
        List<String> dateInText = extractElement(xmlText, ElementType.DATE);
        if (!dateInText.isEmpty()) {
            // DateTimeFormatter przestał działać i nikt nie wie dlaczego
            // Domyślnie wrzucony będzie tutaj null
        }

        // extract and prepare topics reuters
        List<String> topicsInText = extractElement(xmlText, ElementType.TOPICS);
        if (!topicsInText.isEmpty()) {
            topics = extractElement(topicsInText.stream().findFirst().get().trim(), ElementType.SIMPLE);
        }

        // extract and prepare places reuters
        List<String> placesInText = extractElement(xmlText, ElementType.PLACES);
        if (!placesInText.isEmpty()) {
            places = extractElement(placesInText.stream().findFirst().get().trim(), ElementType.SIMPLE);
        }

        // extract and prepare people reuters
        List<String> peopleInText = extractElement(xmlText, ElementType.PEOPLE);
        if (!peopleInText.isEmpty()) {
            people = extractElement(peopleInText.stream().findFirst().get().trim(), ElementType.SIMPLE);
        }

        // extract and prepare orgs reuters
        List<String> orgsInText = extractElement(xmlText, ElementType.ORGS);
        if (!orgsInText.isEmpty()) {
            orgs = extractElement(orgsInText.stream().findFirst().get().trim(), ElementType.SIMPLE);
        }

        // extract and prepare exchanges reuters
        List<String> exchangesInText = extractElement(xmlText, ElementType.EXCHANGES);
        if (!exchangesInText.isEmpty()) {
            exchanges = extractElement(exchangesInText.stream().findFirst().get().trim(), ElementType.SIMPLE);
        }

        // extract and prepare companies reuters
        List<String> companiesInText = extractElement(xmlText, ElementType.COMPANIES);
        if (!companiesInText.isEmpty()) {
            companies = extractElement(companiesInText.stream().findFirst().get().trim(), ElementType.SIMPLE);
        }

        // extract and prepare title reuters
        List<String> titleInText = extractElement(xmlText, ElementType.TITLE);
        if (!titleInText.isEmpty()) {
            title = titleInText.stream().findFirst().get().trim();
        }

        // extract and prepare author reuters
        List<String> authorInText = extractElement(xmlText, ElementType.AUTHOR);
        if (!authorInText.isEmpty()) {
            author = authorInText.stream().findFirst().get().trim();
        }

        // extract and prepare dateline reuters
        List<String> datelineInText = extractElement(xmlText, ElementType.DATELINE);
        if (!datelineInText.isEmpty()) {
            dateline = datelineInText.stream().findFirst().get().trim();
        }

        // extract and prepare body reuters
        List<String> bodyInText = extractElement(xmlText, ElementType.BODY);
        if (!bodyInText.isEmpty()) {
            body = bodyInText.stream().findFirst().get().trim();
            body = StringUtils.normalizeSpace(body);
        }

        ReutersEntity reutersEntity = new ReutersEntity();

        reutersEntity.setDate(date);
        reutersEntity.setTopics(topics);
        reutersEntity.setPlaces(places);
        reutersEntity.setPeople(people);
        reutersEntity.setOrgs(orgs);
        reutersEntity.setExchanges(exchanges);
        reutersEntity.setCompanies(companies);
        reutersEntity.setTitle(title);
        reutersEntity.setAuthor(author);
        reutersEntity.setDateline(dateline);
        reutersEntity.setBody(body);

        // GG: Tutaj pewnie mogłem użyć builder'a ale coś mi się gryzło z encjami
        // GG: Było późno jak to pisałem, może masz jakiś pomysł?

        return reutersEntity;
    }

    public List<String> extractElement(String text, ElementType elementType) {
        List<String> result = new ArrayList<>();
        Matcher matcher_element = elementType.getPattern().matcher(text);
        while (matcher_element.find()) {
            for (int k = 1; k <= matcher_element.groupCount(); k++) {
                if (matcher_element.group(k) != null) {
                    result.add(matcher_element.group(k));
                }
            }
        }
        return result;
    }

}
