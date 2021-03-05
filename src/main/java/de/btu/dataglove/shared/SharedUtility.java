package de.btu.dataglove.shared;

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

    public static int getArrayPositionOfSensor(int sensorNumber) {
        return switch (sensorNumber) {
            case 24 -> 0;
            case 25 -> 1;
            case 26 -> 2;
            case 27 -> 3;
            case 28 -> 4;
            case 29 -> 5;
            case 30 -> 6;
            case 40 -> 7;
            case 41 -> 8;
            case 42 -> 9;
            case 43 -> 10;
            case 44 -> 11;
            case 45 -> 12;
            case 46 -> 13;
            default -> throw new AssertionError("sensorNumber " + sensorNumber + " is invalid");
        };
    }

    public static int getSensorNumberFromArrayIndex(int arrayIndex) {
        return switch (arrayIndex) {
            case 0 -> 24;
            case 1 -> 25;
            case 2 -> 26;
            case 3 -> 27;
            case 4 -> 28;
            case 5 -> 29;
            case 6 -> 30;
            case 7 -> 40;
            case 8 -> 41;
            case 9 -> 42;
            case 10 -> 43;
            case 11 -> 44;
            case 12 -> 45;
            case 13 -> 46;
            default -> throw new AssertionError("arrayIndex " + arrayIndex + " is invalid");
        };
    }

    /*
    serializes and encodes an object
    */
    public static String serializeObject(Serializable serializable) throws IOException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream so = new ObjectOutputStream(bo);
        so.writeObject(serializable);
        so.flush();
        return new String(Base64.getEncoder().encode(bo.toByteArray()));
    }

    /*
     * decodes and deserializes an object
     */
    public static Object deserializeObject(String encodedObject) throws IOException, ClassNotFoundException {
        byte[] b = Base64.getDecoder().decode(encodedObject.getBytes());
        ByteArrayInputStream bi = new ByteArrayInputStream(b);
        ObjectInputStream si = new ObjectInputStream(bi);
        return si.readObject();
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
}
