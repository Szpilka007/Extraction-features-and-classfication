package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class ProportionUniqueWordsCommonWordsTest {

    double expectedValue = 2.0;

    ArrayList<String> uniqueWords = new ArrayList<>(Arrays.asList("war","love","magic","air"));
    ArrayList<String> commonWords = new ArrayList<>(Arrays.asList("world","app"));
    ProportionUniqueWordsCommonWords proportionUniqueWordsCommonWords = new ProportionUniqueWordsCommonWords();


    @Test
    public void extractProportion(){
        double actual = proportionUniqueWordsCommonWords.extract(" ", uniqueWords, commonWords);
        Assert.assertEquals(expectedValue ,actual, 0.1);
    }
}
