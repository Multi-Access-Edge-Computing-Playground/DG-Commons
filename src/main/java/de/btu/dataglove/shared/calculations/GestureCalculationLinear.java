package de.btu.dataglove.shared.calculations;

import de.btu.dataglove.shared.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class GestureCalculationLinear {
    public static Gesture calculateGestureBoundaries(String nameOfResult, List<Frame> frames, double[] parametersForCalculation) {
        long startTime = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        System.out.println("combining sensordata");
        List<Double>[] combinedSensorData = SharedUtility.combineAllSensorData(frames);
        System.out.println("combining sensordata done");

        double[] averages = new double[SharedConstants.TOTAL_SENSOR_DATA_OF_FRAME];
        for (int i = 0; i < averages.length; i++) {
            averages[i] = calculateAverage(combinedSensorData[i]);
        }

        double[] variances = new double[SharedConstants.TOTAL_SENSOR_DATA_OF_FRAME];
        for (int i = 0; i < variances.length; i++) {
            variances[i] = calculateVariance(combinedSensorData[i], averages[i]);
        }

        double[] standardDeviations = new double[SharedConstants.TOTAL_SENSOR_DATA_OF_FRAME];
        for (int i = 0; i < standardDeviations.length; i++) {
            standardDeviations[i] = calculateStandardDeviation(variances[i]);
        }

        Future<double[]> lowerBoundsFuture = calculateLowerBounds(parametersForCalculation[0], averages, standardDeviations,
                combinedSensorData, executor);
        Future<double[]> upperBoundsFuture = calculateUpperBounds(parametersForCalculation[0], averages, standardDeviations,
                combinedSensorData, executor);

        while (!lowerBoundsFuture.isDone() && !upperBoundsFuture.isDone()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        double[] lowerBounds = null;
        double[] upperBounds = null;
        try {
            lowerBounds = lowerBoundsFuture.get();
            upperBounds = upperBoundsFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        int typeOfGesture = getTypeOfRecording(frames);
        Gesture gesture = new Gesture(nameOfResult, typeOfGesture, Algorithms.STATIC_GESTURE_LINEAR.toInt(), parametersForCalculation, lowerBounds, upperBounds);
        System.out.println("gesture created!");
        long endTime = System.currentTimeMillis();
        System.out.println("time: " + (endTime - startTime));
        return gesture;
    }

    private static Future<double[]> calculateLowerBounds(double parameterForCalculation, double[] averages,
                                                         double[] standardDeviations, List<Double>[] frameData,
                                                         ExecutorService executor) {
        System.out.println("lower start");
        return executor.submit(() -> {
            double[] result = new double[SharedConstants.TOTAL_SENSOR_DATA_OF_FRAME];
            for (int i = 0; i < frameData.length; i++) {
                result[i] = calculateLowerBound(averages[i], standardDeviations[i], parameterForCalculation);
            }
            System.out.println("lower end");
            return result;
        });
    }

    private static Future<double[]> calculateUpperBounds(double parameterForCalculation, double[] averages,
                                                         double[] standardDeviations, List<Double>[] frameData,
                                                         ExecutorService executor) {
        System.out.println("upper start");
        return executor.submit(() -> {
            double[] result = new double[SharedConstants.TOTAL_SENSOR_DATA_OF_FRAME];
            for (int i = 0; i < frameData.length; i++) {
                result[i] = calculateUpperBound(averages[i], standardDeviations[i], parameterForCalculation);
            }
            System.out.println("upper end");
            return result;
        });
    }

    private static double calculateUpperBound(double average, double standardDeviation, double parameterForCalculation) {
        return average + (parameterForCalculation * standardDeviation);
    }

    private static double calculateLowerBound(double average, double standardDeviation, double parameterForCalculation) {
        return average - (parameterForCalculation * standardDeviation);
    }

    public static double calculateAverage(List<Double> values) {
        double sum = 0;
        for (int i = 0; i < values.size(); i++) {
            sum = sum + values.get(i);
        }
        return sum / values.size();

    }

    public static double calculateVariance(List<Double> values, double average) {
        double sum = 0;
        for (int i = 0; i < values.size(); i++) {
            sum = sum + Math.pow(values.get(i) - average, 2);
        }
        return sum / values.size();
    }

    public static double calculateStandardDeviation(double variance) {
        return Math.sqrt(variance);
    }

    private static int getTypeOfRecording(List<Frame> frames) {
        return frames.get(0).typeOfRecording;
    }
}
