package pl.lodz.p.edu.csr.textclassification.service.utils;

import org.junit.jupiter.api.Test;
import pl.lodz.p.edu.csr.textclassification.model.enums.ElementType;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.XmlParserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class XmlParserServiceTest {

    XmlParserService xmlParserService = new XmlParserService();

    @Test
    void extractTwoElements() {
        String exampleText = "<PLACES><D>usa</D><D>canada</D></PLACES>";

        List<String> expected = Arrays.asList("usa", "canada");

        List<String> actual = xmlParserService.extractElement(exampleText, ElementType.SIMPLE);

        assertEquals(expected, actual);
    }

    @Test
    void extractElementWithoutInternalElements() {
        String exampleText = "<PLACES></PLACES>";

        List<String> expected = new ArrayList<>();

        List<String> actual = xmlParserService.extractElement(exampleText, ElementType.SIMPLE);

        assertEquals(expected, actual);
    }

    @Test
    void prepareReuters() {
        String rawText = "<!DOCTYPE lewis SYSTEM \"lewis.dtd\">\n" +
                "<REUTERS TOPICS=\"YES\" LEWISSPLIT=\"TRAIN\" CGISPLIT=\"TRAINING-SET\" OLDID=\"18419\" NEWID=\"2001\">\n" +
                "<DATE> 5-JAN-1987 09:07:54.17</DATE>\n" +
                "<TOPICS><D>earn</D></TOPICS>\n" +
                "<PLACES><D>uk</D></PLACES>\n" +
                "<PEOPLE></PEOPLE>\n" +
                "<ORGS></ORGS>\n" +
                "<EXCHANGES></EXCHANGES>\n" +
                "<COMPANIES></COMPANIES>\n" +
                "<UNKNOWN> \n" +
                "&#5;&#5;&#5;F\n" +
                "&#22;&#22;&#1;f0986&#31;reute\n" +
                "d f BC-JAGUAR-SEES-STRONG-GR   03-05 0115</UNKNOWN>\n" +
                "<TEXT>&#2;\n" +
                "<TITLE>JAGUAR SEES STRONG GROWTH IN NEW MODEL SALES</TITLE>\n" +
                "<DATELINE>    LONDON, March 5 - </DATELINE><BODY>Jaguar Plc &lt;JAGR.L> is about to sell its\n" +
                "new XJ-6 model on the U.S. And Japanese markets and expects a\n" +
                "strong reception based on its success in the U.K., Chairman Sir\n" +
                "John Egan told a news conference.\n" +
                "    Commenting on an 11 pct growth in 1986 group turnover to\n" +
                "830.4 mln stg and pre-tax profits at 120.8 mln stg, slightly\n" +
                "below 1985's 121.3 mln, Egan said Jaguar aimed at an average\n" +
                "profit growth of 15 pct per year. However, the introduction of\n" +
                "the new model had kept this year's pre-tax profit down.\n" +
                "    Jaguar starts selling XJ-6 in the U.S. In May and plans to\n" +
                "sell 25,000 of its total 47,000 production there in 1987.\n" +
                "    U.S. Sales now account for 65 pct of total turnover,\n" +
                "finance director John Edwards said.\n" +
                "    A U.S. Price for the car has not been set yet, but Edwards\n" +
                "said the relatively high car prices in dollars of West German\n" +
                "competitors offered an \"umbrella\" for Jaguar. He added the XJ-6\n" +
                "had also to compete with U.S. Luxury car producers which would\n" +
                "restrict the car's price.\n" +
                "    Jaguar hedges a majority of its dollar receipts on a\n" +
                "12-month rolling basis and plans to do so for a larger part of\n" +
                "its receipts for longer periods, John Egan said.\n" +
                "    In the longer term, capital expenditure will amount to 10\n" +
                "pct of net sales. Research and development will cost four pct\n" +
                "of net sales and training two pct.\n" +
                "    Jaguar builds half of its cars and buys components for the\n" +
                "other half. The firm is in early stages of considering the\n" +
                "building of an own press shop in Britain for about 80 mln stg,\n" +
                "but Egan said this would take at least another three years\n" +
                "    On the London Stock Exchange, Jaguar's shares were last\n" +
                "quoted at 591p, down from 611p at yesterday's close, after\n" +
                "reporting 1986 results which were in line with market\n" +
                "expectations, dealers said.\n" +
                " REUTER...\n" +
                "&#3;</BODY></TEXT>\n" +
                "</REUTERS>\n" +
                "<REUTERS TOPICS=\"NO\" LEWISSPLIT=\"TRAIN\" CGISPLIT=\"TRAINING-SET\" OLD";

        LocalDate expectedLocalDate = LocalDate.of(1987, 1, 5);
        LocalTime expectedLocalTime = LocalTime.of(9, 7, 54, 170000000);

        String expectedStringBuilder = "Jaguar Plc <JAGR.L> is about to sell its new XJ-6 model on the U.S. And " +
                "Japanese markets and expects a strong reception based on its success in the U.K., Chairman Sir " +
                "John Egan told a news conference.     Commenting on an 11 pct growth in 1986 group turnover to " +
                "830.4 mln stg and pre-tax profits at 120.8 mln stg, slightly below 1985's 121.3 mln, Egan said " +
                "Jaguar aimed at an average profit growth of 15 pct per year. However, the introduction of the " +
                "new model had kept this year's pre-tax profit down.     Jaguar starts selling XJ-6 in the U.S. " +
                "In May and plans to sell 25,000 of its total 47,000 production there in 1987.     U.S. Sales " +
                "now account for 65 pct of total turnover, finance director John Edwards said.     A U.S. Price " +
                "for the car has not been set yet, but Edwards said the relatively high car prices in dollars of " +
                "West German competitors offered an \"umbrella\" for Jaguar. He added the XJ-6 had also to compete " +
                "with U.S. Luxury car producers which would restrict the car's price.     Jaguar hedges a majority " +
                "of its dollar receipts on a 12-month rolling basis and plans to do so for a larger part of its " +
                "receipts for longer periods, John Egan said.     In the longer term, capital expenditure will " +
                "amount to 10 pct of net sales. Research and development will cost four pct of net sales and " +
                "training two pct.     Jaguar builds half of its cars and buys components for the other half. " +
                "The firm is in early stages of considering the building of an own press shop in Britain for " +
                "about 80 mln stg, but Egan said this would take at least another three years     On the London " +
                "Stock Exchange, Jaguar's shares were last quoted at 591p, down from 611p at yesterday's close, " +
                "after reporting 1986 results which were in line with market expectations, dealers said.  REUTER...";

        ReutersEntity expected = ReutersEntity.builder()
                .date(LocalDateTime.of(expectedLocalDate, expectedLocalTime))
                .author(null)
                .body(expectedStringBuilder)
                .companies(new ArrayList<>())
                .dateline("LONDON, March 5 -")
                .exchanges(new ArrayList<>())
                .orgs(new ArrayList<>())
                .people(new ArrayList<>())
                .places(Collections.singletonList("uk"))
                .title("JAGUAR SEES STRONG GROWTH IN NEW MODEL SALES")
                .topics(Collections.singletonList("earn"))
                .build();

        ReutersEntity actual = xmlParserService.prepareReuters(rawText);
        actual.setUuid(expected.getUuid());

        assertEquals(expected, actual);
    }

}