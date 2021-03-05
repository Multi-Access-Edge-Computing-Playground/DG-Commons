package de.btu.dataglove.shared;

/*
represents the different kinds of recordings
 */
public enum TypeOfGesture {
    STATIC_GESTURE_LEFT(1),
    STATIC_GESTURE_RIGHT(2),
    STATIC_GESTURE_BOTH(3),
    DYNAMIC_GESTURE_LEFT(4),
    DYNAMIC_GESTURE_RIGHT(5),
    DYNAMIC_GESTURE_BOTH(6);

    private final int value;

    TypeOfGesture(int value) {
        this.value = value;
    }

    public int toInt() {
        return value;
    }
}
