package de.btu.dataglove.shared;

import org.jetbrains.annotations.NotNull;

/**
 * represents a frame as differences of euler angles where the angles of the back of the hand are subtracted from the other sensors angles
 */
public class EulerDiffFrame extends AbstractFrame implements Comparable<EulerDiffFrame> {

    private final Double[] phiDiffs;
    private final Double[] thetaDiffs;
    private final Double[] psiDiffs;

    private final double[] accX;
    private final double[] accY;
    private final double[] accZ;

    public EulerDiffFrame(Frame frame) {
        super(frame.nameOfTask, frame.userName, frame.timeStamp,
                frame.frameNumber, frame.recordingNumber, frame.typeOfRecording);

        accX = frame.accX;
        accY = frame.accY;
        accZ = frame.accZ;

        EulerFrame eulerFrame = frame.getEulerRepresentation();

        phiDiffs = new Double[SharedConstants.NUMBER_OF_SENSORS];
        thetaDiffs = new Double[SharedConstants.NUMBER_OF_SENSORS];
        psiDiffs = new Double[SharedConstants.NUMBER_OF_SENSORS];

        for (int i = 0; i < phiDiffs.length; i++) {
            if (i != SensorIDs.LEFT_BACK_OF_HAND.getArrayPosition() && i != SensorIDs.RIGHT_BACK_OF_HAND.getArrayPosition()) {
                if (i <= SharedUtility.getArrayPositionOfSensor(SharedConstants.GLOVE_ONE_HIGHEST_SENSOR_ID)) {
                    phiDiffs[i] = eulerFrame.getPhiAsRadian()[i] - eulerFrame.getPhiAsRadian()[SensorIDs.LEFT_BACK_OF_HAND.getArrayPosition()];
                    thetaDiffs[i] = eulerFrame.getThetaAsRadian()[i] - eulerFrame.getThetaAsRadian()[SensorIDs.LEFT_BACK_OF_HAND.getArrayPosition()];
                    psiDiffs[i] = eulerFrame.getPsiAsRadian()[i] - eulerFrame.getPsiAsRadian()[SensorIDs.LEFT_BACK_OF_HAND.getArrayPosition()];
                } else {
                    phiDiffs[i] = eulerFrame.getPhiAsRadian()[i] - eulerFrame.getPhiAsRadian()[SensorIDs.RIGHT_BACK_OF_HAND.getArrayPosition()];
                    thetaDiffs[i] = eulerFrame.getThetaAsRadian()[i] - eulerFrame.getThetaAsRadian()[SensorIDs.RIGHT_BACK_OF_HAND.getArrayPosition()];
                    psiDiffs[i] = eulerFrame.getPsiAsRadian()[i] - eulerFrame.getPsiAsRadian()[SensorIDs.RIGHT_BACK_OF_HAND.getArrayPosition()];
                }
            }
        }
    }

    public Double[] getPhiDiffsAsRadian() {
        return phiDiffs;
    }

    public Double[] getThetaDiffsAsRadian() {
        return thetaDiffs;
    }

    public Double[] getPsiDiffsAsRadian() {
        return psiDiffs;
    }

    public Double[] getPhiDiffsAsDegrees() {
        Double[] result = new Double[phiDiffs.length];
        for (int i = 0; i < result.length; i++) {
            if (phiDiffs[i] != null) {
                result[i] = Math.toDegrees(phiDiffs[i]);
            }
        }
        return result;
    }

    public Double[] getThetaDiffsAsDegrees() {
        Double[] result = new Double[thetaDiffs.length];
        for (int i = 0; i < result.length; i++) {
            if (thetaDiffs[i] != null) {
                result[i] = Math.toDegrees(thetaDiffs[i]);
            }
        }
        return result;
    }

    public Double[] getPsiDiffsAsDegrees() {
        Double[] result = new Double[psiDiffs.length];
        for (int i = 0; i < result.length; i++) {
            if (psiDiffs[i] != null) {
                result[i] = Math.toDegrees(psiDiffs[i]);
            }
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

    @Override
    public int compareTo(EulerDiffFrame f) {
        return Integer.compare(frameNumber, f.frameNumber);
    }
}
