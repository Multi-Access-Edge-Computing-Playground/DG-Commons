package de.btu.dataglove.shared.calculations;

import de.btu.dataglove.shared.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.DoubleFunction;

/**
 * based on "2021-02-06OnePager_DataGlove_Auswertung_statische_Gesten.docx"
 * and "2020_10_14_OnePager_vonMisesVerteilung.docx"
 */
public class GestureCalculationNaiveBayes {

    private static final double p = 2; //see one pager item 9 of "2020_10_14_OnePager_vonMisesVerteilung.docx"
    private static int numberOfAnglesPerFrame;
    private static int numberOfAccelerationsPerFrame;
    private static int numberOfTotalValuesPerFrame;
    private static final double percentageOfDataToInclude = 95; //see item 23 "2021-02-06OnePager_DataGlove_Auswertung_statische_Gesten.docx"

    private static void initConstants(TypeOfGesture typeOfGesture, boolean useEulerDiffs) {

        if (useEulerDiffs) numberOfAnglesPerFrame = SharedConstants.NUMBER_OF_EULER_ANGLE_DIFFERENCES_PER_HAND * 2;
        else numberOfAnglesPerFrame = SharedConstants.NUMBER_OF_EULER_ANGLES_PER_FRAME;

        switch (typeOfGesture) {
            case STATIC_GESTURE_LEFT:
            case STATIC_GESTURE_RIGHT:
            case DYNAMIC_GESTURE_LEFT:
            case DYNAMIC_GESTURE_RIGHT:
                numberOfAnglesPerFrame = numberOfAnglesPerFrame / 2;
                numberOfAccelerationsPerFrame = SharedConstants.NUMBER_OF_ACCELERATIONS_PER_FRAME / 2;
                numberOfTotalValuesPerFrame = numberOfAnglesPerFrame + numberOfAccelerationsPerFrame;
                break;
            case STATIC_GESTURE_BOTH:
            case DYNAMIC_GESTURE_BOTH:
                numberOfAccelerationsPerFrame = SharedConstants.NUMBER_OF_ACCELERATIONS_PER_FRAME;
                numberOfTotalValuesPerFrame = numberOfAnglesPerFrame + numberOfAccelerationsPerFrame;
        }
    }

    public static GestureNaiveBayes calculateGestureModel(String nameOfResult, List<Frame> frames, double[] parametersForCalculation) {
        return (GestureNaiveBayes) calculateGestureModelImpl(nameOfResult, frames, parametersForCalculation, false);
    }

    public static GestureNaiveBayesEulerDiffs calculateGestureModelWithEulerDiffs(String nameOfResult, List<Frame> frames, double[] parametersForCalculation) {
        return (GestureNaiveBayesEulerDiffs) calculateGestureModelImpl(nameOfResult, frames, parametersForCalculation, true);
    }

