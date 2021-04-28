package de.btu.dataglove.shared.calculations;

import java.util.List;

public class CalculationsUtil {
    public static double calculateAverage(List<Double> values) {
        double sum = 0;
        for (Double value : values) {
            sum = sum + value;
        }
        return sum / values.size();

    }

    public static double calculateVariance(List<Double> values, double average) {
        double sum = 0;
        for (Double value : values) {
            sum = sum + Math.pow(value - average, 2);
        }
        return sum / values.size();
    }
}
