package de.btu.dataglove.shared;

import de.btu.dataglove.shared.calculations.GestureCalculationNaiveBayes;

public class GestureNaiveBayes extends AbstractGesture {

    private final double threshold;
    private final double[] kappaArray;
    private final double[] circularMeanArray;
    private final double[] accelerationMeanArray;
    private final double[] accelerationVarianceArray;

    public GestureNaiveBayes(String name, int typeOfGesture, int algorithmUsedForCalculation, double[] algorithmParameters, double threshold,
                             double[] kappaArray, double[] circularMeanArray, double[] accelerationMeanArray, double[] accelerationVarianceArray) {
        super(name, typeOfGesture, algorithmUsedForCalculation, algorithmParameters);
        this.threshold = threshold;
        this.kappaArray = kappaArray;
        this.circularMeanArray = circularMeanArray;
        this.accelerationMeanArray = accelerationMeanArray;
        this.accelerationVarianceArray = accelerationVarianceArray;
    }

    @Override
    public double getCorrectnessOfFrame(Frame frame) {
        double sumOfLnNaiveBayes = GestureCalculationNaiveBayes.calculateSumOfLnGaussianNaiveBayes(frame, kappaArray, circularMeanArray,
                accelerationMeanArray, accelerationVarianceArray, TypeOfGesture.get(this.typeOfGesture), false);
        System.out.println("isFrameRecognized sumOfLnNB: " + sumOfLnNaiveBayes);
        if (sumOfLnNaiveBayes < threshold) return 0;
        return sumOfLnNaiveBayes / threshold;
    }

    public double getThreshold() {
        return threshold;
    }

    public double[] getKappaArray() {
        return kappaArray;
    }

    public double[] getCircularMeanArray() {
        return circularMeanArray;
    }

    public double[] getAccelerationMeanArray() {
        return accelerationMeanArray;
    }

    public double[] getAccelerationVarianceArray() {
        return accelerationVarianceArray;
    }
}
