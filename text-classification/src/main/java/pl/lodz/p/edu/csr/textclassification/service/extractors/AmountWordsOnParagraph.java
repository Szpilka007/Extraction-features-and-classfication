//package pl.lodz.p.edu.csr.textclassification.service.extractors;
//
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//@Component
//public class AmountWordsOnParagraph implements Extractor {
//
//    private static final String PARAGRAPH_SPLIT_REGEX = "\n +";
//
//    @Override
//    public Double extract(String body, ArrayList<String> uniqueWords, ArrayList<String> commonWords) {
//
//        ArrayList<String> paragraphs = new ArrayList<>(Arrays.asList(body.split(PARAGRAPH_SPLIT_REGEX)));
//        ArrayList<String> text = new ArrayList<>(Arrays.asList(body.split(" ")));
//
//        return (double) text.size() / paragraphs.size();
//    }
//}
