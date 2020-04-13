package pl.lodz.p.edu.csr.textclassification.service.classifier;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import pl.lodz.p.edu.csr.textclassification.model.enums.DataBreakdown;
import pl.lodz.p.edu.csr.textclassification.model.enums.DataGroup;
import pl.lodz.p.edu.csr.textclassification.model.enums.FeatureType;
import pl.lodz.p.edu.csr.textclassification.repository.ReutersRepository;
import pl.lodz.p.edu.csr.textclassification.repository.entities.FeatureEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;
import pl.lodz.p.edu.csr.textclassification.service.metrics.EuclideanDistance;
import pl.lodz.p.edu.csr.textclassification.service.metrics.Metric;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

/*
    Let's treat articles as objects having their location on the Euclidean plane with two features.
    (labels A & B - learningData) (our point to classify = C - testingData)
    (In example: X-axis as AL & Y-axis as AKOP)

    POINTS:

    A1 [2,3]    A2 [7,2]    A3 [8,7]
    B1 [4,2]    B2 [5,7]    B3 [8,4]
                C0 [3,4]

    FOR K=3.0 WINNER IS A AND B (IN RESULT RANDOM LABEL)
    FOR K=5.0 WINNER IS B (ALWAYS)
     */

@RunWith(MockitoJUnitRunner.class)
class KnnAlgorithmTest {

    @Mock
    ReutersRepository reutersRepository;

    @InjectMocks
    KnnAlgorithm knnAlgorithm;

    @Mock
    Metric metric;

    List<FeatureType> usingFeatures = Arrays.asList(FeatureType.AL, FeatureType.AKOP);

    // Features Values
    private FeatureEntity A1X = FeatureEntity.builder().featureType(FeatureType.AL).value(2.0).build();
    private FeatureEntity A2X = FeatureEntity.builder().featureType(FeatureType.AL).value(7.0).build();
    private FeatureEntity A3X = FeatureEntity.builder().featureType(FeatureType.AL).value(8.0).build();
    private FeatureEntity B1X = FeatureEntity.builder().featureType(FeatureType.AL).value(4.0).build();
    private FeatureEntity B2X = FeatureEntity.builder().featureType(FeatureType.AL).value(5.0).build();
    private FeatureEntity B3X = FeatureEntity.builder().featureType(FeatureType.AL).value(8.0).build();
    private FeatureEntity C0X = FeatureEntity.builder().featureType(FeatureType.AL).value(3.0).build();
    private FeatureEntity A1Y = FeatureEntity.builder().featureType(FeatureType.AKOP).value(3.0).build();
    private FeatureEntity A2Y = FeatureEntity.builder().featureType(FeatureType.AKOP).value(2.0).build();
    private FeatureEntity A3Y = FeatureEntity.builder().featureType(FeatureType.AKOP).value(7.0).build();
    private FeatureEntity B1Y = FeatureEntity.builder().featureType(FeatureType.AKOP).value(2.0).build();
    private FeatureEntity B2Y = FeatureEntity.builder().featureType(FeatureType.AKOP).value(7.0).build();
    private FeatureEntity B3Y = FeatureEntity.builder().featureType(FeatureType.AKOP).value(4.0).build();
    private FeatureEntity C0Y = FeatureEntity.builder().featureType(FeatureType.AKOP).value(4.0).build();

    // Reuters
    private ReutersEntity A1 = ReutersEntity.builder().dataGroup(DataGroup.A)
            .features(Arrays.asList(A1X, A1Y)).places(Collections.singletonList("A")).build();
    private ReutersEntity A2 = ReutersEntity.builder().dataGroup(DataGroup.A)
            .features(Arrays.asList(A2X, A2Y)).places(Collections.singletonList("A")).build();
    private ReutersEntity A3 = ReutersEntity.builder().dataGroup(DataGroup.A)
            .features(Arrays.asList(A3X, A3Y)).places(Collections.singletonList("A")).build();
    private ReutersEntity B1 = ReutersEntity.builder().dataGroup(DataGroup.B)
            .features(Arrays.asList(B1X, B1Y)).places(Collections.singletonList("B")).build();
    private ReutersEntity B2 = ReutersEntity.builder().dataGroup(DataGroup.B)
            .features(Arrays.asList(B2X, B2Y)).places(Collections.singletonList("B")).build();
    private ReutersEntity B3 = ReutersEntity.builder().dataGroup(DataGroup.B)
            .features(Arrays.asList(B3X, B3Y)).places(Collections.singletonList("B")).build();
    private ReutersEntity C0 = ReutersEntity.builder().dataGroup(DataGroup.C)
            .features(Arrays.asList(C0X, C0Y)).places(Collections.singletonList("C")).build();

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        this.knnAlgorithm = new KnnAlgorithm(reutersRepository);
        this.metric = new EuclideanDistance();
    }

    @Test
        // result is A or B label
    void classificationForResultEqualForAorB() throws Exception {
        when(reutersRepository.findAll()).thenReturn(Arrays.asList(A1, A2, A3, B1, B2, B3, C0));
        List<DataGroup> dataGroupsToLearning = DataBreakdown.getLearningGroups(DataBreakdown.L20T80);
        List<ReutersEntity> learningData = reutersRepository.findAll().stream()
                .filter(i -> dataGroupsToLearning.contains(i.getDataGroup()))
                .collect(Collectors.toList());
        List<String> actual = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            actual.add(knnAlgorithm.classifyReuters(3.0, learningData, C0, usingFeatures, metric));
        }
        System.out.println(actual);

    }

    @Test
        // result is B (always)
    void classificationForResultEqualForB() throws Exception {
        when(reutersRepository.findAll()).thenReturn(Arrays.asList(A1, A2, A3, B1, B2, B3, C0));
        List<DataGroup> dataGroupsToLearning = DataBreakdown.getLearningGroups(DataBreakdown.L20T80);
        List<ReutersEntity> learningData = reutersRepository.findAll().stream()
                .filter(i -> dataGroupsToLearning.contains(i.getDataGroup()))
                .collect(Collectors.toList());
        List<String> actual = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            actual.add(knnAlgorithm.classifyReuters(5.0, learningData, C0, usingFeatures, metric));
        }
        Assert.assertTrue(actual.contains("B"));
        Assert.assertFalse(actual.contains("A"));
    }

    @Test
    void mostCommonLabel() {
        List<String> labels = Arrays.asList("A", "B", "C", "A", "B");
        List<String> actual = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            actual.add(KnnAlgorithm.mostCommonLabel(labels));
        }
        Assert.assertTrue(actual.contains("A") && actual.contains("B"));
    }
}