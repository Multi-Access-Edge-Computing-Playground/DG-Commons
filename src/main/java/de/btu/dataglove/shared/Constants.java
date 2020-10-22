package de.btu.dataglove.shared;

public class Constants {

    //these numbers are fixed as long as the same pair of gloves are being used
    public static final int GLOVE_ONE_LOWEST_SENSOR_ID = 24;
    public static final int GLOVE_ONE_HIGHEST_SENSOR_ID = 30;
    public static final int GLOVE_TWO_LOWEST_SENSOR_ID = 40;
    public static final int GLOVE_TWO_HIGHEST_SENSOR_ID = 46;

    //total number of sensors on the pair of gloves
    public static final int NUMBER_OF_SENSORS = 14;
    //each sensor has 11 fields that are used for calculation (rawRotScalar, rawRotVectorX, rawRotVectorY and so on)
    public static final int TOTAL_FIELDS_PER_SENSOR = 11;
    public static final int TOTAL_SENSOR_DATA_OF_FRAME = NUMBER_OF_SENSORS * TOTAL_FIELDS_PER_SENSOR;

    //algorithms
    public static final int USED_ALGORITHM_GESTURE_BOUNDARIES_LINEAR = 0;
    public static final int USED_ALGORITHM_GESTURE_BOUNDARIES_CIRCULAR = 1;

    //exceptions
    public static final String INVALID_SENSOR_NUMBER_EXCEPTION = "INVALID_SENSOR_NUMBER_EXCEPTION";
    public static final String INVALID_ARRAY_INDEX_EXCEPTION = "INVALID_ARRAY_INDEX_EXCEPTION";
}
