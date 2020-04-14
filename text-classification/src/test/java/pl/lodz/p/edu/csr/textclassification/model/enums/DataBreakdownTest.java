package pl.lodz.p.edu.csr.textclassification.model.enums;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataBreakdownTest {

    @Test
    void getLearningGroupsByDataBreakdown() {
        // 30 learning 70 testing A, B, C
        List<DataGroup> actual = DataBreakdown.getLearningGroups(DataBreakdown.L30T70);
        List<DataGroup> expected = Arrays.asList(DataGroup.A,DataGroup.B,DataGroup.C);
        Assert.assertEquals(expected,actual);
    }

    @Test
    void getLearningGroupsByInt() {
        // 30 learning 70 testing A, B, C
        List<DataGroup> actual = DataBreakdown.getLearningGroups(3);
        List<DataGroup> expected = Arrays.asList(DataGroup.A,DataGroup.B,DataGroup.C);
        Assert.assertEquals(expected,actual);
    }

    @Test
    void getTestingGroupsByDataBreakdown() {
        // 30 learning 70 testing D, E, F, G, H, I, J
        List<DataGroup> actual = DataBreakdown.getTestingGroups(DataBreakdown.L30T70);
        List<DataGroup> expected = Arrays.asList(DataGroup.D, DataGroup.E, DataGroup.F,
                DataGroup.G, DataGroup.H, DataGroup.I, DataGroup.J);
        Assert.assertEquals(expected,actual);
    }

    @Test
    void getTestingGroupsByInt() {
        // 30 learning 70 testing A, B, C
        List<DataGroup> actual = DataBreakdown.getTestingGroups(3);
        List<DataGroup> expected = Arrays.asList(DataGroup.D, DataGroup.E, DataGroup.F,
                DataGroup.G, DataGroup.H, DataGroup.I, DataGroup.J);
        Assert.assertEquals(expected,actual);
    }
}