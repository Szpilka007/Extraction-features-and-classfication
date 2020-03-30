//package pl.lodz.p.edu.csr.textclassification.service.extractors;
//
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//@Component
//public class SumCommonWordsDivideAmountWords implements Extractor {
//
//    @Override
//    public Double extract(String body, ArrayList<String> uniqueWords, ArrayList<String> commonWords) {
//        ArrayList<String> listOfWords = new ArrayList<>(Arrays.asList(body.split(" ")));
//        final int[] commonWordAmount = {0};
//        listOfWords.forEach(word -> {
//            commonWords.forEach(commonWord ->{
//                if(commonWord.equals(word)){
//                    commonWordAmount[0]++;
//                }
//            });
//        });
//        return ((double) commonWordAmount[0] / (double)listOfWords.size());
//    }
//}
