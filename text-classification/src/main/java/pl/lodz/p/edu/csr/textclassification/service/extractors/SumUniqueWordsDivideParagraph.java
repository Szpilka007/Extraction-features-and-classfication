//package pl.lodz.p.edu.csr.textclassification.service.extractors;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//@Component
//public class SumUniqueWordsDivideParagraph implements Extractor {
//
//    private static final String PARAGRAPH_SPLIT_REGEX = "\n +";
//
//    @Override
//    public Double extract(String body, ArrayList<String> uniqueWords, ArrayList<String> commonWords) {
//
//
//        ArrayList<String> paragraphs = new ArrayList<>(Arrays.asList(body.split(PARAGRAPH_SPLIT_REGEX)));
//        ArrayList<String> wordsInText = new ArrayList<>(Arrays.asList(body.split(" ")));
//        final int[] uniqueWordsInText = {0};
//
//        wordsInText.forEach(word ->{
//            uniqueWords.forEach(uniqueWord -> {
//                if(word.equals(uniqueWord)){
//                    uniqueWordsInText[0]++;
//                }
//            });
//        });
//
//        return (double) uniqueWordsInText[0] / paragraphs.size();
//    }
//
//}
