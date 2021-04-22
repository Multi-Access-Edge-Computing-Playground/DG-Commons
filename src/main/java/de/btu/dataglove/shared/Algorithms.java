package de.btu.dataglove.shared;

import java.util.HashMap;
import java.util.Map;

/*
represents algorithms used to calculate gestures
 */
public enum Algorithms {
    STATIC_GESTURE_LINEAR(0, 1),
    STATIC_GESTURE_CIRCULAR(1, 1),
    STATIC_GESTURE_NAIVE_BAYES(2, 1);

    private final int value; //the value representing the algorithm in the database
    private final int numberOfParameters;

    //reverse lookup for getting the corresponding algorithm from an int
    private static final Map<Integer, Algorithms> lookup = new HashMap<>();

    static {
        for (Algorithms a : Algorithms.values()) {
            lookup.put(a.toInt(), a);
        }
    }

    Algorithms(int value, int numberOfParameters) {
        this.value = value;
        this.numberOfParameters = numberOfParameters;
    }

    public int toInt() {
        return value;
    }

    public static Algorithms get(int valueOfAlgorithm) {
        return lookup.get(valueOfAlgorithm);
    }

    public int getNumberOfParameters() {
        return numberOfParameters;
    }
}
