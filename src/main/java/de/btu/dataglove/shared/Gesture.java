package de.btu.dataglove.shared;

import java.io.Serializable;

/*
represents a static gesture
 */
public class Gesture implements Serializable {
    private static final long serialVersionUID = -3244143415404822243L;
    public String name;
    public int typeOfGesture;
    public int algorithmUsedForCalculation;
    public double[] algorithmParameters;

    public double[] lowerBoundRotScalar;
    public double[] lowerBoundRotVectorX;
    public double[] lowerBoundRotVectorY;
    public double[] lowerBoundRotVectorZ;
    public double[] lowerBoundAccX;
    public double[] lowerBoundAccY;
    public double[] lowerBoundAccZ;

    public double[] upperBoundRotScalar;
    public double[] upperBoundRotVectorX;
    public double[] upperBoundRotVectorY;
    public double[] upperBoundRotVectorZ;
    public double[] upperBoundAccX;
    public double[] upperBoundAccY;
    public double[] upperBoundAccZ;


    public Gesture(String name, int typeOfGesture, int algorithmUsedForCalculation, double[] algorithmParameters,
                   double[] lowerBoundRotScalar, double[] lowerBoundRotVectorX, double[] lowerBoundRotVectorY, double[] lowerBoundRotVectorZ,
                   double[] lowerBoundAccX, double[] lowerBoundAccY, double[] lowerBoundAccZ,
                   double[] upperBoundRotScalar, double[] upperBoundRotVectorX, double[] upperBoundRotVectorY, double[] upperBoundRotVectorZ,
                   double[] upperBoundAccX, double[] upperBoundAccY, double[] upperBoundAccZ) {
        this.name = name;
        this.typeOfGesture = typeOfGesture;
        this.algorithmUsedForCalculation = algorithmUsedForCalculation;
        this.algorithmParameters = algorithmParameters;

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
        this.name = name;
        this.typeOfGesture = typeOfGesture;
        this.algorithmUsedForCalculation = algorithmUsedForCalculation;
        this.algorithmParameters = algorithmParameters;

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
    field ids:
    0: boundRawRotScalar
    1: boundRawRotVectorX
    2: boundRawRotVectorY
    ...
    10: boundAccZ
     */
    private double[] extractFieldFromAllBoundariesArray(double[] allBoundaries, int fieldID) {
        double[] result = new double[Constants.NUMBER_OF_SENSORS];

        int startPos = fieldID * Constants.NUMBER_OF_SENSORS;
        for (int i = 0; i < result.length; i++) {
            result[i] = allBoundaries[startPos + i];
        }
        return result;
    }

}
