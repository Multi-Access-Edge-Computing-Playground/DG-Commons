package de.btu.dataglove.shared.webservice;

import de.btu.dataglove.shared.*;

/**
 * a RecognitionLog as it is represented in the database
 */
public class RecognitionLog {
    String name; //name of the log
    boolean wasRecognized;

    String frameDB_nameOfTask;
    int frameDB_recordingNumber;
    int frameDB_frameNumber;

    String gesture_name;
    int gesture_algorithmUsedForCalculation;
    double[] gesture_algorithmParameters;

    String euler_name;
    int euler_algorithmUsedForCalculation;
    double[] euler_algorithmParameters;

    protected RecognitionLog(String nameOfLog, AbstractFrame frame, boolean wasRecognized, AbstractGesture gesture) {
        name = nameOfLog;
        this.wasRecognized = wasRecognized;
        if (frame instanceof Frame) {
            frameDB_nameOfTask = frame.nameOfTask;
            frameDB_frameNumber = frame.frameNumber;
            frameDB_recordingNumber = frame.recordingNumber;
        } else {
            throw new AssertionError("only Frame.class is supported");
        }

        if (gesture instanceof Gesture) {
            gesture_name = gesture.name;
            gesture_algorithmUsedForCalculation = gesture.algorithmUsedForCalculation;
            gesture_algorithmParameters = gesture.algorithmParameters;
        } else {
            if (gesture instanceof EulerGesture) {
                euler_name = gesture.name;
                euler_algorithmUsedForCalculation = gesture.algorithmUsedForCalculation;
                euler_algorithmParameters = gesture.algorithmParameters;
            } else {
                throw new AssertionError("only Gesture.class and EulerGesture.class are supported");
            }
        }
    }
}
