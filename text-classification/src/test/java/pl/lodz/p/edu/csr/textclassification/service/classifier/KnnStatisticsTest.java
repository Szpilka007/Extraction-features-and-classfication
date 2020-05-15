package pl.lodz.p.edu.csr.textclassification.service.classifier;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class KnnStatisticsTest {

    KnnStatistics knnStatistics = new KnnStatistics();
    private final int[][] matrix;

    /*

    EXAMPLE CONFUSION MATRIX

    +-------------+-------------------------------------------------------------------+
    |             |                          PREDICTED LABELS                         |
    +-------------+--------------+--------------+-----+--------+-----+--------+-------+
    |             |              | west-germany | usa | france | uk  | canada | japan |
    |             +--------------+--------------+-----+--------+-----+--------+-------+
    |             | west-germany | 21           | 5   | 14     | 5   | 0      | 19    |
    |             +--------------+--------------+-----+--------+-----+--------+-------+
    |             | usa          | 405          | 247 | 223    | 187 | 1      | 467   |
    |             +--------------+--------------+-----+--------+-----+--------+-------+
    | REAL LABELS | france       | 15           | 5   | 11     | 5   | 0      | 18    |
    |             +--------------+--------------+-----+--------+-----+--------+-------+
    |             | uk           | 62           | 29  | 24     | 24  | 0      | 77    |
    |             +--------------+--------------+-----+--------+-----+--------+-------+
    |             | canada       | 24           | 12  | 12     | 10  | 1      | 46    |
    |             +--------------+--------------+-----+--------+-----+--------+-------+
    |             | japan        | 26           | 6   | 27     | 4   | 0      | 45    |
    +-------------+--------------+--------------+-----+--------+-----+--------+-------+

     */

    public KnnStatisticsTest() {
        this.matrix = new int[][]{
                {21, 5, 14, 5, 0, 19},
                {405, 247, 223, 187, 1, 467},
                {15, 5, 11, 5, 0, 18},
                {62, 29, 24,24, 0, 77},
                {24, 12, 12, 10, 1, 46},
                {26, 6, 27, 4, 0, 45},
        };
    }

    @Test
    void getTruePositives() {
        Map<String, Double> actual = knnStatistics.getTruePositives(matrix);

        Map<String, Double> expected = new HashMap<>();
        expected.put("west-germany",21.0);
        expected.put("usa",247.0);
        expected.put("france",11.0);
        expected.put("uk",24.0);
        expected.put("canada",1.0);
        expected.put("japan",45.0);

        for(Map.Entry<String,Double> labelTP : expected.entrySet()){
            Assert.assertEquals(labelTP.getValue(),actual.get(labelTP.getKey()));
        }
    }

    @Test
    void getTrueNegatives() {
        Map<String, Double> actual = knnStatistics.getTrueNegatives(matrix);

        Map<String, Double> expected = new HashMap<>();
        expected.put("west-germany",1481.0);
        expected.put("usa",490.0);
        expected.put("france",1723.0);
        expected.put("uk",1650.0);
        expected.put("canada",1971.0);
        expected.put("japan",1342.0);

        for(Map.Entry<String,Double> labelTP : expected.entrySet()){
            Assert.assertEquals(labelTP.getValue(),actual.get(labelTP.getKey()));
        }
    }

    @Test
    void getFalsePositives() {
        Map<String, Double> actual = knnStatistics.getFalsePositives(matrix);

        Map<String, Double> expected = new HashMap<>();
        expected.put("west-germany",43.0);
        expected.put("usa",1283.0);
        expected.put("france",43.0);
        expected.put("uk",192.0);
        expected.put("canada",104.0);
        expected.put("japan",63.0);

        for(Map.Entry<String,Double> labelTP : expected.entrySet()){
            Assert.assertEquals(labelTP.getValue(),actual.get(labelTP.getKey()));
        }
    }

    @Test
    void getFalseNegatives() {
        Map<String, Double> actual = knnStatistics.getFalseNegatives(matrix);

        Map<String, Double> expected = new HashMap<>();
        expected.put("west-germany",532.0);
        expected.put("usa",57.0);
        expected.put("france",300.0);
        expected.put("uk",211.0);
        expected.put("canada",1.0);
        expected.put("japan",627.0);

        for(Map.Entry<String,Double> labelTP : expected.entrySet()){
            Assert.assertEquals(labelTP.getValue(),actual.get(labelTP.getKey()));
        }
    }

    @Test
    void getAccuracies() {
        Map<String, Double> actual = knnStatistics.getAccuracies(matrix);

        Map<String, Double> expected = new HashMap<>();
        expected.put("west-germany",72.32);
        expected.put("usa",35.48);
        expected.put("france",83.49);
        expected.put("uk",80.60);
        expected.put("canada",94.94);
        expected.put("japan",66.78);

        for(Map.Entry<String,Double> labelTP : expected.entrySet()){
            Assert.assertEquals(labelTP.getValue(),actual.get(labelTP.getKey())*100,0.01);
        }
    }

    @Test
    void getPrecisions() {
        Map<String, Double> actual = knnStatistics.getPrecisions(matrix);

        Map<String, Double> expected = new HashMap<>();
        expected.put("west-germany",0.33);
        expected.put("usa",0.16);
        expected.put("france",0.20);
        expected.put("uk",0.11);
        expected.put("canada",0.0095);
        expected.put("japan",0.42);

        for(Map.Entry<String,Double> labelTP : expected.entrySet()){
            Assert.assertEquals(labelTP.getValue(),actual.get(labelTP.getKey()),0.01);
        }
    }

    @Test
    void getRecalls() {
        Map<String, Double> actual = knnStatistics.getRecalls(matrix);

        Map<String, Double> expected = new HashMap<>();
        expected.put("west-germany",0.038);
        expected.put("usa",0.81);
        expected.put("france",0.035);
        expected.put("uk",0.10);
        expected.put("canada",0.50);
        expected.put("japan",0.067);

        for(Map.Entry<String,Double> labelTP : expected.entrySet()){
            Assert.assertEquals(labelTP.getValue(),actual.get(labelTP.getKey()),0.01);
        }
    }

    @Test
    void calculateAccuracy() {
        double actual = knnStatistics.calculateAccuracy(matrix);
        double expected = 16.8;
        Assert.assertEquals(expected,actual*100,0.1);
    }

    @Test
    void calculatePrecision() {
        double actual = knnStatistics.calculatePrecision(matrix);
        double expected = 20.4917;
        Assert.assertEquals(expected,actual*100,0.001);
    }

    @Test
    void calculateRecall() {
        double actual = knnStatistics.calculateRecall(matrix);
        double expected = 25.9156;
        Assert.assertEquals(expected,actual*100,0.001);
    }
}
