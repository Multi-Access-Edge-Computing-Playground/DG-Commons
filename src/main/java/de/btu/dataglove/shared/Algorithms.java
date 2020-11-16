package de.btu.dataglove.shared;

/*
represents algorithms used to calculate gestures
 */
public enum Algorithms {
    STATIC_GESTURE_LINEAR(0),
    STATIC_GESTURE_CIRCULAR(1);

    private final int value;

    Algorithms(int value) {
        this.value = value;
    }

    public int toInt() {
        return value;
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
