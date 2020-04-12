package pl.lodz.p.edu.csr.textclassification.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum DataBreakdown {

    // L[10]T[90] - 10% learning data / 90% testing data

    L10T90(1), L20T80(2), L30T70(3),
    L40T60(4), L50T50(5), L60T40(6),
    L70T30(7), L80T20(8), L90T10(9);

    private final int quantity;

    DataBreakdown(int i) {
        this.quantity = i;
    }

    public static List<DataGroup> getLearningGroups(DataBreakdown dataBreakdown) {
        return Arrays.stream(DataGroup.values())
                .limit(dataBreakdown.getQuantity())
                .collect(Collectors.toList());
    }

    public static List<DataGroup> getTestingGroups(DataBreakdown dataBreakdown) {
        return Arrays.stream(DataGroup.values())
                .skip(dataBreakdown.getQuantity())
                .collect(Collectors.toList());
    }

    public static List<DataGroup> getLearningGroups(int i) {
        return Arrays.stream(DataGroup.values())
                .limit(i%10)
                .collect(Collectors.toList());
    }

    public static List<DataGroup> getTestingGroups(int i) {
        return Arrays.stream(DataGroup.values())
                .skip(i%10)
                .collect(Collectors.toList());
    }

}
