package pl.lodz.p.edu.csr.textclassification.service.utils;

import lombok.Data;
import pl.lodz.p.edu.csr.textclassification.model.enums.ReutersDateMonth;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class ReutersDateTime {

    private static Pattern patternDateReuters = Pattern.compile(
            "([0-9]+)-(.*?)-([0-9]{4}) ([0-9]{2}):([0-9]{2}):([0-9]{2}).([0-9]{2})");
    private LocalDateTime localDateTime;

    public static LocalDateTime parse(String text) {
        LocalDateTime parsed = null;
        Matcher matcher = patternDateReuters.matcher(text);
        if (matcher.find()) {
            LocalDate localDate = LocalDate.of(
                    Integer.parseInt(matcher.group(3)),
                    ReutersDateMonth.parseMonth(matcher.group(2)),
                    Integer.parseInt(matcher.group(1)));
            LocalTime localTime = LocalTime.of(
                    Integer.parseInt(matcher.group(4)),
                    Integer.parseInt(matcher.group(5)),
                    Integer.parseInt(matcher.group(6)),
                    Integer.parseInt(matcher.group(7)) * 10000000);
            parsed = LocalDateTime.of(localDate, localTime);
        }
        return parsed;
    }

}
