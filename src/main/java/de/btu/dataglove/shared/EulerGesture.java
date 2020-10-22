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
}
