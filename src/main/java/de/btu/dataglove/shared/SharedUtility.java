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

    public static void createFileAndWriteJson(String json, File outputFile) throws IOException {
        if (outputFile.createNewFile()) System.out.println("file" + outputFile.getName() + "created!");;
        Files.writeString(Path.of(outputFile.getPath()), json);
        System.out.println("your file can be found here: " + outputFile.getPath());
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
