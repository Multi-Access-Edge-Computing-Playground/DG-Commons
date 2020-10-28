package de.btu.dataglove.shared;

/*
represents a static gesture with euler angles instead of quaternions
 */
public class EulerGesture {

    public String name;
    public int typeOfGesture;
    public int algorithmUsedForCalculation;
    public double[] algorithmParameters;

    public double[] lowerBoundPhi;
    public double[] lowerBoundTheta;
    public double[] lowerBoundPsi;

    public double[] upperBoundPhi;
    public double[] upperBoundTheta;
    public double[] upperBoundPsi;

    public EulerGesture(String name, int typeOfGesture, int algorithmUsedForCalculation, double[] algorithmParameters,
                        double[] lowerBoundPhi, double[] lowerBoundTheta, double[] lowerBoundPsi,
                        double[] upperBoundPhi, double[] upperBoundTheta, double[] upperBoundPsi) {
        this.name = name;
        this.typeOfGesture = typeOfGesture;
        this.algorithmUsedForCalculation = algorithmUsedForCalculation;
        this.algorithmParameters = algorithmParameters;
        this.lowerBoundPhi = lowerBoundPhi;
        this.lowerBoundTheta = lowerBoundTheta;
        this.lowerBoundPsi = lowerBoundPsi;
        this.upperBoundPhi = upperBoundPhi;
        this.upperBoundTheta = upperBoundTheta;
        this.upperBoundPsi = upperBoundPsi;
    }

    public EulerGesture(String name, int typeOfGesture, int algorithmUsedForCalculation,
                        double[] algorithmParameters, double[] allLowerBoundaries, double[] allUpperBoundaries) {
        this.name = name;
        this.typeOfGesture = typeOfGesture;
        this.algorithmUsedForCalculation = algorithmUsedForCalculation;
        this.algorithmParameters = algorithmParameters;
        lowerBoundPhi = extractFieldFromAllBoundariesArray(allLowerBoundaries, 0);
        lowerBoundTheta = extractFieldFromAllBoundariesArray(allLowerBoundaries, 1);
        lowerBoundPsi = extractFieldFromAllBoundariesArray(allLowerBoundaries, 2);
        upperBoundPhi = extractFieldFromAllBoundariesArray(allUpperBoundaries, 0);
        upperBoundTheta = extractFieldFromAllBoundariesArray(allUpperBoundaries, 1);
        upperBoundPsi = extractFieldFromAllBoundariesArray(allUpperBoundaries, 2);
    }

    /*
    returns the boundary values for a given field based on the field id
    field ids:
    0: boundPhi
    1: boundTheta
    2: boundPsi
    */
    private double[] extractFieldFromAllBoundariesArray(double[] allBoundaries, int fieldID) {
        double[] result = new double[SharedConstants.NUMBER_OF_SENSORS];

        int startPos = fieldID * SharedConstants.NUMBER_OF_SENSORS;
        for (int i = 0; i < result.length; i++) {
            result[i] = allBoundaries[startPos + i];
        }
        return result;
    }
}
