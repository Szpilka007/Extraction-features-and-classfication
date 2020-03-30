package pl.lodz.p.edu.csr.textclassification.service.utils;


import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TextProcessorTest {

    @Test
    void prepare() throws IOException {

        StringBuilder rawTextStringBuilder = new StringBuilder();
        rawTextStringBuilder.append("Jaguar Plc <JAGR.L> is about to sell its new XJ-6 model on the U.S. And ");
        rawTextStringBuilder.append("Japanese markets and expects a strong reception based on its success ");
        rawTextStringBuilder.append("in the U.K., Chairman Sir John Egan told a news conference. ");
        rawTextStringBuilder.append("Commenting on an 11 pct growth in 1986 group turnover to 830.4 mln stg ");
        rawTextStringBuilder.append("and pre-tax profits at 120.8 mln stg, slightly below 1985's 121.3 mln, Egan ");
        rawTextStringBuilder.append("said Jaguar aimed at an average profit growth of 15 pct per year. However, the ");
        rawTextStringBuilder.append("introduction of the new model had kept this year's pre-tax profit down. ");
        rawTextStringBuilder.append("Jaguar starts selling XJ-6 in the U.S. In May and plans to sell 25,000 of ");
        rawTextStringBuilder.append("its total 47,000 production there in 1987. U.S. Sales now account for 65 pct ");
        rawTextStringBuilder.append("of total turnover, finance director John Edwards said. A U.S. Price for the ");
        rawTextStringBuilder.append("car has not been set yet, but Edwards said the relatively high car ");
        rawTextStringBuilder.append("prices in dollars of West German competitors offered an \"umbrella\" ");
        rawTextStringBuilder.append("for Jaguar. He added the XJ-6 had also to compete with U.S. Luxury car ");
        rawTextStringBuilder.append("producers which would restrict the car's price. Jaguar hedges a majority ");
        rawTextStringBuilder.append("of its dollar receipts on a 12-month rolling basis and plans to do so for a ");
        rawTextStringBuilder.append("larger part of its receipts for longer periods, John Egan said. ");
        rawTextStringBuilder.append("In the longer term, capital expenditure will amount to 10 pct of net sales. ");
        rawTextStringBuilder.append("Research and development will cost four pct of net sales and training two pct. ");
        rawTextStringBuilder.append("Jaguar builds half of its cars and buys components for the other half. ");
        rawTextStringBuilder.append("The firm is in early stages of considering the building of an own press ");
        rawTextStringBuilder.append("shop in Britain for about 80 mln stg, but Egan said this would take at ");
        rawTextStringBuilder.append("least another three years On the London Stock Exchange, Jaguar's shares ");
        rawTextStringBuilder.append("were last quoted at 591p, down from 611p at yesterday's close, after reporting ");
        rawTextStringBuilder.append("1986 results which were in line with market expectations, dealers said. ");
        rawTextStringBuilder.append("REUTER...");

        TextProcessor textProcessor = new TextProcessor();
        assertDoesNotThrow(() -> textProcessor.prepare(rawTextStringBuilder.toString()));
        // Sprawdzamy tylko czy rzuca wyjątkiem przy ładowaniu, gdyż ...
        // Prawdopodobnie model tokenizacji, stoplisty, stemizacji może się jeszcze zmienić

    }
}