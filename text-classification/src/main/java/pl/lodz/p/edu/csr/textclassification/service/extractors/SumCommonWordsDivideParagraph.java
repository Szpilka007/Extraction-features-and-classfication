//package pl.lodz.p.edu.csr.textclassification.service.extractors;
//
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//@Component
//public class SumCommonWordsDivideParagraph implements Extractor {
//
//    private static final String PARAGRAPH_SPLIT_REGEX = "\n +";
//
//    @Override
//    public Double extract(String body, ArrayList<String> uniqueWords, ArrayList<String> commonWords) {
//
//        ArrayList<String> paragraphs = new ArrayList<>(Arrays.asList(body.split(PARAGRAPH_SPLIT_REGEX)));
//        ArrayList<String> wordsInText = new ArrayList<>(Arrays.asList(body.split(" ")));
//        final int[] commonWordsInText = {0};
//
//        wordsInText.forEach(word ->{
//            commonWords.forEach(commonWord -> {
//                if(word.equals(commonWord)){
//                    commonWordsInText[0]++;
//                }
//            });
//        });
//
//        System.out.println(commonWordsInText[0]);
//        System.out.println(paragraphs.size());
//
//        return (double) commonWordsInText[0] / paragraphs.size();
//    }
//}
