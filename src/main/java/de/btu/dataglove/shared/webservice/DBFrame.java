package de.btu.dataglove.shared.webservice;

import de.btu.dataglove.shared.Frame;

/*
represents a single frame like it's stored in the database
 */
public class DBFrame implements Comparable<DBFrame> {
    public final String nameOfTask;
    public final String userName;
    public final double timeStamp;
    public final int frameNumber;
    public final int recordingNumber;
    public final int typeOfRecording;

    public final double sensor24RawRotScalar;
    public final double sensor24RawRotVectorX;
    public final double sensor24RawRotVectorY;
    public final double sensor24RawRotVectorZ;
    public final double sensor24RotScalar;
    public final double sensor24RotVectorX;
    public final double sensor24RotVectorY;
    public final double sensor24RotVectorZ;
    public final double sensor24AccX;
    public final double sensor24AccY;
    public final double sensor24AccZ;

    public final double sensor25RawRotScalar;
    public final double sensor25RawRotVectorX;
    public final double sensor25RawRotVectorY;
    public final double sensor25RawRotVectorZ;
    public final double sensor25RotScalar;
    public final double sensor25RotVectorX;
    public final double sensor25RotVectorY;
    public final double sensor25RotVectorZ;
    public final double sensor25AccX;
    public final double sensor25AccY;
    public final double sensor25AccZ;

    public final double sensor26RawRotScalar;
    public final double sensor26RawRotVectorX;
    public final double sensor26RawRotVectorY;
    public final double sensor26RawRotVectorZ;
    public final double sensor26RotScalar;
    public final double sensor26RotVectorX;
    public final double sensor26RotVectorY;
    public final double sensor26RotVectorZ;
    public final double sensor26AccX;
    public final double sensor26AccY;
    public final double sensor26AccZ;

    public final double sensor27RawRotScalar;
    public final double sensor27RawRotVectorX;
    public final double sensor27RawRotVectorY;
    public final double sensor27RawRotVectorZ;
    public final double sensor27RotScalar;
    public final double sensor27RotVectorX;
    public final double sensor27RotVectorY;
    public final double sensor27RotVectorZ;
    public final double sensor27AccX;
    public final double sensor27AccY;
    public final double sensor27AccZ;

    public final double sensor28RawRotScalar;
    public final double sensor28RawRotVectorX;
    public final double sensor28RawRotVectorY;
    public final double sensor28RawRotVectorZ;
    public final double sensor28RotScalar;
    public final double sensor28RotVectorX;
    public final double sensor28RotVectorY;
    public final double sensor28RotVectorZ;
    public final double sensor28AccX;
    public final double sensor28AccY;
    public final double sensor28AccZ;

    public final double sensor29RawRotScalar;
    public final double sensor29RawRotVectorX;
    public final double sensor29RawRotVectorY;
    public final double sensor29RawRotVectorZ;
    public final double sensor29RotScalar;
    public final double sensor29RotVectorX;
    public final double sensor29RotVectorY;
    public final double sensor29RotVectorZ;
    public final double sensor29AccX;
    public final double sensor29AccY;
    public final double sensor29AccZ;

    public final double sensor30RawRotScalar;
    public final double sensor30RawRotVectorX;
    public final double sensor30RawRotVectorY;
    public final double sensor30RawRotVectorZ;
    public final double sensor30RotScalar;
    public final double sensor30RotVectorX;
    public final double sensor30RotVectorY;
    public final double sensor30RotVectorZ;
    public final double sensor30AccX;
    public final double sensor30AccY;
    public final double sensor30AccZ;

    public final double sensor40RawRotScalar;
    public final double sensor40RawRotVectorX;
    public final double sensor40RawRotVectorY;
    public final double sensor40RawRotVectorZ;
    public final double sensor40RotScalar;
    public final double sensor40RotVectorX;
    public final double sensor40RotVectorY;
    public final double sensor40RotVectorZ;
    public final double sensor40AccX;
    public final double sensor40AccY;
    public final double sensor40AccZ;

    public final double sensor41RawRotScalar;
    public final double sensor41RawRotVectorX;
    public final double sensor41RawRotVectorY;
    public final double sensor41RawRotVectorZ;
    public final double sensor41RotScalar;
    public final double sensor41RotVectorX;
    public final double sensor41RotVectorY;
    public final double sensor41RotVectorZ;
    public final double sensor41AccX;
    public final double sensor41AccY;
    public final double sensor41AccZ;

