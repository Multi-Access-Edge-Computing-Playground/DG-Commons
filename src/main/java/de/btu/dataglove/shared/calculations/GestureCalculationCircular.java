package de.btu.dataglove.shared.calculations;

import de.btu.dataglove.shared.*;

import java.util.LinkedList;
import java.util.List;
import java.util.function.DoubleFunction;

/*
used for calculation of static gestures based on the description in "2020_10_14_OnePager_vonMisesVerteilung.docx"
 */
public class GestureCalculationCircular {

    //see one pager item 9
    private static final double p = 2;

    public static EulerGesture calculateGestureBoundaries(String nameOfResult, List<Frame> frames, double[] parametersForCalculation) {

        int typeOfGesture = frames.get(0).typeOfRecording;
        int algorithmUsedForCalculation = Algorithms.STATIC_GESTURE_CIRCULAR.toInt();

        double parameterFactorToR = parametersForCalculation[0];

        List<EulerFrame> eulerFrames = new LinkedList<>();
        for (Frame frame : frames) {
            eulerFrames.add(new EulerFrame(frame));
        }

        List<double[]> combinedAngles = new LinkedList<>();
        for (EulerFrame eulerFrame : eulerFrames) {
            combinedAngles.add(eulerFrame.getAllAngles());
        }
        int numberOfAnglesPerFrame = combinedAngles.get(0).length;

        double[] meanOfSinArray = new double[numberOfAnglesPerFrame];
        double[] meanOfCosArray = new double[numberOfAnglesPerFrame];
        double[] circularMeanArray = new double[numberOfAnglesPerFrame];
        for (int i = 0; i < meanOfSinArray.length; i++) {
            meanOfSinArray[i] = calculateMeanOfTrigonometricFunction(combinedAngles, i, Math::sin);
            meanOfCosArray[i] = calculateMeanOfTrigonometricFunction(combinedAngles, i, Math::cos);
            circularMeanArray[i] = calculateCircularMean(meanOfSinArray[i], meanOfCosArray[i]);
        }

        //items 6 and 7 on the one pager
        double[] rSquaredArray = new double[numberOfAnglesPerFrame];
        double[] rArray = new double[numberOfAnglesPerFrame];
        for (int i = 0; i < rArray.length; i++) {
            rSquaredArray[i] = Math.pow(meanOfSinArray[i], 2) + Math.pow(meanOfCosArray[i], 2);
            rArray[i] = Math.sqrt(rSquaredArray[i]);
        }

        //item 8 on the one pager
        double[] kappaArray = new double[numberOfAnglesPerFrame];
        for (int i = 0; i < kappaArray.length; i++) {
            kappaArray[i] = (rArray[i] * (p - rSquaredArray[i])) / (1 - rSquaredArray[i]);
        }

        //item 11 on the one pager
        double[] upperAngleBoundaries = new double[numberOfAnglesPerFrame];
        double[] lowerAngleBoundaries = new double[numberOfAnglesPerFrame];
        for (int i = 0; i < upperAngleBoundaries.length; i++) {
            upperAngleBoundaries[i] = circularMeanArray[i] + parameterFactorToR;
            lowerAngleBoundaries[i] = circularMeanArray[i] - parameterFactorToR;
        }

        //we use this to calculate the acceleration boundaries /TODO have separate implementation instead if needed
        Gesture linearGesture = GestureCalculationLinear.calculateGestureBoundaries(nameOfResult, frames, parametersForCalculation);
        double[] lowerBoundAccX = linearGesture.lowerBoundAccX;
        double[] lowerBoundAccY = linearGesture.lowerBoundAccY;
        double[] lowerBoundAccZ = linearGesture.lowerBoundAccZ;

        double[] upperBoundAccX = linearGesture.upperBoundAccX;
        double[] upperBoundAccY = linearGesture.upperBoundAccY;
        double[] upperBoundAccZ = linearGesture.upperBoundAccZ;


        return new EulerGesture(nameOfResult, typeOfGesture, algorithmUsedForCalculation, parametersForCalculation,
                lowerAngleBoundaries, upperAngleBoundaries,
                lowerBoundAccX, lowerBoundAccY, lowerBoundAccZ, upperBoundAccX, upperBoundAccY, upperBoundAccZ);
    }

    /*
    corresponds to items 3 and 4 on the one pager
     */
    private static double calculateMeanOfTrigonometricFunction(List<double[]> combinedAngles, int arrayIndex, DoubleFunction<Double> function) {
        double sum = 0;
        for (double[] combinedAngle : combinedAngles) {
            sum += function.apply(combinedAngle[arrayIndex] * Math.PI / 180);
        }

        return sum / combinedAngles.size();
    }

    /*
    see item 5 on the one pager
     */
    private static double calculateCircularMean(double meanOfSin, double meanOfCos) {
        double intermediateResult = Math.atan(meanOfSin/ meanOfCos);
        if (meanOfSin > 0 && meanOfCos > 0) {
            return intermediateResult * 180/Math.PI;
        }
        if (meanOfCos < 0) {
            return intermediateResult * 180/Math.PI + 180;
        }
        else {
            return intermediateResult * 180/Math.PI + 360;
        }
    }
}