    private static AbstractGesture calculateGestureModelImpl(String nameOfResult, List<Frame> frames, double[] parametersForCalculation, boolean useEulerDiffs) {

        TypeOfGesture typeOfGesture = TypeOfGesture.get(frames.get(0).typeOfRecording);
        initConstants(typeOfGesture, useEulerDiffs);

        Algorithms algorithmUsedForCalculation;
        if (useEulerDiffs) algorithmUsedForCalculation = Algorithms.STATIC_GESTURE_NAIVE_BAYES_EULER_DIFFS;
        else algorithmUsedForCalculation = Algorithms.STATIC_GESTURE_NAIVE_BAYES;

        List<double[]> combinedAngles = new LinkedList<>();
        for (Frame frame : frames) {
            if (useEulerDiffs)
                combinedAngles.add(frame.getEulerDiffRepresentation().getAllRelevantAngleDiffs((typeOfGesture)));
            else combinedAngles.add(frame.getEulerRepresentation().getAllRelevantAngles((typeOfGesture)));
        }

        double[] accelerationMeanArray = new double[numberOfAccelerationsPerFrame];
        double[] accelerationVarianceArray = new double[numberOfAccelerationsPerFrame];
        for (int i = 0; i < accelerationMeanArray.length; i++) {
            List<Double> accelerationsAtIOfAllFrames = new LinkedList<>();
            for (Frame frame : frames) {
                accelerationsAtIOfAllFrames.add(frame.getAllRelevantAccelerations(typeOfGesture)[i]);
            }
            accelerationMeanArray[i] = CalculationsUtil.calculateAverage(accelerationsAtIOfAllFrames);
            accelerationVarianceArray[i] = CalculationsUtil.calculateVariance(accelerationsAtIOfAllFrames, accelerationMeanArray[i]);
        }


        double[] meanOfSinArray = new double[numberOfAnglesPerFrame];
        double[] meanOfCosArray = new double[numberOfAnglesPerFrame];
        double[] circularMeanArray = new double[numberOfAnglesPerFrame];
        for (int i = 0; i < meanOfSinArray.length; i++) {
            meanOfSinArray[i] = calculateMeanOfTrigonometricFunction(combinedAngles, i, Math::sin);
            meanOfCosArray[i] = calculateMeanOfTrigonometricFunction(combinedAngles, i, Math::cos);
//            circularMeanArray[i] = calculateCircularMean(meanOfSinArray[i], meanOfCosArray[i]);
            circularMeanArray[i] = Math.atan2(meanOfSinArray[i], meanOfCosArray[i]);
        }

        //items 6 and 7 "2020_10_14_OnePager_vonMisesVerteilung.docx"
        double[] rSquaredArray = new double[numberOfAnglesPerFrame];
        double[] rArray = new double[numberOfAnglesPerFrame];
        for (int i = 0; i < rArray.length; i++) {
            rSquaredArray[i] = Math.pow(meanOfSinArray[i], 2) + Math.pow(meanOfCosArray[i], 2);
            rArray[i] = Math.sqrt(rSquaredArray[i]);
        }

        //item 8 "2020_10_14_OnePager_vonMisesVerteilung.docx"
        double[] kappaArray = new double[numberOfAnglesPerFrame];
        for (int i = 0; i < kappaArray.length; i++) {
            kappaArray[i] = (rArray[i] * (p - rSquaredArray[i])) / (1 - rSquaredArray[i]);
        }

        List<Double> sumOfLnGaussianNaiveBayesList = new LinkedList<>();
        for (Frame frame : frames) {
            double sumOfLnGaussianNaiveBayes = calculateSumOfLnGaussianNaiveBayes(frame, kappaArray, circularMeanArray,
                    accelerationMeanArray, accelerationVarianceArray, typeOfGesture, useEulerDiffs);
            sumOfLnGaussianNaiveBayesList.add(sumOfLnGaussianNaiveBayes);
        }
        double threshold = calculateThreshold(sumOfLnGaussianNaiveBayesList);
        if (useEulerDiffs)
            return new GestureNaiveBayesEulerDiffs(nameOfResult, typeOfGesture.toInt(), algorithmUsedForCalculation.toInt(), parametersForCalculation,
                    threshold, kappaArray, circularMeanArray, accelerationMeanArray, accelerationVarianceArray);
        else
            return new GestureNaiveBayes(nameOfResult, typeOfGesture.toInt(), algorithmUsedForCalculation.toInt(), parametersForCalculation,
                    threshold, kappaArray, circularMeanArray, accelerationMeanArray, accelerationVarianceArray);
    }

    //item 15, 16 "2021-02-06OnePager_DataGlove_Auswertung_statische_Gesten.docx"
    public static double calculateSumOfLnGaussianNaiveBayes(Frame frame, double[] kappaArray, double[] circularMeanArray, double[] accelerationMeanArray,
                                                            double[] accelerationVarianceArray, TypeOfGesture typeOfGesture, boolean useEulerDiffs) {
        initConstants(typeOfGesture, useEulerDiffs);
        double[] likelihoods = calculateLikelihoods(frame, kappaArray, circularMeanArray, accelerationMeanArray, accelerationVarianceArray, typeOfGesture, useEulerDiffs);
        double[] lnLikelihoods = new double[numberOfTotalValuesPerFrame];
        for (int i = 0; i < likelihoods.length; i++) {
            lnLikelihoods[i] = Math.log(likelihoods[i]);
        }
        //item 16 "2021-02-06OnePager_DataGlove_Auswertung_statische_Gesten.docx"
        double sumOfLnGaussianNaiveBayes = Math.log(.5);
        for (double lnLikelihood : lnLikelihoods) {
            sumOfLnGaussianNaiveBayes += lnLikelihood;
        }
        return sumOfLnGaussianNaiveBayes;
    }

    //item 17-23 "2021-02-06OnePager_DataGlove_Auswertung_statische_Gesten.docx"
    private static double calculateThreshold(List<Double> sumOfLnGaussianNaiveBayesList) {
        Collections.sort(sumOfLnGaussianNaiveBayesList);
        double minOfSumOfLnGaussianNaiveBayes = getMinimumOfSumOfLnGaussianNaiveBayes(sumOfLnGaussianNaiveBayesList);
        double maxOfSumOfLnGaussianNaiveBayes = sumOfLnGaussianNaiveBayesList.get(sumOfLnGaussianNaiveBayesList.size() - 1);
        int numberOfIntervals = 100; //item 18 "2021-02-06OnePager_DataGlove_Auswertung_statische_Gesten.docx"
        double intervalWidth = (maxOfSumOfLnGaussianNaiveBayes - minOfSumOfLnGaussianNaiveBayes) / numberOfIntervals; //item 19
        double[] lowerIntervalBounds = new double[numberOfIntervals];
        double[] upperIntervalBounds = new double[numberOfIntervals];
        //item 20
        lowerIntervalBounds[0] = minOfSumOfLnGaussianNaiveBayes;
        upperIntervalBounds[0] = minOfSumOfLnGaussianNaiveBayes + intervalWidth;
        for (int i = 1; i < numberOfIntervals; i++) {
            lowerIntervalBounds[i] = upperIntervalBounds[i - 1];
            upperIntervalBounds[i] = upperIntervalBounds[i - 1] + intervalWidth;
        }

        //item 21
        double[] frequencyArray = new double[numberOfIntervals];
        for (int i = 0; i < numberOfIntervals; i++) {
            frequencyArray[i] = calculateFrequency(lowerIntervalBounds[i], upperIntervalBounds[i], sumOfLnGaussianNaiveBayesList);
        }
        return filterMeasurementSeries(frequencyArray, upperIntervalBounds, sumOfLnGaussianNaiveBayesList.size());
    }

