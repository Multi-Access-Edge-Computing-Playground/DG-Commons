package de.btu.dataglove.shared;

import java.util.LinkedList;
import java.util.List;

/*
represents a single frame with quaternions having been converted to euler angles
 */
public class EulerFrame extends AbstractFrame implements Comparable<EulerFrame> {

    private final double[] phi;
    private final double[] theta;
    private final double[] psi;

    private final double[] accX;
    private final double[] accY;
    private final double[] accZ;

    public EulerFrame(Frame frame) {

        super(frame.nameOfTask, frame.userName, frame.timeStamp,
                frame.frameNumber, frame.recordingNumber, frame.typeOfRecording);
        nameOfTask = frame.nameOfTask;
        accX = frame.accX;
        accY = frame.accY;
        accZ = frame.accZ;

        phi = new double[SharedConstants.NUMBER_OF_SENSORS];
        theta = new double[SharedConstants.NUMBER_OF_SENSORS];
        psi = new double[SharedConstants.NUMBER_OF_SENSORS];

        double x;
        double y;
        for (int i = 0; i < SharedConstants.NUMBER_OF_SENSORS; i++) {
            x = 2 * (frame.rawRotScalar[i] * frame.rawRotVectorX[i] + frame.rawRotVectorY[i] * frame.rawRotVectorZ[i]);
            y = 1 - 2 * (Math.pow(frame.rawRotVectorX[i], 2) + Math.pow(frame.rawRotVectorY[i], 2));
            phi[i] = Math.atan2(x, y);

            theta[i] = Math.asin(2 * (frame.rawRotScalar[i] * frame.rawRotVectorY[i] - frame.rawRotVectorZ[i] * frame.rawRotVectorX[i]));

            x = 2 * (frame.rawRotScalar[i] * frame.rawRotVectorZ[i] + frame.rawRotVectorX[i] * frame.rawRotVectorY[i]);
            y = 1 - 2 * (Math.pow(frame.rawRotVectorY[i], 2) + Math.pow(frame.rawRotVectorZ[i], 2));
            psi[i] = Math.atan2(x, y);
        }
    }
    public double[] getPhiAsRadian() {
        return phi;
    }

    public double[] getThetaAsRadian() {
        return theta;
    }

    public double[] getPsiAsRadian() {
        return psi;
    }

    public double[] getPhiAsDegrees() {
        double[] result = new double[phi.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Math.toDegrees(phi[i]);
        }
        return result;
    }

    public double[] getThetaAsDegrees() {
        double[] result = new double[theta.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Math.toDegrees(theta[i]);
        }
        return result;
    }

    public double[] getPsiAsDegrees() {
        double[] result = new double[psi.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Math.toDegrees(psi[i]);
        }
        return result;
    }

    public double[] getAccX() {
        return accX;
    }

    public double[] getAccY() {
        return accY;
    }

    public double[] getAccZ() {
        return accZ;
    }

    public void makeAnglesPositive() {
        for (int i = 0; i < SharedConstants.NUMBER_OF_SENSORS; i++) {
            if (phi[i] < 0) {
                phi[i] += 360;
            }

            if (theta[i] < 0) {
                theta[i] += 360;
            }

            if (psi[i] < 0) {
                psi[i] += 360;
            }
        }
    }

    /*
    returns all angles in a single array
    order: 0  sensor24phi
           1  sensor25phi
           ...
           13 sensor46phi
           14 sensor24theta
           ...
     */
    public double[] getAllAngles() {
        List<Double> resultList = new LinkedList<>();
        resultList.addAll(SharedUtility.array2List(phi));
        resultList.addAll(SharedUtility.array2List(theta));
        resultList.addAll(SharedUtility.array2List(psi));

        return SharedUtility.list2Array(resultList);
    }

    @Override
    public int compareTo(EulerFrame o) {
        return nameOfTask.compareTo(o.nameOfTask);
    }
}