    public final double sensor42RawRotScalar;
    public final double sensor42RawRotVectorX;
    public final double sensor42RawRotVectorY;
    public final double sensor42RawRotVectorZ;
    public final double sensor42RotScalar;
    public final double sensor42RotVectorX;
    public final double sensor42RotVectorY;
    public final double sensor42RotVectorZ;
    public final double sensor42AccX;
    public final double sensor42AccY;
    public final double sensor42AccZ;

    public final double sensor43RawRotScalar;
    public final double sensor43RawRotVectorX;
    public final double sensor43RawRotVectorY;
    public final double sensor43RawRotVectorZ;
    public final double sensor43RotScalar;
    public final double sensor43RotVectorX;
    public final double sensor43RotVectorY;
    public final double sensor43RotVectorZ;
    public final double sensor43AccX;
    public final double sensor43AccY;
    public final double sensor43AccZ;

    public final double sensor44RawRotScalar;
    public final double sensor44RawRotVectorX;
    public final double sensor44RawRotVectorY;
    public final double sensor44RawRotVectorZ;
    public final double sensor44RotScalar;
    public final double sensor44RotVectorX;
    public final double sensor44RotVectorY;
    public final double sensor44RotVectorZ;
    public final double sensor44AccX;
    public final double sensor44AccY;
    public final double sensor44AccZ;

    public final double sensor45RawRotScalar;
    public final double sensor45RawRotVectorX;
    public final double sensor45RawRotVectorY;
    public final double sensor45RawRotVectorZ;
    public final double sensor45RotScalar;
    public final double sensor45RotVectorX;
    public final double sensor45RotVectorY;
    public final double sensor45RotVectorZ;
    public final double sensor45AccX;
    public final double sensor45AccY;
    public final double sensor45AccZ;

    public final double sensor46RawRotScalar;
    public final double sensor46RawRotVectorX;
    public final double sensor46RawRotVectorY;
    public final double sensor46RawRotVectorZ;
    public final double sensor46RotScalar;
    public final double sensor46RotVectorX;
    public final double sensor46RotVectorY;
    public final double sensor46RotVectorZ;
    public final double sensor46AccX;
    public final double sensor46AccY;
    public final double sensor46AccZ;

