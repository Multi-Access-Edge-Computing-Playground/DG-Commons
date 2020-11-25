package de.btu.dataglove.shared;

public class AbstractGesture {
    public final String name;
    public final int typeOfGesture;
    public final int algorithmUsedForCalculation;
    public final double[] algorithmParameters;

    public AbstractGesture(String name, int typeOfGesture, int algorithmUsedForCalculation, double[] algorithmParameters) {
        this.name = name;
        this.typeOfGesture = typeOfGesture;
        this.algorithmUsedForCalculation = algorithmUsedForCalculation;
        this.algorithmParameters = algorithmParameters;
    }
}
