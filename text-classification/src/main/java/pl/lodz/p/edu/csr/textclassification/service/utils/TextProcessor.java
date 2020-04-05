package pl.lodz.p.edu.csr.textclassification.service.utils;

import lombok.Data;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.stemmer.PorterStemmer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Component
public class TextProcessor {

    public TextProcessor() throws IOException {
    }

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

    public List<String> tokenize(String text) {
        return Arrays.asList(tokenizerME.tokenize(text));
    }

    public List<String> stem(List<String> text) {
        return text.stream()
                .map(i -> porterStemmer.stem(i))
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    public List<String> useStopWords(List<String> text) {
        return text.stream()
                .filter(i -> !listOfStopWords.contains(i) || !listOfStopWords.contains(i.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Prepare to extraction
    public List<String> prepare(String text) {
        return Arrays.asList(tokenizerME.tokenize(text)).stream()
                .map(i -> porterStemmer.stem(i)).map(String::toLowerCase)
                .filter(i -> !listOfStopWords.contains(i))
                .collect(Collectors.toList());
    }

    public List<String> prepareWithoutThe(String text){
        return Arrays.asList(tokenizerME.tokenize(text)).stream()
                .map(i -> porterStemmer.stem(i)).map(String::toLowerCase)
                .filter(i -> !listOfStopWords.contains(i) || i.equals("the") || i.equals("."))
                .collect(Collectors.toList());
    }

}