    public DBFrame(Frame frame) {
        nameOfTask = frame.nameOfTask;
        userName = frame.userName;
        timeStamp = frame.timeStamp;
         frameNumber = frame.frameNumber;
        recordingNumber = frame.recordingNumber;
        typeOfRecording = frame.typeOfRecording;
        sensor24RawRotScalar = frame.rawRotScalar[0];
        sensor24RawRotVectorX = frame.rawRotVectorX[0];
        sensor24RawRotVectorY = frame.rawRotVectorY[0];
        sensor24RawRotVectorZ = frame.rawRotVectorZ[0];
        sensor24RotScalar = frame.rotScalar[0];
        sensor24RotVectorX = frame.rotVectorX[0];
        sensor24RotVectorY = frame.rotVectorY[0];
        sensor24RotVectorZ = frame.rotVectorZ[0];
        sensor24AccX = frame.accX[0];
        sensor24AccY = frame.accY[0];
        sensor24AccZ = frame.accZ[0];

        sensor25RawRotScalar = frame.rawRotScalar[1];
        sensor25RawRotVectorX = frame.rawRotVectorX[1];
        sensor25RawRotVectorY = frame.rawRotVectorY[1];
        sensor25RawRotVectorZ = frame.rawRotVectorZ[1];
        sensor25RotScalar = frame.rotScalar[1];
        sensor25RotVectorX = frame.rotVectorX[1];
        sensor25RotVectorY = frame.rotVectorY[1];
        sensor25RotVectorZ = frame.rotVectorZ[1];
        sensor25AccX = frame.accX[1];
        sensor25AccY = frame.accY[1];
        sensor25AccZ = frame.accZ[1];

        sensor26RawRotScalar = frame.rawRotScalar[2];
        sensor26RawRotVectorX = frame.rawRotVectorX[2];
        sensor26RawRotVectorY = frame.rawRotVectorY[2];
        sensor26RawRotVectorZ = frame.rawRotVectorZ[2];
        sensor26RotScalar = frame.rotScalar[2];
        sensor26RotVectorX = frame.rotVectorX[2];
        sensor26RotVectorY = frame.rotVectorY[2];
        sensor26RotVectorZ = frame.rotVectorZ[2];
        sensor26AccX = frame.accX[2];
        sensor26AccY = frame.accY[2];
        sensor26AccZ = frame.accZ[2];

        sensor27RawRotScalar = frame.rawRotScalar[3];
        sensor27RawRotVectorX = frame.rawRotVectorX[3];
        sensor27RawRotVectorY = frame.rawRotVectorY[3];
        sensor27RawRotVectorZ = frame.rawRotVectorZ[3];
        sensor27RotScalar = frame.rotScalar[3];
        sensor27RotVectorX = frame.rotVectorX[3];
        sensor27RotVectorY = frame.rotVectorY[3];
        sensor27RotVectorZ = frame.rotVectorZ[3];
        sensor27AccX = frame.accX[3];
        sensor27AccY = frame.accY[3];
        sensor27AccZ = frame.accZ[3];

        sensor28RawRotScalar = frame.rawRotScalar[4];
        sensor28RawRotVectorX = frame.rawRotVectorX[4];
        sensor28RawRotVectorY = frame.rawRotVectorY[4];
        sensor28RawRotVectorZ = frame.rawRotVectorZ[4];
        sensor28RotScalar = frame.rotScalar[4];
        sensor28RotVectorX = frame.rotVectorX[4];
        sensor28RotVectorY = frame.rotVectorY[4];
        sensor28RotVectorZ = frame.rotVectorZ[4];
        sensor28AccX = frame.accX[4];
        sensor28AccY = frame.accY[4];
        sensor28AccZ = frame.accZ[4];

        sensor29RawRotScalar = frame.rawRotScalar[5];
        sensor29RawRotVectorX = frame.rawRotVectorX[5];
        sensor29RawRotVectorY = frame.rawRotVectorY[5];
        sensor29RawRotVectorZ = frame.rawRotVectorZ[5];
        sensor29RotScalar = frame.rotScalar[5];
        sensor29RotVectorX = frame.rotVectorX[5];
        sensor29RotVectorY = frame.rotVectorY[5];
        sensor29RotVectorZ = frame.rotVectorZ[5];
        sensor29AccX = frame.accX[5];
        sensor29AccY = frame.accY[5];
        sensor29AccZ = frame.accZ[5];

        sensor30RawRotScalar = frame.rawRotScalar[6];
        sensor30RawRotVectorX = frame.rawRotVectorX[6];
        sensor30RawRotVectorY = frame.rawRotVectorY[6];
        sensor30RawRotVectorZ = frame.rawRotVectorZ[6];
        sensor30RotScalar = frame.rotScalar[6];
        sensor30RotVectorX = frame.rotVectorX[6];
        sensor30RotVectorY = frame.rotVectorY[6];
        sensor30RotVectorZ = frame.rotVectorZ[6];
        sensor30AccX = frame.accX[6];
        sensor30AccY = frame.accY[6];
        sensor30AccZ = frame.accZ[6];

        sensor40RawRotScalar = frame.rawRotScalar[7];
        sensor40RawRotVectorX = frame.rawRotVectorX[7];
        sensor40RawRotVectorY = frame.rawRotVectorY[7];
        sensor40RawRotVectorZ = frame.rawRotVectorZ[7];
        sensor40RotScalar = frame.rotScalar[7];
        sensor40RotVectorX = frame.rotVectorX[7];
        sensor40RotVectorY = frame.rotVectorY[7];
        sensor40RotVectorZ = frame.rotVectorZ[7];
        sensor40AccX = frame.accX[7];
        sensor40AccY = frame.accY[7];
        sensor40AccZ = frame.accZ[7];

        sensor41RawRotScalar = frame.rawRotScalar[8];
        sensor41RawRotVectorX = frame.rawRotVectorX[8];
        sensor41RawRotVectorY = frame.rawRotVectorY[8];
        sensor41RawRotVectorZ = frame.rawRotVectorZ[8];
        sensor41RotScalar = frame.rotScalar[8];
        sensor41RotVectorX = frame.rotVectorX[8];
        sensor41RotVectorY = frame.rotVectorY[8];
        sensor41RotVectorZ = frame.rotVectorZ[8];
        sensor41AccX = frame.accX[8];
        sensor41AccY = frame.accY[8];
        sensor41AccZ = frame.accZ[8];

        sensor42RawRotScalar = frame.rawRotScalar[9];
        sensor42RawRotVectorX = frame.rawRotVectorX[9];
        sensor42RawRotVectorY = frame.rawRotVectorY[9];
        sensor42RawRotVectorZ = frame.rawRotVectorZ[9];
        sensor42RotScalar = frame.rotScalar[9];
        sensor42RotVectorX = frame.rotVectorX[9];
        sensor42RotVectorY = frame.rotVectorY[9];
        sensor42RotVectorZ = frame.rotVectorZ[9];
        sensor42AccX = frame.accX[9];
        sensor42AccY = frame.accY[9];
        sensor42AccZ = frame.accZ[9];

        sensor43RawRotScalar = frame.rawRotScalar[10];
        sensor43RawRotVectorX = frame.rawRotVectorX[10];
        sensor43RawRotVectorY = frame.rawRotVectorY[10];
        sensor43RawRotVectorZ = frame.rawRotVectorZ[10];
        sensor43RotScalar = frame.rotScalar[10];
        sensor43RotVectorX = frame.rotVectorX[10];
        sensor43RotVectorY = frame.rotVectorY[10];
        sensor43RotVectorZ = frame.rotVectorZ[10];
        sensor43AccX = frame.accX[10];
        sensor43AccY = frame.accY[10];
        sensor43AccZ = frame.accZ[10];

        sensor44RawRotScalar = frame.rawRotScalar[11];
        sensor44RawRotVectorX = frame.rawRotVectorX[11];
        sensor44RawRotVectorY = frame.rawRotVectorY[11];
        sensor44RawRotVectorZ = frame.rawRotVectorZ[11];
        sensor44RotScalar = frame.rotScalar[11];
        sensor44RotVectorX = frame.rotVectorX[11];
        sensor44RotVectorY = frame.rotVectorY[11];
        sensor44RotVectorZ = frame.rotVectorZ[11];
        sensor44AccX = frame.accX[11];
        sensor44AccY = frame.accY[11];
        sensor44AccZ = frame.accZ[11];

        sensor45RawRotScalar = frame.rawRotScalar[12];
        sensor45RawRotVectorX = frame.rawRotVectorX[12];
        sensor45RawRotVectorY = frame.rawRotVectorY[12];
        sensor45RawRotVectorZ = frame.rawRotVectorZ[12];
        sensor45RotScalar = frame.rotScalar[12];
        sensor45RotVectorX = frame.rotVectorX[12];
        sensor45RotVectorY = frame.rotVectorY[12];
        sensor45RotVectorZ = frame.rotVectorZ[12];
        sensor45AccX = frame.accX[12];
        sensor45AccY = frame.accY[12];
        sensor45AccZ = frame.accZ[12];

        sensor46RawRotScalar = frame.rawRotScalar[13];
        sensor46RawRotVectorX = frame.rawRotVectorX[13];
        sensor46RawRotVectorY = frame.rawRotVectorY[13];
        sensor46RawRotVectorZ = frame.rawRotVectorZ[13];
        sensor46RotScalar = frame.rotScalar[13];
        sensor46RotVectorX = frame.rotVectorX[13];
        sensor46RotVectorY = frame.rotVectorY[13];
        sensor46RotVectorZ = frame.rotVectorZ[13];
        sensor46AccX = frame.accX[13];
        sensor46AccY = frame.accY[13];
        sensor46AccZ = frame.accZ[13];
    }

