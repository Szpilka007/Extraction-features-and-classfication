package pl.lodz.p.edu.csr.textclassification.service.utils;

import lombok.Data;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.stemmer.PorterStemmer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TextProcessor {

    private InputStream inputStreamPOSTagger = getClass().getResourceAsStream("/models/en-pos-maxent.bin");
    private InputStream inputStreamTokenizer = getClass().getResourceAsStream("/models/en-token.bin");
    private InputStream inputStreamLemmatizer = getClass().getResourceAsStream("/models/en-lemmatizer.dict");
    private InputStream inputStreamStopWords = getClass().getResourceAsStream("/models/stopwords_en.txt");

    private TokenizerModel tokenizerModel = new TokenizerModel(inputStreamTokenizer);
    private TokenizerME tokenizerME = new TokenizerME(tokenizerModel);

    private POSModel posModel = new POSModel(inputStreamPOSTagger);
    private POSTaggerME posTaggerME = new POSTaggerME(posModel);

    private DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(inputStreamLemmatizer);

    private PorterStemmer porterStemmer = new PorterStemmer();

    private List<String> listOfStopWords = IOUtils.readLines(inputStreamStopWords, "UTF-8");

    public TextProcessor() throws IOException {
    }

    // Tutaj mamy tokenizację, stoplistę, stemizację
    public List<String> prepare(String text) {
        String[] tokens = tokenizerME.tokenize(text);
//        List<String> stemed = Arrays.asList(tokens).
//        System.out.println(Arrays.toString(tokens));
//        Arrays.asList(tokens).forEach(a->porterStemmer.stem(a));
//        System.out.println(Arrays.toString(tokens));
        return Arrays.stream(tokens)
                .filter(a -> !listOfStopWords.contains(a))
                .collect(Collectors.toList());
    }

}
