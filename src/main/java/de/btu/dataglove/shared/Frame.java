package de.btu.dataglove.shared;

import de.btu.dataglove.shared.webservice.DBFrame;

import java.util.LinkedList;
import java.util.List;

/*
represents a single frame captured with the data glove
 */
public class Frame extends AbstractFrame implements Comparable<Frame> {

    public final double[] rawRotScalar;
    public final double[] rawRotVectorX;
    public final double[] rawRotVectorY;
    public final double[] rawRotVectorZ;
    public final double[] rotScalar;
    public final double[] rotVectorX;
    public final double[] rotVectorY;
    public final double[] rotVectorZ;
    public final double[] accX;
    public final double[] accY;
    public final double[] accZ;

    private transient EulerFrame eulerRepresentation;
    private transient EulerDiffFrame eulerDiffRepresentation;
    private transient DBFrame dbRepresentation;

    /*
    constructor for a frame. Arrays - sensor matching as follows:
    Array 00 - Sensor 24
    Array 01 - Sensor 25
    Array 02 - Sensor 26
    Array 03 - Sensor 27
    Array 04 - Sensor 28
    Array 05 - Sensor 29
    Array 06 - Sensor 30
    Array 07 - Sensor 40
    Array 08 - Sensor 41
    Array 09 - Sensor 42
    Array 10 - Sensor 43
    Array 11 - Sensor 44
    Array 12 - Sensor 45
    Array 13 - Sensor 46
     */
    public Frame(String nameOfTask, String userName, double timeStamp, int frameNumber, int recordingNumber, int typeOfRecording,
                 double[] rawRotScalar, double[] rawRotVectorX, double[] rawRotVectorY, double[] rawRotVectorZ,
                 double[] rotScalar, double[] rotVectorX, double[] rotVectorY, double[] rotVectorZ,
                 double[] accX, double[] accY, double[] accZ) {

        super(nameOfTask, userName, timeStamp, frameNumber, recordingNumber, typeOfRecording);

        this.rawRotScalar = rawRotScalar;
        this.rawRotVectorX = rawRotVectorX;
        this.rawRotVectorY = rawRotVectorY;
        this.rawRotVectorZ = rawRotVectorZ;
        this.rotScalar = rotScalar;
        this.rotVectorX = rotVectorX;
        this.rotVectorY = rotVectorY;
        this.rotVectorZ = rotVectorZ;
        this.accX = accX;
        this.accY = accY;
        this.accZ = accZ;
    }

    /*
    returns all sensor data of a frame as a single array
    Order: 0  sensor24rawRotScalar
           1  sensor25rawRotScalar
           2  sensor26RawRotScalar
           ...
           13 sensor46RawRotScalar
           14 sensor24RawRotVectorX
           15 sensor25RawRotVectorX
           ...
     */
    public double[] getAllFrameData() {
        List<Double> resultList = new LinkedList<>();
        resultList.addAll(SharedUtility.array2List(this.rawRotScalar));
        resultList.addAll(SharedUtility.array2List(this.rawRotVectorX));
        resultList.addAll(SharedUtility.array2List(this.rawRotVectorY));
        resultList.addAll(SharedUtility.array2List(this.rawRotVectorZ));
        resultList.addAll(SharedUtility.array2List(this.rotScalar));
        resultList.addAll(SharedUtility.array2List(this.rotVectorX));
        resultList.addAll(SharedUtility.array2List(this.rotVectorY));
        resultList.addAll(SharedUtility.array2List(this.rotVectorZ));
        resultList.addAll(SharedUtility.array2List(this.accX));
        resultList.addAll(SharedUtility.array2List(this.accY));
        resultList.addAll(SharedUtility.array2List(this.accZ));

        return SharedUtility.list2Array(resultList);
    }

    public double[] getAllRelevantAccelerations(TypeOfGesture typeOfGesture) {
        if (typeOfGesture == null) return getAllAccelerations();
        switch (typeOfGesture) {
            case STATIC_GESTURE_LEFT:
            case DYNAMIC_GESTURE_LEFT:
                return getAllAccelerationsLeftHand();
            case STATIC_GESTURE_RIGHT:
            case DYNAMIC_GESTURE_RIGHT:
                return getAllAccelerationsRightHand();
            case STATIC_GESTURE_BOTH:
            case DYNAMIC_GESTURE_BOTH:
                return getAllAccelerations();
        }
        throw new AssertionError("this should never be reachable");
    }

    public double[] getAllAccelerations() {
        List<Double> resultList = new LinkedList<>();
        resultList.addAll(SharedUtility.array2List(this.accX));
        resultList.addAll(SharedUtility.array2List(this.accY));
        resultList.addAll(SharedUtility.array2List(this.accZ));
        return SharedUtility.list2Array(resultList);
    }

    private double[] getAllAccelerationsLeftHand() {
        List<Double> resultList = new LinkedList<>();
        SharedUtility.addToListLeftHand(resultList, accX);
        SharedUtility.addToListLeftHand(resultList, accY);
        SharedUtility.addToListLeftHand(resultList, accZ);
        return SharedUtility.list2Array(resultList);
    }

    private double[] getAllAccelerationsRightHand() {
        List<Double> resultList = new LinkedList<>();
        SharedUtility.addToListRightHand(resultList, accX);
        SharedUtility.addToListRightHand(resultList, accY);
        SharedUtility.addToListRightHand(resultList, accZ);
        return SharedUtility.list2Array(resultList);
    }

    @Override
    public int compareTo(Frame f) {
        return Integer.compare(frameNumber, f.frameNumber);
    }

    public EulerFrame getEulerRepresentation() {
        if (eulerRepresentation == null) {
            eulerRepresentation = new EulerFrame(this);
        }
        return eulerRepresentation;
    }

    public EulerDiffFrame getEulerDiffRepresentation() {
        if (eulerDiffRepresentation == null) {
            eulerDiffRepresentation = new EulerDiffFrame(this);
        }
        return eulerDiffRepresentation;
    }

    public DBFrame getDbRepresentation() {
        if (dbRepresentation == null) {
            dbRepresentation = new DBFrame(this);
        }
        return dbRepresentation;
    }
}

