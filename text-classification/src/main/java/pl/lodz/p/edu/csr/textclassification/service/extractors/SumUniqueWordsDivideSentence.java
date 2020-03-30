//package pl.lodz.p.edu.csr.textclassification.service.extractors;
//
//        import org.springframework.stereotype.Component;
//
//        import java.util.ArrayList;
//        import java.util.Arrays;
//
//@Component
//public class SumUniqueWordsDivideSentence implements Extractor {
//
//
//    @Override
//    public Double extract(String body, ArrayList<String> uniqueWords, ArrayList<String> commonWords) {
//
//        ArrayList<String> sentences = new ArrayList<>(Arrays.asList(body.split(". ")));
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
//        System.out.println(uniqueWordsInText[0]);
//        System.out.println(sentences.size());
//
//        return (double) uniqueWordsInText[0] / sentences.size();
//
//    }
//}
