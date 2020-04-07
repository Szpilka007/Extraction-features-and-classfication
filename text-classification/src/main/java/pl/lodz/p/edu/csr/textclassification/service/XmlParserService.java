package pl.lodz.p.edu.csr.textclassification.service;

import lombok.Data;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.edu.csr.textclassification.model.enums.ElementType;
import pl.lodz.p.edu.csr.textclassification.repository.ReutersRepository;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.utils.ReutersDateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@Data
@Service
public class XmlParserService {

    @Autowired
    public ReutersRepository reutersRepository;

    public InputStream loadFileFromResources(String path) {
        return getClass().getResourceAsStream(path);
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
            date = ReutersDateTime.parse(dateInText.stream().findFirst().get().trim());
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
//            body = StringUtils.normalizeSpace(body);  // off-paragraphs not working
        }

        return ReutersEntity.builder()
                .date(date).topics(topics).places(places)
                .people(people).orgs(orgs).exchanges(exchanges)
                .companies(companies).title(title).author(author)
                .dateline(dateline).body(body)
                .features(new ArrayList<>())
                .build();
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
