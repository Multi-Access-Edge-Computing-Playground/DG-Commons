package de.btu.dataglove.shared;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class SharedUtility {

    public static List<Double> array2List(double[] array) {
        return Arrays.stream(array)
                .boxed()
                .collect(Collectors.toList());
    }

    public static double[] list2Array(List<Double> list) {
        return list.stream()
                .mapToDouble(Double::doubleValue)
                .toArray();
    }

    public static int getArrayPositionOfSensor(int sensorNumber) throws  InvalidSensorNumberException {
        switch (sensorNumber) {
            case 24: return 0;
            case 25: return 1;
            case 26: return 2;
            case 27: return 3;
            case 28: return 4;
            case 29: return 5;
            case 30: return 6;
            case 40: return 7;
            case 41: return 8;
            case 42: return 9;
            case 43: return 10;
            case 44: return 11;
            case 45: return 12;
            case 46: return 13;
            default:
                throw new InvalidSensorNumberException(sensorNumber);
        }
    }

    public static int getSensorNumberFromArrayIndex(int arrayIndex) throws InvalidArrayIndexException {
        switch (arrayIndex) {
            case 0: return 24;
            case 1: return 25;
            case 2: return 26;
            case 3: return 27;
            case 4: return 28;
            case 5: return 29;
            case 6: return 30;
            case 7: return 40;
            case 8: return 41;
            case 9: return 42;
            case 10: return 43;
            case 11: return 44;
            case 12: return 45;
            case 13: return 46;
            default:
                throw new InvalidArrayIndexException(arrayIndex);
        }
    }

    /*
    serializes an object to JSON using gson
    */
    public static String serializeObject(Object object) throws IOException {
        return new Gson().toJson(object);
    }

    /*
    deserializes an object from JSON using gson
     */
    public static <T> T deserializeObject(String serializedObject, Class<T> clazz) {
        return new Gson().fromJson(serializedObject, clazz);
    }

    /*
     * returns an array containing lists of all the sensorData for an input list of
     * frames
     */
    public static List<Double>[] combineAllSensorData(List<Frame> frames) {
        List<Double>[] result = new List[SharedConstants.TOTAL_SENSOR_DATA_OF_FRAME];
        List<double[]> allFrameDataList = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
            List<Double> sensorValues = new LinkedList<>();
            for (int j = 0; j < frames.size(); j++) {
                if (i == 0) {
                    allFrameDataList.add(frames.get(j).getAllFrameData());
                }
                sensorValues.add(allFrameDataList.get(j)[i]);
            }
            result[i] = sensorValues;
        }
        return result;
    }

    public static class InvalidSensorNumberException extends Exception {
        private static final long serialVersionUID = 612554581182062L;

        private InvalidSensorNumberException(int sensorNumber) {
            super(SharedConstants.INVALID_SENSOR_NUMBER_EXCEPTION + sensorNumber);
        }
    }

    public static class InvalidArrayIndexException extends Exception {
        private static final long serialVersionUID = -4772924540264831249L;

        private InvalidArrayIndexException(int arrayIndex) {
            super(SharedConstants.INVALID_ARRAY_INDEX_EXCEPTION + arrayIndex);
        }
    }
}
