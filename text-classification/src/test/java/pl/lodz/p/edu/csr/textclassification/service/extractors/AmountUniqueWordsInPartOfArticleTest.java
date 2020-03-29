package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class AmountUniqueWordsInPartOfArticleTest {

    double expectedValue = 3.0;

    ArrayList<String> uniqueWords = new ArrayList<>(Arrays.asList("we","be"));
    ArrayList<String> commonWords = new ArrayList<>(Arrays.asList("world","app"));
    AmountUniqueWordsInPartOfArticle amountUniqueWordsInPartOfArticle = new AmountUniqueWordsInPartOfArticle();

    String text = "So, can we do anything to protect our data? Or should we just accept that in fact nothing is " +
            "'free' and sharing our data is the price we have to pay for using many online services? As people are " +
            "increasingly aware of and worried about data protection, governments and organisations are taking a more " +
            "active role in protecting privacy. For example, the European Union passed the General Data Protection Law, " +
            "which regulates how personal information is collected online. However, there is still much work to be done.";

    @Test
    public void extractScore(){
        double actual = amountUniqueWordsInPartOfArticle.extract(text, uniqueWords, commonWords);
        Assert.assertEquals(expectedValue, actual, 0.1);
    }

}
