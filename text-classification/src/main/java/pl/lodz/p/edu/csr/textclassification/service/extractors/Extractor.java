package pl.lodz.p.edu.csr.textclassification.service.extractors;

import java.util.ArrayList;

public interface Extractor {

    Double extract(String body, ArrayList<String> uniqueWords, ArrayList<String> commonWords);
}
