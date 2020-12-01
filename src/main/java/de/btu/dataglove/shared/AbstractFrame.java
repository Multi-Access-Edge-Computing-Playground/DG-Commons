package de.btu.dataglove.shared;

import java.io.Serializable;

public abstract class AbstractFrame implements Serializable {
    private static final long serialVersionUID = 1540638284367827038L;

    public String nameOfTask;
    public String userName;
    public final double timeStamp;
    public final int frameNumber;
    public int recordingNumber;
    public int typeOfRecording;

    public AbstractFrame(String nameOfTask, String userName, double timeStamp, int frameNumber, int recordingNumber, int typeOfRecording) {
        this.nameOfTask = nameOfTask;
        this.userName = userName;
        this.timeStamp = timeStamp;
        this.frameNumber = frameNumber;
        this.recordingNumber = recordingNumber;
        this.typeOfRecording = typeOfRecording;
    }
}
