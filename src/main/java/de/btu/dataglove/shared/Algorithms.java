package de.btu.dataglove.shared;

import java.util.HashMap;
import java.util.Map;

/*
represents algorithms used to calculate gestures
 */
public enum Algorithms {
    STATIC_GESTURE_LINEAR(0),
    STATIC_GESTURE_CIRCULAR(1);

    private final int value;

    //reverse lookup for getting the corresponding algorithm from an int
    private static final Map<Integer, Algorithms> lookup = new HashMap<>();

    static {
        for (Algorithms a : Algorithms.values()) {
            lookup.put(a.toInt(), a);
        }
    }

    Algorithms(int value) {
        this.value = value;
    }

    public int toInt() {
        return value;
    }

    public static Algorithms get(int valueOfAlgorithm) {
        return lookup.get(valueOfAlgorithm);
    }

    public int getNumberOfParameters() {
        switch(value) {
            case 0:
            case 1:
                return 1;
            default: return 0;
        }
    }
}
