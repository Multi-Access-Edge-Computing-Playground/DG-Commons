package de.btu.dataglove.shared;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

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

    private transient double[] allAngleDiffsLeft;
    private transient double[] allAngleDiffsRight;
    private transient double[] allAngleDiffsBoth;

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

    /**
     * returns relevant angle differences (based on type of gesture) as radian
     */
    public double[] getAllRelevantAngleDiffs(TypeOfGesture typeOfGesture) {
        switch (typeOfGesture) {
            case STATIC_GESTURE_LEFT:
            case DYNAMIC_GESTURE_LEFT:
                return getAllAngleDiffsLeftHand();
            case STATIC_GESTURE_RIGHT:
            case DYNAMIC_GESTURE_RIGHT:
                return getAllAngleDiffsRightHand();
            case STATIC_GESTURE_BOTH:
            case DYNAMIC_GESTURE_BOTH:
                return getAllAngleDiffsBothHands();
        }
        throw new AssertionError("this should never be reached");
    }

    private double[] getAllAngleDiffsLeftHand() {
        if (allAngleDiffsLeft == null) {
            List<Double> resultList = new LinkedList<>();
            SharedUtility.addToListLeftHand(resultList, filterOutBackOfHand(phiDiffs));
            SharedUtility.addToListLeftHand(resultList, filterOutBackOfHand(thetaDiffs));
            SharedUtility.addToListLeftHand(resultList, filterOutBackOfHand(psiDiffs));
            allAngleDiffsLeft = SharedUtility.list2Array(resultList);
        }
        return allAngleDiffsLeft;
    }

    private double[] getAllAngleDiffsRightHand() {
        if (allAngleDiffsRight == null) {
            List<Double> resultList = new LinkedList<>();
            SharedUtility.addToListRightHand(resultList, filterOutBackOfHand(phiDiffs));
            SharedUtility.addToListRightHand(resultList, filterOutBackOfHand(thetaDiffs));
            SharedUtility.addToListRightHand(resultList, filterOutBackOfHand(psiDiffs));
            allAngleDiffsRight = SharedUtility.list2Array(resultList);
        }
        return allAngleDiffsRight;
    }

    private double[] getAllAngleDiffsBothHands() {
        if (allAngleDiffsBoth == null) {
            List<Double> resultList = new LinkedList<>();
            resultList.addAll(filterOutBackOfHand(psiDiffs));
            resultList.addAll(filterOutBackOfHand(thetaDiffs));
            resultList.addAll(filterOutBackOfHand(psiDiffs));
            allAngleDiffsBoth = SharedUtility.list2Array(resultList);
        }
        return allAngleDiffsBoth;
    }

    /*
    the value for the back of the hand will never have a useful value because it just serves as the reference point to all other sensors of a given hand.
    it is however still part of the phiDiffs, thetaDiffs and psiDiffs arrays in order for the getArrayPosition() method to still work as intended.
    for calculations the back of the hand values can be filtered out with this method
     */
    private List<Double> filterOutBackOfHand(Double[] angleDiffs) {
        List<Double> resultList = new LinkedList<>();
        for (Double angleDiff : angleDiffs) {
            if (angleDiff != null) {
                resultList.add(angleDiff);
            }
        }
        return resultList;
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
            if (result[i] != null) result[i] = Math.toDegrees(phiDiffs[i]);
        }
        return result;
    }

    public Double[] getThetaDiffsAsDegrees() {
        Double[] result = new Double[thetaDiffs.length];
        for (int i = 0; i < result.length; i++) {
            if (result[i] != null) result[i] = Math.toDegrees(thetaDiffs[i]);
        }
        return result;
    }

    public Double[] getPsiDiffsAsDegrees() {
        Double[] result = new Double[psiDiffs.length];
        for (int i = 0; i < result.length; i++) {
            if (result[i] != null) result[i] = Math.toDegrees(psiDiffs[i]);
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