    //if minSumOfLnGaussianNaiveBayes is -Infinity, it will be ignored ant the second smallest value is set as the minimum instead
    private static double getMinimumOfSumOfLnGaussianNaiveBayes(List<Double> sumOfLnGaussianNaiveBayesList) {
        for (double d : sumOfLnGaussianNaiveBayesList) {
            if (Double.isFinite(d)) return d;
        }
        throw new AssertionError("no finite sumOfLnGaussianNaiveBayes found");
    }

    //item 22, 23
    private static double filterMeasurementSeries(double[] frequencyArray, double[] upperIntervalBounds, double numberOfFrames) {
        double x = (100 - percentageOfDataToInclude) / 100;
        double counter = 0;
        for (int i = 0; i < frequencyArray.length; i++) {
            double relativeFrequency = frequencyArray[i] / numberOfFrames;
            if ((relativeFrequency + counter) >= x) {
                return upperIntervalBounds[i];
            }
            counter += relativeFrequency;
        }
        return upperIntervalBounds[upperIntervalBounds.length-1];
    }

    private static int calculateFrequency(double lowerIntervalBound, double upperIntervalBound, List<Double> sumOfLnGaussianNaiveBayesList) {
        int counter = 0;
        for (Double entry : sumOfLnGaussianNaiveBayesList) {
            if ((entry > lowerIntervalBound) && entry <= upperIntervalBound) counter++;
        }
        return counter;
    }

    private static double[] calculateLikelihoods(Frame frame, double[] kappaArray, double[] circularMeanArray, double[] accelerationMeanArray,
                                                 double[] accelerationVarianceArray, TypeOfGesture typeOfGesture, boolean useEulerDiffs) {
        double[] allRelevantEulerAnglesOrAngleDiffs;
        if (useEulerDiffs)
            allRelevantEulerAnglesOrAngleDiffs = frame.getEulerDiffRepresentation().getAllRelevantAngleDiffs(typeOfGesture);
        else allRelevantEulerAnglesOrAngleDiffs = frame.getEulerRepresentation().getAllRelevantAngles(typeOfGesture);
        double[] likelihoodAnglesArray = new double[numberOfTotalValuesPerFrame];
        for (int i = 0; i < numberOfAnglesPerFrame; i++) {
            if (kappaArray[i] > 709) {
                double[] varianceFromKappaArray = new double[kappaArray.length];
                for (int j = 0; j < varianceFromKappaArray.length; j++) {
                    varianceFromKappaArray[j] = 1 / kappaArray[j];
                }
                likelihoodAnglesArray[i] = calculateLikelihoodWithGaussianDistribution(allRelevantEulerAnglesOrAngleDiffs[i],
                        circularMeanArray[i], varianceFromKappaArray[i]);
            } else {
                likelihoodAnglesArray[i] = calculateLikelihoodWithVonMisesDistribution(allRelevantEulerAnglesOrAngleDiffs[i],
                        circularMeanArray[i], kappaArray[i]);
            }
        }

        for (int i = numberOfAnglesPerFrame; i < numberOfTotalValuesPerFrame; i++) {
            likelihoodAnglesArray[i] = calculateLikelihoodWithGaussianDistribution(frame.getAllRelevantAccelerations(typeOfGesture)[i - numberOfAnglesPerFrame],
                    accelerationMeanArray[i - numberOfAnglesPerFrame], accelerationVarianceArray[i - numberOfAnglesPerFrame]);
        }

        return likelihoodAnglesArray;
    }

    /*
    corresponds to items 3 and 4 on "2020_10_14_OnePager_vonMisesVerteilung.docx"
     */
    private static double calculateMeanOfTrigonometricFunction(List<double[]> combinedAngles, int arrayIndex, DoubleFunction<Double> function) {
        double sum = 0;
        for (double[] combinedAngle : combinedAngles) {
            sum += function.apply(combinedAngle[arrayIndex]);
        }

        return sum / combinedAngles.size();
    }

    private static double calculateLikelihoodWithGaussianDistribution(double x, double mean, double variance) {
        double leftPart = 1 / Math.sqrt(2 * Math.PI * variance);
        double rightUpperPart = ((Math.pow((x - mean), 2)) / (2 * variance));
        double rightPart = Math.exp(-(rightUpperPart));
        return leftPart * rightPart;
    }

    private static double calculateLikelihoodWithVonMisesDistribution(double x, double mean, double kappa) {
        double upperPart = Math.exp(kappa * (Math.cos(x - mean)));
        double lowerPart = 2 * Math.PI * BesselI.value(0, kappa);
        return upperPart / lowerPart;
    }

}
