package de.btu.dataglove.shared;

/*
represents the different kinds of recordings
 */
public enum TypeOfRecording {
    GESTURE_LEFT(1),
    GESTURE_RIGHT(2),
    GESTURE_BOTH(3),
    MOVEMENT_LEFT(4),
    MOVEMENT_RIGHT(5),
    MOVEMENT_BOTH(6);

    private final int value;

    TypeOfRecording(int value) {
        this.value = value;
    }

    public int toInt() {
        return value;
    }
}
