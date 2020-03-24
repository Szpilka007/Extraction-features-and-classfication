package pl.lodz.p.edu.csr.textclassification.service.extractors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SumUniqueWordsDivideParagraph implements Extractor {


    @Override
    public Integer extract(String body) {
        return 0;

    }
}
