package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class SumUniqueWordsDivideSentenceTest {

    double expectedValue = 0.375;

    ArrayList<String> uniqueWords = new ArrayList<>(Arrays.asList("world","the","we","be"));
    ArrayList<String> commonWords = new ArrayList<>(Arrays.asList("sun","bee"));
    SumUniqueWordsDivideSentence sumUniqueWordsDivideSentence = new SumUniqueWordsDivideSentence();
    String text = "    Commenting on an 11 pct growth in 1986 group turnover to\n" +
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
            "restrict the car's price.\n";


//    @Test
//    public void extractScore(){
//        double actual = sumUniqueWordsDivideSentence.extract(text, uniqueWords,commonWords);
//        Assert.assertEquals(expectedValue,actual,0.2);
//    }

}
