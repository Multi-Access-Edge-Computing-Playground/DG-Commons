package de.btu.dataglove.shared;

import de.btu.dataglove.shared.calculations.GestureCalculationLinear;

import java.io.Serializable;
import java.util.List;

/*
represents a static gesture
 */
public class Gesture extends AbstractGesture implements Comparable<Gesture> {

    public final double[] lowerBoundRotScalar;
    public final double[] lowerBoundRotVectorX;
    public final double[] lowerBoundRotVectorY;
    public final double[] lowerBoundRotVectorZ;
    public final double[] lowerBoundAccX;
    public final double[] lowerBoundAccY;
    public final double[] lowerBoundAccZ;

    public final double[] upperBoundRotScalar;
    public final double[] upperBoundRotVectorX;
    public final double[] upperBoundRotVectorY;
    public final double[] upperBoundRotVectorZ;
    public final double[] upperBoundAccX;
    public final double[] upperBoundAccY;
    public final double[] upperBoundAccZ;


    public Gesture(String name, int typeOfGesture, int algorithmUsedForCalculation, double[] algorithmParameters,
                   double[] lowerBoundRotScalar, double[] lowerBoundRotVectorX, double[] lowerBoundRotVectorY, double[] lowerBoundRotVectorZ,
                   double[] lowerBoundAccX, double[] lowerBoundAccY, double[] lowerBoundAccZ,
                   double[] upperBoundRotScalar, double[] upperBoundRotVectorX, double[] upperBoundRotVectorY, double[] upperBoundRotVectorZ,
                   double[] upperBoundAccX, double[] upperBoundAccY, double[] upperBoundAccZ) {
        super(name, typeOfGesture, algorithmUsedForCalculation, algorithmParameters);

        this.lowerBoundRotScalar = lowerBoundRotScalar;
        this.lowerBoundRotVectorX = lowerBoundRotVectorX;
        this.lowerBoundRotVectorY = lowerBoundRotVectorY;
        this.lowerBoundRotVectorZ = lowerBoundRotVectorZ;
        this.lowerBoundAccX = lowerBoundAccX;
        this.lowerBoundAccY = lowerBoundAccY;
        this.lowerBoundAccZ = lowerBoundAccZ;
        this.upperBoundRotScalar = upperBoundRotScalar;
        this.upperBoundRotVectorX = upperBoundRotVectorX;
        this.upperBoundRotVectorY = upperBoundRotVectorY;
        this.upperBoundRotVectorZ = upperBoundRotVectorZ;
        this.upperBoundAccX = upperBoundAccX;
        this.upperBoundAccY = upperBoundAccY;
        this.upperBoundAccZ = upperBoundAccZ;
    }

    public Gesture(String name, int typeOfGesture, int algorithmUsedForCalculation, double[] algorithmParameters, double[] allLowerBoundaries, double[] allUpperBoundaries) {
        super(name, typeOfGesture, algorithmUsedForCalculation, algorithmParameters);

        lowerBoundRotScalar = extractFieldFromAllBoundariesArray(allLowerBoundaries, 4);
        lowerBoundRotVectorX = extractFieldFromAllBoundariesArray(allLowerBoundaries, 5);
        lowerBoundRotVectorY = extractFieldFromAllBoundariesArray(allLowerBoundaries, 6);
        lowerBoundRotVectorZ = extractFieldFromAllBoundariesArray(allLowerBoundaries, 7);
        lowerBoundAccX = extractFieldFromAllBoundariesArray(allLowerBoundaries, 8);
        lowerBoundAccY = extractFieldFromAllBoundariesArray(allLowerBoundaries, 9);
        lowerBoundAccZ = extractFieldFromAllBoundariesArray(allLowerBoundaries, 10);

        upperBoundRotScalar = extractFieldFromAllBoundariesArray(allUpperBoundaries, 4);
        upperBoundRotVectorX = extractFieldFromAllBoundariesArray(allUpperBoundaries, 5);
        upperBoundRotVectorY = extractFieldFromAllBoundariesArray(allUpperBoundaries, 6);
        upperBoundRotVectorZ = extractFieldFromAllBoundariesArray(allUpperBoundaries, 7);
        upperBoundAccX = extractFieldFromAllBoundariesArray(allUpperBoundaries, 8);
        upperBoundAccY = extractFieldFromAllBoundariesArray(allUpperBoundaries, 9);
        upperBoundAccZ = extractFieldFromAllBoundariesArray(allUpperBoundaries, 10);
    }

