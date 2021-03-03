package de.btu.dataglove.shared.webservice;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * class that is used for live feedback of currently recognized gestures
 */
public class RecognitionGesture {
    private static Map<Integer, Field> fieldMap;
    private double timeStamp;
    private double gesture00;
    private double gesture01;
    private double gesture02;
    private double gesture03;
    private double gesture04;
    private double gesture05;
    private double gesture06;
    private double gesture07;
    private double gesture08;
    private double gesture09;
    private double gesture10;
    private double gesture11;
    private double gesture12;
    private double gesture13;
    private double gesture14;
    private double gesture15;
    private double gesture16;
    private double gesture17;
    private double gesture18;
    private double gesture19;
    private double gesture20;
    private double gesture21;
    private double gesture22;
    private double gesture23;
    private double gesture24;
    private double gesture25;
    private double gesture26;
    private double gesture27;
    private double gesture28;
    private double gesture29;
    private double gesture30;
    private double gesture31;
    private double gesture32;
    private double gesture33;
    private double gesture34;
    private double gesture35;
    private double gesture36;
    private double gesture37;
    private double gesture38;
    private double gesture39;
    private double gesture40;
    private double gesture41;
    private double gesture42;
    private double gesture43;
    private double gesture44;
    private double gesture45;
    private double gesture46;
    private double gesture47;
    private double gesture48;
    private double gesture49;
    private double gesture50;
    private double gesture51;
    private double gesture52;
    private double gesture53;
    private double gesture54;
    private double gesture55;
    private double gesture56;
    private double gesture57;
    private double gesture58;
    private double gesture59;
    private double gesture60;
    private double gesture61;
    private double gesture62;
    private double gesture63;
    private double gesture64;
    private double gesture65;
    private double gesture66;
    private double gesture67;
    private double gesture68;
    private double gesture69;
    private double gesture70;
    private double gesture71;
    private double gesture72;
    private double gesture73;
    private double gesture74;
    private double gesture75;
    private double gesture76;
    private double gesture77;
    private double gesture78;
    private double gesture79;
    private double gesture80;
    private double gesture81;
    private double gesture82;
    private double gesture83;
    private double gesture84;
    private double gesture85;
    private double gesture86;
    private double gesture87;
    private double gesture88;
    private double gesture89;
    private double gesture90;
    private double gesture91;
    private double gesture92;
    private double gesture93;
    private double gesture94;
    private double gesture95;
    private double gesture96;
    private double gesture97;
    private double gesture98;
    private double gesture99;

    public RecognitionGesture(double timeStamp, Map<Integer, Double> recognizedGestures) {
        if (fieldMap == null) initFieldMap();
        this.timeStamp = timeStamp;
        Field currentField;
        for (Map.Entry<Integer, Double> pair : recognizedGestures.entrySet()) {
            currentField = fieldMap.get(pair.getKey());
            try {
                currentField.setDouble(this, pair.getValue());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                //this should never happen
            }
        }
    }

    /**
     * initializes a static map that stores for each possible gesture id the corresponding field of this class
     * examples:
     * 0  -> gesture00
     * 1  -> gesture01
     * 15 -> gesture15
     * ...
     */
    private static void initFieldMap() {
        Field[] fields = RecognitionGesture.class.getDeclaredFields();
        fieldMap = new HashMap<>();
        Integer gestureNumber = null;
        for (Field field : fields) {
            try {
                gestureNumber = Integer.parseInt(field.getName().substring(field.getName().length() - 2));
            } catch (NumberFormatException e) {
                //do nothing. this will happen for the timestamp and the fieldMap fields
            }
            fieldMap.put(gestureNumber, field);
        }
    }
}