    /*
    converts a dbFrame to a normal frame
    */
    public Frame convertToFrame() {

        double[] rawRotScalar = {sensor24RawRotScalar, sensor25RawRotScalar, sensor26RawRotScalar, sensor27RawRotScalar, sensor28RawRotScalar, sensor29RawRotScalar, sensor30RawRotScalar, sensor40RawRotScalar, sensor41RawRotScalar, sensor42RawRotScalar, sensor43RawRotScalar, sensor44RawRotScalar, sensor45RawRotScalar, sensor46RawRotScalar};
        double[] rawRotVectorX = {sensor24RawRotVectorX, sensor25RawRotVectorX, sensor26RawRotVectorX, sensor27RawRotVectorX, sensor28RawRotVectorX, sensor29RawRotVectorX, sensor30RawRotVectorX, sensor40RawRotVectorX, sensor41RawRotVectorX, sensor42RawRotVectorX, sensor43RawRotVectorX, sensor44RawRotVectorX, sensor45RawRotVectorX, sensor46RawRotVectorX};
        double[] rawRotVectorY = {sensor24RawRotVectorY, sensor25RawRotVectorY, sensor26RawRotVectorY, sensor27RawRotVectorY, sensor28RawRotVectorY, sensor29RawRotVectorY, sensor30RawRotVectorY, sensor40RawRotVectorY, sensor41RawRotVectorY, sensor42RawRotVectorY, sensor43RawRotVectorY, sensor44RawRotVectorY, sensor45RawRotVectorY, sensor46RawRotVectorY};
        double[] rawRotVectorZ = {sensor24RawRotVectorZ, sensor25RawRotVectorZ, sensor26RawRotVectorZ, sensor27RawRotVectorZ, sensor28RawRotVectorZ, sensor29RawRotVectorZ, sensor30RawRotVectorZ, sensor40RawRotVectorZ, sensor41RawRotVectorZ, sensor42RawRotVectorZ, sensor43RawRotVectorZ, sensor44RawRotVectorZ, sensor45RawRotVectorZ, sensor46RawRotVectorZ};
        double[] rotScalar = {sensor24RotScalar, sensor25RotScalar, sensor26RotScalar, sensor27RotScalar, sensor28RotScalar, sensor29RotScalar, sensor30RotScalar, sensor40RotScalar, sensor41RotScalar, sensor42RotScalar, sensor43RotScalar, sensor44RotScalar, sensor45RotScalar, sensor46RotScalar};
        double[] rotVectorX = {sensor24RotVectorX, sensor25RotVectorX, sensor26RotVectorX, sensor27RotVectorX, sensor28RotVectorX, sensor29RotVectorX, sensor30RotVectorX, sensor40RotVectorX, sensor41RotVectorX, sensor42RotVectorX, sensor43RotVectorX, sensor44RotVectorX, sensor45RotVectorX, sensor46RotVectorX};
        double[] rotVectorY = {sensor24RotVectorY, sensor25RotVectorY, sensor26RotVectorY, sensor27RotVectorY, sensor28RotVectorY, sensor29RotVectorY, sensor30RotVectorY, sensor40RotVectorY, sensor41RotVectorY, sensor42RotVectorY, sensor43RotVectorY, sensor44RotVectorY, sensor45RotVectorY, sensor46RotVectorY};
        double[] rotVectorZ = {sensor24RotVectorZ, sensor25RotVectorZ, sensor26RotVectorZ, sensor27RotVectorZ, sensor28RotVectorZ, sensor29RotVectorZ, sensor30RotVectorZ, sensor40RotVectorZ, sensor41RotVectorZ, sensor42RotVectorZ, sensor43RotVectorZ, sensor44RotVectorZ, sensor45RotVectorZ, sensor46RotVectorZ};
        double[] accX = {sensor24AccX, sensor25AccX, sensor26AccX, sensor27AccX, sensor28AccX, sensor29AccX, sensor30AccX, sensor40AccX, sensor41AccX, sensor42AccX, sensor43AccX, sensor44AccX, sensor45AccX, sensor46AccX};
        double[] accY = {sensor24AccY, sensor25AccY, sensor26AccY, sensor27AccY, sensor28AccY, sensor29AccY, sensor30AccY, sensor40AccY, sensor41AccY, sensor42AccY, sensor43AccY, sensor44AccY, sensor45AccY, sensor46AccY};
        double[] accZ = {sensor24AccZ, sensor25AccZ, sensor26AccZ, sensor27AccZ, sensor28AccZ, sensor29AccZ, sensor30AccZ, sensor40AccZ, sensor41AccZ, sensor42AccZ, sensor43AccZ, sensor44AccZ, sensor45AccZ, sensor46AccZ};

        return new Frame(nameOfTask, userName, timeStamp, frameNumber, recordingNumber, typeOfRecording,
                rawRotScalar, rawRotVectorX, rawRotVectorY, rawRotVectorZ, rotScalar, rotVectorX, rotVectorY, rotVectorZ,
                accX, accY, accZ);
    }

    @Override
    public int compareTo(DBFrame o) {
        return nameOfTask.compareTo(o.nameOfTask);
    }
}
