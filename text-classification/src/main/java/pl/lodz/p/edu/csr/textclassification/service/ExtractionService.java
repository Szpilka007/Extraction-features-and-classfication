package pl.lodz.p.edu.csr.textclassification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.lodz.p.edu.csr.textclassification.service.extractors.Extractor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ExtractionService {

    private final List<Extractor> extractorList;

    @Autowired
    public ExtractionService(List<Extractor> extractorList) {
        this.extractorList = Collections.unmodifiableList(extractorList);
    }

    public void extractFeature(String text/*Obiekt Tekstu do extrakcji */){
        ArrayList<Integer> features = new ArrayList<>();
        extractorList.forEach(extractor -> features.add(extractor.extract(text)));
    }
}
