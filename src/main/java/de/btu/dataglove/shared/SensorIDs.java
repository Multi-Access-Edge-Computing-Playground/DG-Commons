package de.btu.dataglove.shared;

/**
 * an enumeration of all sensor numbers and their positions in arrays
 */
public enum SensorIDs {

    LEFT_BACK_OF_HAND(24, 0),
    LEFT_THUMB_ONE(25, 1),
    LEFT_THUMB_TWO(26, 2),
    LEFT_INDEX_FINGER(27, 3),
    LEFT_MIDDLE_FINGER(28, 4),
    LEFT_RING_FINGER(29, 5),
    LEFT_LITTLE_FINGER(30, 6),
    RIGHT_BACK_OF_HAND(40, 7),
    RIGHT_THUMB_ONE(41, 8),
    RIGHT_THUMB_TWO(42, 9),
    RIGHT_INDEX_FINGER(43, 10),
    RIGHT_MIDDLE_FINGER(44, 11),
    RIGHT_RING_FINGER(45, 12),
    RIGHT_LITTLE_FINGER(46, 13);

    private final int sensorNumber;
    private final int arrayPosition;

    SensorIDs(int sensorNumber, int arrayPosition) {
        this.sensorNumber = sensorNumber;
        this.arrayPosition = arrayPosition;
    }

    public int getSensorNumber() {
        return sensorNumber;
    }

    public int getArrayPosition() {
        return arrayPosition;
    }

}
