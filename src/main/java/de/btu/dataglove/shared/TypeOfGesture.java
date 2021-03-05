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

    public boolean isSensorNumberRelevant(int sensorNumber) {
        switch(this) {
            case STATIC_GESTURE_LEFT: case DYNAMIC_GESTURE_LEFT: return isSensorNumberPartOfLeftGlove(sensorNumber);
            case STATIC_GESTURE_RIGHT: case DYNAMIC_GESTURE_RIGHT: return isSensorNumberPartOfRightGlove(sensorNumber);
            case STATIC_GESTURE_BOTH: case DYNAMIC_GESTURE_BOTH: return (isSensorNumberPartOfLeftGlove(sensorNumber) || isSensorNumberPartOfRightGlove(sensorNumber));
            default: throw new AssertionError();
        }
    }

    private boolean isSensorNumberPartOfLeftGlove(int sensorNumber) {
        return (sensorNumber >= SharedConstants.GLOVE_ONE_LOWEST_SENSOR_ID && sensorNumber <= SharedConstants.GLOVE_ONE_HIGHEST_SENSOR_ID);
    }

    private boolean isSensorNumberPartOfRightGlove(int sensorNumber) {
        return (sensorNumber >= SharedConstants.GLOVE_TWO_LOWEST_SENSOR_ID && sensorNumber <= SharedConstants.GLOVE_TWO_HIGHEST_SENSOR_ID);
    }
}
