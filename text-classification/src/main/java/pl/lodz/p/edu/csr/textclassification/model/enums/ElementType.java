package pl.lodz.p.edu.csr.textclassification.model.enums;

import java.util.regex.Pattern;

public enum ElementType {
    DATE("<DATE>(.*?)</DATE>"),
    TOPICS("<TOPICS>(.*?)</TOPICS>"),
    PLACES("<PLACES>(.*?)</PLACES>"),
    PEOPLE("<PEOPLE>(.*?)</PEOPLE>"),
    ORGS("<ORGS>(.*?)</ORGS>"),
    EXCHANGES("<EXCHANGES>(.*?)</EXCHANGES>"),
    COMPANIES("<COMAPNIES>(.*?)</COMAPNIES>"),
    TITLE("<TITLE>(.*?)</TITLE>"),
    BODY("<BODY>(.*?)</BODY>"),
    AUTHOR("<AUTHOR>(.*?)</AUTHOR>"),
    DATELINE("<DATELINE>(.*?)</DATELINE>"),
    ANY("(.*?"),
    SIMPLE("<D>(.*?)</D>");

    private String pattern;

    ElementType(String patternText) {
        this.pattern = patternText;
    }

    public Pattern getPattern() {
        return Pattern.compile(pattern);
    }

}
