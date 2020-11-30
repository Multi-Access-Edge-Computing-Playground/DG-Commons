package de.btu.dataglove.shared;

import java.io.Serializable;

public abstract class AbstractGesture implements Serializable {
    private static final long serialVersionUID = -3244143415404822243L;
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
