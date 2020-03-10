package pl.lodz.p.edu.csr.textclassification.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureType {

    private int id;
    private String name;
    private String description;

}
