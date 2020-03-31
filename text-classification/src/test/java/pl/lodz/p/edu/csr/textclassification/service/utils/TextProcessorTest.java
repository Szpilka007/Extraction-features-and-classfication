package pl.lodz.p.edu.csr.textclassification.service.utils;


import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TextProcessorTest {

    @Test
    void prepare() throws IOException {

        TextProcessor textProcessor = new TextProcessor();
        String rawTextStringBuilder = "Jaguar Plc <JAGR.L> is about to sell its new XJ-6 model on the U.S. And " +
                "Japanese markets and expects a strong reception based on its success " +
                "in the U.K., Chairman Sir John Egan told a news conference. " +
                "Commenting on an 11 pct growth in 1986 group turnover to 830.4 mln stg " +
                "and pre-tax profits at 120.8 mln stg, slightly below 1985's 121.3 mln, Egan " +
                "said Jaguar aimed at an average profit growth of 15 pct per year. However, the " +
                "introduction of the new model had kept this year's pre-tax profit down. " +
                "Jaguar starts selling XJ-6 in the U.S. In May and plans to sell 25,000 of " +
                "its total 47,000 production there in 1987. U.S. Sales now account for 65 pct " +
                "of total turnover, finance director John Edwards said. A U.S. Price for the " +
                "car has not been set yet, but Edwards said the relatively high car " +
                "prices in dollars of West German competitors offered an \"umbrella\" " +
                "for Jaguar. He added the XJ-6 had also to compete with U.S. Luxury car " +
                "producers which would restrict the car's price. Jaguar hedges a majority " +
                "of its dollar receipts on a 12-month rolling basis and plans to do so for a " +
                "larger part of its receipts for longer periods, John Egan said. " +
                "In the longer term, capital expenditure will amount to 10 pct of net sales. " +
                "Research and development will cost four pct of net sales and training two pct. " +
                "Jaguar builds half of its cars and buys components for the other half. " +
                "The firm is in early stages of considering the building of an own press " +
                "shop in Britain for about 80 mln stg, but Egan said this would take at " +
                "least another three years On the London Stock Exchange, Jaguar's shares " +
                "were last quoted at 591p, down from 611p at yesterday's close, after reporting " +
                "1986 results which were in line with market expectations, dealers said. " +
                "REUTER... test tests tested testing";

        assertDoesNotThrow(() -> textProcessor.prepare(rawTextStringBuilder));

        // Sprawdzamy tylko czy rzuca wyjątkiem przy ładowaniu, gdyż ...
        // Prawdopodobnie model tokenizacji, stoplisty, stemizacji może się jeszcze zmienić

    }
}