package pl.lodz.p.edu.csr.textclassification.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum FeatureType {

    SUKDAW, SCKDAW, AOSUKDS, AOSCKDS, AOSUKDP, AOSCKDP, PUACK, PUKIPOA, AKOP, AWWTNOSDAK, AWBUK, AL;

    public static List<FeatureType> unpackFeatures(String features) {
        return Arrays.stream(features.split(","))
                .map(FeatureType::valueOf)
                .collect(Collectors.toList());
    }

    public static String packFeatures(List<FeatureType> features){
        return features.stream().map(Enum::toString).collect(Collectors.joining(","));
    }

}
