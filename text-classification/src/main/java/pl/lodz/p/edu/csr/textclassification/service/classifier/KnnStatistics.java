package pl.lodz.p.edu.csr.textclassification.service.classifier;


import javafx.util.Pair;
import netscape.javascript.JSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.edu.csr.textclassification.repository.ReutersRepository;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ClassifiedEntity;
import pl.lodz.p.edu.csr.textclassification.repository.entities.ReutersEntity;

import java.util.*;

@Service
public class KnnStatistics {

    @Autowired
    ReutersRepository reutersRepository;

    ArrayList<String> labels = new ArrayList<>(Arrays.asList("west-germany", "usa", "france", "uk", "canada", "japan"));

    /*
    MATRIX MODEL (example)

    +-------------+------------------------------------------------------------------+
    |             |                         PREDICTED LABELS                         |
    +-------------+--------------+--------------+-----+--------+----+--------+-------+
    |             |              | west-germany | usa | france | uk | canada | japan |
    |             +--------------+--------------+-----+--------+----+--------+-------+
    |             | west-germany | 0            | 0   | 0      | 0  | 0      | 0     |
    |             +--------------+--------------+-----+--------+----+--------+-------+
    |             | usa          | 0            | 0   | 0      | 0  | 0      | 0     |
    |             +--------------+--------------+-----+--------+----+--------+-------+
    | REAL LABELS | france       | 0            | 0   | 0      | 0  | 0      | 0     |
    |             +--------------+--------------+-----+--------+----+--------+-------+
    |             | uk           | 0            | 0   | 0      | 0  | 0      | 0     |
    |             +--------------+--------------+-----+--------+----+--------+-------+
    |             | canada       | 0            | 0   | 0      | 0  | 0      | 0     |
    |             +--------------+--------------+-----+--------+----+--------+-------+
    |             | japan        | 0            | 0   | 0      | 0  | 0      | 0     |
    +-------------+--------------+--------------+-----+--------+----+--------+-------+

    The real matrix consists of the order defined in "labels".
     */

    public int[][] generateMatrixConfusion(String classificationName) {
        int[][] matrix = new int[6][6]; // 6 places labels
        List<Pair<String, String>> realAndPredictedList = getPairsRealAndPredicted(classificationName);
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[row].length; column++) {
                int finalColumn = column;
                int finalRow = row;
                matrix[row][column] = (int) realAndPredictedList.stream()
                        .filter(i -> i.getKey().equals(labels.get(finalRow)))
                        .filter(i -> i.getValue().equals(labels.get(finalColumn)))
                        .count();
            }
        }
        return matrix;
    }

    public List<Pair<String, String>> getPairsRealAndPredicted(String classificationName) {
        List<Pair<String, String>> realAndPredictedList = new ArrayList<>();
        for (ReutersEntity reuters : reutersRepository.findAll()) {
            String predicted = reuters.getClassified().stream()
                    .filter(i -> i.getName().equals(classificationName))
                    .map(ClassifiedEntity::getLabel)
                    .findFirst().orElse("unknown");
            realAndPredictedList.add(new Pair(reuters.getPlaces().get(0), predicted));
        }
        return realAndPredictedList;
    }

    public String printMatrixConfusion(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 6; column++) {
                sb.append(matrix[row][column]);
                sb.append(",");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // Its TP (matrix diagonal)
    public Map<String, Double> getTruePositives(int[][] matrix) {
        Map<String, Double> TP = new HashMap<>();
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[row].length; column++) {
                if (row == column) TP.put(labels.get(row), (double) matrix[row][column]);
            }
        }
        return TP;
    }

    // For specific TN -> the sum of all columns and rows excluding that label's column and row
    public Map<String, Double> getTrueNegatives(int[][] matrix) {
        Map<String, Double> TN = new HashMap<>();
        for (String label : labels) {
            double sum = 0.0;
            for (int row = 0; row < matrix.length; row++) {
                for (int column = 0; column < matrix[row].length; column++) {
                    if (labels.indexOf(label) == row || labels.indexOf(label) == column) continue;
                    sum += matrix[row][column];
                }
            }
            TN.put(label, sum);
        }
        return TN;
    }

    // For specific FN -> the sum of values in the corresponding row (excluding the TP)
    public Map<String, Double> getFalseNegatives(int[][] matrix) {
        Map<String, Double> FN = new HashMap<>();
        for (String label : labels) {
            double sum = 0.0;
            for (int row = 0; row < matrix[labels.indexOf(label)].length; row++) {
                if (labels.indexOf(label) == row) continue;
                sum += matrix[row][labels.indexOf(label)];
            }
            FN.put(label, sum);
        }
        return FN;
    }

    // For specific FP -> the sum of values in the corresponding column (excluding the TP)
    public Map<String, Double> getFalsePositives(int[][] matrix) {
        Map<String, Double> FP = new HashMap<>();
        for (String label : labels) {
            double sum = 0.0;
            for (int column = 0; column < matrix[labels.indexOf(label)].length; column++) {
                if (labels.indexOf(label) == column) continue;
                sum += matrix[labels.indexOf(label)][column];
            }
            FP.put(label, sum);
        }
        return FP;
    }

    // Its (TP + TN) / (TP + TN + FP + FN) for each label
    public Map<String, Double> getAccuracies(int[][] matrix) {
        Map<String, Double> accuracies = new HashMap<>();
        for (String label : labels) {
            double TP = getTruePositives(matrix).get(label);
            double TN = getTrueNegatives(matrix).get(label);
            double FP = getFalsePositives(matrix).get(label);
            double FN = getFalseNegatives(matrix).get(label);
            double value = (TP + TN) / (TP + TN + FP + FN);
            if(Double.valueOf(value).isNaN()) value = 0.0;
            accuracies.put(label, value);
        }
        return accuracies;
    }

    // Its TP / (TP + FP) for each label
    public Map<String, Double> getPrecisions(int[][] matrix) {
        Map<String, Double> precisions = new HashMap<>();
        for (String label : labels) {
            double TP = getTruePositives(matrix).get(label);
            double FP = getFalsePositives(matrix).get(label);
            double value = TP / (TP + FP);
            if(Double.valueOf(value).isNaN()) value = 0.0;
            precisions.put(label, value);
        }
        return precisions;
    }

    public Map<String, Double> getRecalls(int[][] matrix) {
        Map<String, Double> recalls = new HashMap<>();
        for (String label : labels) {
            double TP = getTruePositives(matrix).get(label);
            double FN = getFalseNegatives(matrix).get(label);
            double value = TP / (TP + FN);
            if(Double.valueOf(value).isNaN()) value = 0.0;
            recalls.put(label, value);
        }
        return recalls;
    }

    // Its average for sum of all accuracies
    public double calculateAccuracy(int[][] matrix) {
        Map<String,Double> accuracies = this.getTruePositives(matrix);
        double sumOfAll = 0.0;
        for (int[] row : matrix) {
            for (int cell : row) {
                sumOfAll += cell;
            }
        }
        return accuracies.values().stream().reduce(Double::sum).orElse(0.0) / sumOfAll;
    }

    // Its average for all precisions
    public double calculatePrecision(int[][] matrix) {
        Map<String,Double> precisions = this.getPrecisions(matrix);
        return precisions.values().stream().mapToDouble(v -> v).average().orElse(0.0);
    }

    // Its average for all recalls
    public double calculateRecall(int[][] matrix) {
        Map<String,Double> recalls = this.getRecalls(matrix);
        return recalls.values().stream().mapToDouble(v -> v).average().orElse(0.0);
    }
}