    /*
    returns the boundary values for a given field based on the field id
    field ids are based on the getAllFrameData method in the Frame class
    field ids:
    0: boundRawRotScalar
    1: boundRawRotVectorX
    2: boundRawRotVectorY
    ...
    10: boundAccZ
     */
    private double[] extractFieldFromAllBoundariesArray(double[] allBoundaries, int fieldID) {
        double[] result = new double[SharedConstants.NUMBER_OF_SENSORS];

        int startPos = fieldID * SharedConstants.NUMBER_OF_SENSORS;
        for (int i = 0; i < result.length; i++) {
            result[i] = allBoundaries[startPos + i];
        }
        return result;
    }

    @Override
    public double getCorrectnessOfFrame(Frame frame) {
        TypeOfGesture typeOfGesture = TypeOfGesture.get(this.typeOfGesture);
        int numberOfValuesToBeChecked;
        if ((typeOfGesture == TypeOfGesture.STATIC_GESTURE_LEFT) || (typeOfGesture == TypeOfGesture.STATIC_GESTURE_RIGHT)) {
            numberOfValuesToBeChecked = SharedConstants.TOTAL_SENSOR_DATA_OF_FRAME_WITHOUT_RAW / 2;
        } else if (typeOfGesture == TypeOfGesture.STATIC_GESTURE_BOTH)
            numberOfValuesToBeChecked = SharedConstants.TOTAL_SENSOR_DATA_OF_FRAME_WITHOUT_RAW;
        else throw new AssertionError();

        double trueCounter = areArrayValuesWithinBoundaries(lowerBoundAccX, upperBoundAccX, frame.accX, typeOfGesture)
                + areArrayValuesWithinBoundaries(lowerBoundAccY, upperBoundAccY, frame.accY, typeOfGesture)
                + areArrayValuesWithinBoundaries(lowerBoundAccZ, upperBoundAccZ, frame.accZ, typeOfGesture)
                + areArrayValuesWithinBoundaries(lowerBoundRotScalar, upperBoundRotScalar, frame.rotScalar, typeOfGesture)
                + areArrayValuesWithinBoundaries(lowerBoundRotVectorX, upperBoundRotVectorX, frame.rotVectorX, typeOfGesture)
                + areArrayValuesWithinBoundaries(lowerBoundRotVectorY, upperBoundRotVectorY, frame.rotVectorY, typeOfGesture)
                + areArrayValuesWithinBoundaries(lowerBoundRotVectorZ, upperBoundRotVectorZ, frame.rotVectorZ, typeOfGesture);
        return trueCounter / numberOfValuesToBeChecked;
    }

    /*
    returns the number of values that are within boundaries
     */
    protected static int areArrayValuesWithinBoundaries(double[] lowerBoundValues, double[] upperBoundValues, double[] frameValues, TypeOfGesture typeOfGesture) {
        int trueCounter = 0;
        for (int i = 0; i < frameValues.length; i++) {
            if (typeOfGesture.isSensorNumberRelevant(SharedUtility.getSensorNumberFromArrayIndex(i))) {
                if (frameValues[i] > lowerBoundValues[i] && frameValues[i] < upperBoundValues[i]) {
                    trueCounter++;
                }
            }
        }
        return trueCounter;
    }

    @Override
    public int compareTo(Gesture o) {
        return name.compareTo(o.name);
    }
}
