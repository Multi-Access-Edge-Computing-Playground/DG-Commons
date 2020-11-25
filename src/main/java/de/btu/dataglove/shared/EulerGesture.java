package de.btu.dataglove.shared;

/*
represents a static gesture with euler angles instead of quaternions
 */
public class EulerGesture extends AbstractGesture implements Comparable<EulerGesture> {

    public final double[] lowerBoundPhi;
    public final double[] lowerBoundTheta;
    public final double[] lowerBoundPsi;

    public final double[] upperBoundPhi;
    public final double[] upperBoundTheta;
    public final double[] upperBoundPsi;

    public EulerGesture(String name, int typeOfGesture, int algorithmUsedForCalculation, double[] algorithmParameters,
                        double[] lowerBoundPhi, double[] lowerBoundTheta, double[] lowerBoundPsi,
                        double[] upperBoundPhi, double[] upperBoundTheta, double[] upperBoundPsi) {
        super(name, typeOfGesture, algorithmUsedForCalculation, algorithmParameters);
        this.lowerBoundPhi = lowerBoundPhi;
        this.lowerBoundTheta = lowerBoundTheta;
        this.lowerBoundPsi = lowerBoundPsi;
        this.upperBoundPhi = upperBoundPhi;
        this.upperBoundTheta = upperBoundTheta;
        this.upperBoundPsi = upperBoundPsi;
    }

    public EulerGesture(String name, int typeOfGesture, int algorithmUsedForCalculation,
                        double[] algorithmParameters, double[] allLowerBoundaries, double[] allUpperBoundaries) {
        super(name, typeOfGesture, algorithmUsedForCalculation, algorithmParameters);
        lowerBoundPhi = extractFieldFromAllBoundariesArray(allLowerBoundaries, 0);
        lowerBoundTheta = extractFieldFromAllBoundariesArray(allLowerBoundaries, 1);
        lowerBoundPsi = extractFieldFromAllBoundariesArray(allLowerBoundaries, 2);
        upperBoundPhi = extractFieldFromAllBoundariesArray(allUpperBoundaries, 0);
        upperBoundTheta = extractFieldFromAllBoundariesArray(allUpperBoundaries, 1);
        upperBoundPsi = extractFieldFromAllBoundariesArray(allUpperBoundaries, 2);
    }

    /*
    returns the boundary values for a given field based on the field id
    field ids are based on the getAllAngles method in the EulerFrame class
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

    @Override
    public int compareTo(EulerGesture o) {
        return name.compareTo(o.name);
    }
}
