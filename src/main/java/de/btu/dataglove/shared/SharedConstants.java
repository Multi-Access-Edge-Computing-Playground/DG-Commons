package de.btu.dataglove.shared;

public class SharedConstants {

    //these numbers are fixed as long as the same pair of gloves are being used
    public static final int GLOVE_ONE_LOWEST_SENSOR_ID = 24;
    public static final int GLOVE_ONE_HIGHEST_SENSOR_ID = 30;
    public static final int GLOVE_TWO_LOWEST_SENSOR_ID = 40;
    public static final int GLOVE_TWO_HIGHEST_SENSOR_ID = 46;

    //total number of sensors on the pair of gloves
    public static final int NUMBER_OF_SENSORS = 14;
    public static final int NUMBER_OF_EULER_ANGLES_PER_FRAME = 42;
    public static final int NUMBER_OF_ACCELERATIONS_PER_FRAME = 42;
    public static final int NUMBER_OF_EULER_ANGLE_DIFFERENCES_PER_HAND = 6;
    //each sensor has 11 fields that are used for calculation (rawRotScalar, rawRotVectorX, rawRotVectorY and so on)
    public static final int TOTAL_FIELDS_PER_SENSOR = 11;
    public static final int TOTAL_SENSOR_DATA_OF_FRAME = NUMBER_OF_SENSORS * TOTAL_FIELDS_PER_SENSOR;
    public static final int TOTAL_FIELDS_PER_SENSOR_WITHOUT_RAW = 7;
    public static final int TOTAL_SENSOR_DATA_OF_FRAME_WITHOUT_RAW = NUMBER_OF_SENSORS * TOTAL_FIELDS_PER_SENSOR_WITHOUT_RAW;

    //web service paths
    public static final String WEB_SERVICE_TASK_CALCULATOR = "/data-glove/rest/v1/task-calculator";
    public static final String DB_TABLE_FRAME_DB = "/data-glove/rest/v1/frameDB";
    public static final String DB_TABLE_USER = "/data-glove/rest/v1/user";
    public static final String DB_TABLE_GESTURE = "/data-glove/rest/v1/Gesture";
    public static final String DB_TABLE_EULER_GESTURE = "/data-glove/rest/v1/Euler";
    public static final String DB_TABLE_RECOGNITION_LOG = "/data-glove/rest/v1/RecognitionLog";
    public static final String DB_TABLE_RECOGNITION_GESTURE = "/data-glove/rest/v1/RecognitionGesture";
    public static final String DB_TABLE_ROBOT_ACTION = "/data-glove/rest/v1/RobotAction";
    public static final String DB_TABLE_OBJECT_TRACKING = "/data-glove/rest/v1/ObjectTracking";
    public static final String DB_TABLE_GESTURE_NAIVE_BAYES = "/data-glove/rest/v1/GestureNaiveBayes";


    //exceptions
    public static final String INVALID_SENSOR_NUMBER_EXCEPTION = "INVALID_SENSOR_NUMBER_EXCEPTION";
    public static final String INVALID_ARRAY_INDEX_EXCEPTION = "INVALID_ARRAY_INDEX_EXCEPTION";

}
