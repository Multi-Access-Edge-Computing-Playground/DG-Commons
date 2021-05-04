package de.btu.dataglove.shared;

/*
represents a static gesture with euler angles instead of quaternions
 */
public class EulerGesture extends AbstractGesture implements Comparable<EulerGesture> {

    public final double[] lowerBoundPhi;
    public final double[] lowerBoundTheta;
    public final double[] lowerBoundPsi;
    public final double[] lowerBoundAccX;
    public final double[] lowerBoundAccY;
    public final double[] lowerBoundAccZ;

    public final double[] upperBoundPhi;
    public final double[] upperBoundTheta;
    public final double[] upperBoundPsi;
    public final double[] upperBoundAccX;
    public final double[] upperBoundAccY;
    public final double[] upperBoundAccZ;

    public EulerGesture(String name, int typeOfGesture, int algorithmUsedForCalculation, double[] algorithmParameters,
                        double[] lowerBoundPhi, double[] lowerBoundTheta, double[] lowerBoundPsi,
                        double[] lowerBoundAccX, double[] lowerBoundAccY, double[] lowerBoundAccZ,
                        double[] upperBoundPhi, double[] upperBoundTheta, double[] upperBoundPsi, double[]
                                upperBoundAccX, double[] upperBoundAccY, double[] upperBoundAccZ) {
        super(name, typeOfGesture, algorithmUsedForCalculation, algorithmParameters);
        this.lowerBoundPhi = lowerBoundPhi;
        this.lowerBoundTheta = lowerBoundTheta;
        this.lowerBoundPsi = lowerBoundPsi;
        this.lowerBoundAccX = lowerBoundAccX;
        this.lowerBoundAccY = lowerBoundAccY;
        this.lowerBoundAccZ = lowerBoundAccZ;
        this.upperBoundPhi = upperBoundPhi;
        this.upperBoundTheta = upperBoundTheta;
        this.upperBoundPsi = upperBoundPsi;
        this.upperBoundAccX = upperBoundAccX;
        this.upperBoundAccY = upperBoundAccY;
        this.upperBoundAccZ = upperBoundAccZ;
    }

    public EulerGesture(String name, int typeOfGesture, int algorithmUsedForCalculation,
                        double[] algorithmParameters, double[] allLowerAngleBoundaries, double[] allUpperAngleBoundaries,
                        double[] lowerBoundAccX, double[] lowerBoundAccY, double[] lowerBoundAccZ,
                        double[] upperBoundAccX, double[] upperBoundAccY, double[] upperBoundAccZ) {
        super(name, typeOfGesture, algorithmUsedForCalculation, algorithmParameters);
        lowerBoundPhi = extractFieldFromAllBoundariesArray(allLowerAngleBoundaries, 0);
        lowerBoundTheta = extractFieldFromAllBoundariesArray(allLowerAngleBoundaries, 1);
        lowerBoundPsi = extractFieldFromAllBoundariesArray(allLowerAngleBoundaries, 2);
        upperBoundPhi = extractFieldFromAllBoundariesArray(allUpperAngleBoundaries, 0);
        upperBoundTheta = extractFieldFromAllBoundariesArray(allUpperAngleBoundaries, 1);
        upperBoundPsi = extractFieldFromAllBoundariesArray(allUpperAngleBoundaries, 2);

        this.lowerBoundAccX = lowerBoundAccX;
        this.lowerBoundAccY = lowerBoundAccY;
        this.lowerBoundAccZ = lowerBoundAccZ;
        this.upperBoundAccX = upperBoundAccX;
        this.upperBoundAccY = upperBoundAccY;
        this.upperBoundAccZ = upperBoundAccZ;
    }

    @Override
    public double getCorrectnessOfFrame(Frame frame) {
        TypeOfGesture typeOfGesture = TypeOfGesture.get(this.typeOfGesture);
        int numberOfValuesToBeChecked;
        if ((typeOfGesture == TypeOfGesture.STATIC_GESTURE_LEFT) || (typeOfGesture == TypeOfGesture.STATIC_GESTURE_RIGHT)) {
            numberOfValuesToBeChecked = (SharedConstants.NUMBER_OF_ACCELERATIONS_PER_FRAME + SharedConstants.NUMBER_OF_EULER_ANGLES_PER_FRAME) / 2;
        } else if (typeOfGesture == TypeOfGesture.STATIC_GESTURE_BOTH)
            numberOfValuesToBeChecked = (SharedConstants.NUMBER_OF_ACCELERATIONS_PER_FRAME + SharedConstants.NUMBER_OF_EULER_ANGLES_PER_FRAME);
        else throw new AssertionError();

        double trueCounter = Gesture.areArrayValuesWithinBoundaries(lowerBoundAccX, upperBoundAccX, frame.accX, typeOfGesture)
                + Gesture.areArrayValuesWithinBoundaries(lowerBoundAccY, upperBoundAccY, frame.accY, typeOfGesture)
                + Gesture.areArrayValuesWithinBoundaries(lowerBoundAccZ, upperBoundAccZ, frame.accZ, typeOfGesture)
                + Gesture.areArrayValuesWithinBoundaries(lowerBoundPhi, upperBoundPhi, frame.getEulerRepresentation().getPhiAsRadian(), typeOfGesture)
                + Gesture.areArrayValuesWithinBoundaries(lowerBoundTheta, upperBoundTheta, frame.getEulerRepresentation().getThetaAsRadian(), typeOfGesture)
                + Gesture.areArrayValuesWithinBoundaries(lowerBoundPsi, upperBoundPsi, frame.getEulerRepresentation().getPsiAsRadian(), typeOfGesture);
        return trueCounter / numberOfValuesToBeChecked;
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
