//package pl.lodz.p.edu.csr.textclassification.service.extractors;
//
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//@Component
//public class SumUniqueWordsDivideAmountWords implements Extractor {
//
//
//    @Override
//    public Double extract(String body, ArrayList<String> uniqueWords, ArrayList<String> commonWords) {
//
//        ArrayList<String> listOfWords = new ArrayList<>(Arrays.asList(body.split(" ")));
//        final int[] uniqueWordAmount = {0};
//        listOfWords.forEach(word -> {
//            uniqueWords.forEach(commonWord ->{
//                if(commonWord.equals(word)){
//                    uniqueWordAmount[0]++;
//                }
//            });
//        });
//        return ((double) uniqueWordAmount[0] / (double)listOfWords.size());
//    }
//}
