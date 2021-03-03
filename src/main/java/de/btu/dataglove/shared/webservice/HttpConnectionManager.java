package de.btu.dataglove.shared.webservice;

import com.google.gson.*;
import de.btu.dataglove.shared.*;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * this class is used for communicating with the http server
 */
public class HttpConnectionManager {

    private static final OkHttpClient client = new OkHttpClient();
    private static String AUTHORIZATION_HEADER;
    private static String SERVER_URL;
    private static String NAME_OF_FRAME_DB;
    private static String NAME_OF_USER_DB;
    private static String NAME_OF_GESTURE_DB;
    private static String NAME_OF_EULER_GESTURE_DB;
    private static String NAME_OF_TASK_CALCULATOR;
    private static String NAME_OF_RECOGNITION_LOG;
    private static String NAME_OF_RECOGNITION_GESTURE_TABLE;
    private static int LIMIT_FOR_HTTP_REQUEST_SIZE;

    /**
     * this method needs to be called once by the application before communicating with the server
     * this way, the server url and authorization header do not need to be part of the public library of this project
     */
    public static void init(String authorizationHeader, String serverUrl, String nameOfFrameDB, String nameOfUserDB,
                            String nameOfGestureDB, String nameOfEulerGestureDB, String nameOfTaskCalculator,
                            String nameOfRecognitionLog, String nameOfRecognitionGestureTable, int limitForHttpRequestSize) {
        AUTHORIZATION_HEADER = authorizationHeader;
        SERVER_URL = serverUrl;
        NAME_OF_FRAME_DB = nameOfFrameDB;
        NAME_OF_USER_DB = nameOfUserDB;
        NAME_OF_GESTURE_DB = nameOfGestureDB;
        NAME_OF_EULER_GESTURE_DB = nameOfEulerGestureDB;
        NAME_OF_TASK_CALCULATOR = nameOfTaskCalculator;
        NAME_OF_RECOGNITION_LOG = nameOfRecognitionLog;
        NAME_OF_RECOGNITION_GESTURE_TABLE = nameOfRecognitionGestureTable;
        LIMIT_FOR_HTTP_REQUEST_SIZE = limitForHttpRequestSize;
    }

    /**
     * saves an object to the database.
     * the object can also be a list containing supported elements
     * returns true on success, returns false otherwise
     */
    public static boolean saveObjectToDatabase(Object object) throws IOException {
        String dbTable;
        if (object instanceof List) {
            dbTable = determineDatabaseTable(((List<?>) object).get(0).getClass());
        } else {
            dbTable = determineDatabaseTable(object.getClass());
        }

        String json = new Gson().toJson(object);
        String jsonWithAddedBoilerplate = addBoilerplateToJson(json);
        RequestBody body = RequestBody.create(jsonWithAddedBoilerplate, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(SERVER_URL + dbTable)// + "?values=" + json)
                .addHeader("Authorization", AUTHORIZATION_HEADER)
                .post(body)
                .build();
        OkHttpClient extendedTimeoutClient = client.newBuilder()
                .connectTimeout(0, TimeUnit.MILLISECONDS)
                .build();
        Call call = extendedTimeoutClient.newCall(request);

        Response response = call.execute();
        System.out.println("http response code: " + response.code());
        if (response.code() == 200 || response.code() == 201) {
            Objects.requireNonNull(response.body()).close();
            return true;
        }
        Objects.requireNonNull(response.body()).close();
        return false;
    }

    /**
     * retrieves a list of objects from the database
     *
     * @param clazz       the class of the objects that are to be retrieved
     * @param identifiers used to identify the objects in the database
     */
    public static <T> List<T> getObjectsFromDatabase(Class<T> clazz, Map<String, ?> identifiers) throws IOException {
        int offset = 0;
        boolean allObjectsReceived = false;
        List<T> objects = new LinkedList<>();
        //multiple get requests can be necessary to get all relevant objects because the server specifies a limit of objects sent per request
        while (!allObjectsReceived) {
            objects.addAll(sendGetRequestToDatabase(clazz, identifiers, offset));
            offset += LIMIT_FOR_HTTP_REQUEST_SIZE;
            if (objects.size() < offset) {
                allObjectsReceived = true;
            }
        }
        return objects;
    }

    private static <T> List<T> sendGetRequestToDatabase(Class<T> clazz, Map<String, ?> identifiers, int offset) throws IOException {
        String dbTable = determineDatabaseTable(clazz);

        StringBuilder url = new StringBuilder(SERVER_URL + dbTable);
        if (identifiers != null && !identifiers.isEmpty()) {
            url.append("?q={");
            List<String> identifiersInUrl = new LinkedList<>();
            for (String key : identifiers.keySet()) {
                identifiersInUrl.add(surroundWithBoilerplate(key, identifiers.get(key)));
            }
            url.append(String.join(",", identifiersInUrl))
                    .append("}" + "&start=").append(offset).append("&limit=").append(LIMIT_FOR_HTTP_REQUEST_SIZE);
        }

        Request request = new Request.Builder()
                .url(url.toString())
                .addHeader("Authorization", AUTHORIZATION_HEADER)
                .build();
        Call call = client.newCall(request);

        Response response = call.execute();
        String responseBodyString = Objects.requireNonNull(response.body()).string();
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(responseBodyString, JsonObject.class);
        JsonArray jsonArray = jsonObject.getAsJsonArray("result");
        List<T> resultList = new LinkedList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            resultList.add(gson.fromJson(jsonArray.get(i), (Type) clazz));
        }
        Objects.requireNonNull(response.body()).close();
        return resultList;
    }

    /**
     * retrieves an aggregate from the database
     *
     * @param clazz                        the class of the objects that are to be retrieved
     * @param aggregateFunction            the aggregate function to be retrieved (like count or max)
     * @param argumentForAggregateFunction the argument for the aggregate function (leaving this empty will send * as the argument)
     * @param identifiers                  used to identify the objects in the database
     * @return an Optional that will be empty if the aggregate function doesn't return anything
     */
    public static Optional<Integer> getAggregateFromDatabase(Class<?> clazz, String aggregateFunction, String argumentForAggregateFunction,
                                                             Map<String, ?> identifiers) throws IOException {

        JsonElement element = sendAggregateRequest(clazz, aggregateFunction, argumentForAggregateFunction, identifiers);
        if (element.isJsonNull()) return Optional.empty();
        JsonElement result = element
                .getAsJsonArray()
                .get(0)
                .getAsJsonObject()
                .get(aggregateFunction);
        if (result.isJsonNull()) return Optional.empty();
        return Optional.of(result.getAsInt());
    }

    /**
     * retrieves all names from frames or gestures from the database
     *
     * @param clazz this determines which database table is to be queried
     */
    public static List<String> getAllNamesFromDatabase(Class<?> clazz) throws IOException {
        String nameOfColumn;
        if (clazz.getTypeName().equals(DBFrame.class.getTypeName())) {
            nameOfColumn = "nameOfTask";
        } else nameOfColumn = "name";

        List<String> resultList = new LinkedList<>();
        JsonElement jsonElement = sendAggregateRequest(clazz, "distinct",
                nameOfColumn, null);
        if (!jsonElement.isJsonNull()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonElement entry = jsonArray.get(i).getAsJsonObject().get(nameOfColumn);
                if (!entry.isJsonNull()) {
                    resultList.add(entry.getAsString());
                }
            }
        }
        return resultList;
    }

    /**
     * retrieves the number of recordings of a given task from the database (column numberOfRecordings in the frameDB)
     *
     * @param nameOfTask the name of the task
     */
    public static int getNumberOfRecordings(String nameOfTask) throws IOException {
        Map<String, String> identifiers = new HashMap<>();
        identifiers.put("nameOfTask", nameOfTask);
        JsonElement element =  sendAggregateRequest(DBFrame.class, "max", "recordingNumber", identifiers);
        if (element.isJsonNull()) return 0;
        JsonElement result = element.getAsJsonArray()
                .getAsJsonArray()
                .get(0)
                .getAsJsonObject()
                .get("max");
        if (result.isJsonNull()) return 0;
        return result.getAsInt();
    }

    /**
     * sends a get request for an aggregate
     *
     * @param clazz                        the class of the objects that are to be retrieved
     * @param aggregateFunction            the aggregate function to be retrieved (like count or max)
     * @param argumentForAggregateFunction the argument for the aggregate function (leaving this empty will send * as the argument)
     * @param identifiers                  used to identify the objects in the database
     *///TODO currently not respecting the limit of 1000 entries per request
    private static JsonElement sendAggregateRequest(Class<?> clazz, String aggregateFunction, String argumentForAggregateFunction,
                                                              Map<String, ?> identifiers) throws IOException {

        String dbTable = determineDatabaseTable(clazz);
        String argumentForAggregateFunctionInURL;
        if (argumentForAggregateFunction == null || argumentForAggregateFunction.equals("")) {
            argumentForAggregateFunctionInURL = "*";
        } else argumentForAggregateFunctionInURL = argumentForAggregateFunction;

        StringBuilder url = new StringBuilder(SERVER_URL + dbTable);
        url.append("?q={\"$").append(aggregateFunction).append("\":")
                .append("\"").append(argumentForAggregateFunctionInURL).append("\"");
        if (identifiers != null && !identifiers.isEmpty()) {
            url.append(", ");
            List<String> identifiersInUrl = new LinkedList<>();
            for (String key : identifiers.keySet()) {
                identifiersInUrl.add(surroundWithBoilerplate(key, identifiers.get(key)));
            }

            url.append(String.join(",", identifiersInUrl));

        }
        url.append("}");

        Request request = new Request.Builder()
                .url(url.toString())
                .addHeader("Authorization", AUTHORIZATION_HEADER)
                .build();
        Call call = client.newCall(request);

        Response response = call.execute();
        String responseBodyString = Objects.requireNonNull(response.body()).string();
        if (response.code() == 204) return JsonNull.INSTANCE;
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(responseBodyString, JsonObject.class);
        return jsonObject.get("result");
    }

    /**
     * saves a recognition log to the database
     *
     * @param nameOfLog name of the log
     * @param framesMap frames that were checked against the gesture and a boolean indicating whether they were recognized
     * @param gesture   the gesture that was being checked against
     */
    public static boolean saveRecognitionLogToDatabase(String nameOfLog, Map<AbstractFrame, Boolean> framesMap, AbstractGesture gesture) throws IOException {
        List<RecognitionLog> logs = new LinkedList<>();
        List<Frame> frames = new LinkedList<>();
        for (AbstractFrame frame : framesMap.keySet()) {
            frame.nameOfTask = nameOfLog;
            frame.typeOfRecording = gesture.typeOfGesture;
            frame.recordingNumber = 0;
            logs.add(new RecognitionLog(nameOfLog, frame, framesMap.get(frame), gesture));
            if (frame instanceof Frame) {
                frames.add((Frame) frame);
            } else throw new AssertionError("type of frame not currently supported");
        }
        boolean savingFramesWorked = saveFramesToDatabase(frames);
        boolean savingLogsWorked = saveObjectToDatabase(logs);
        return savingFramesWorked && savingLogsWorked;
    }

    /**
     * @param nameOfLog
     * @return
     * @throws IOException
     */
    public static String getRecognitionLogFromDatabase(String nameOfLog) throws IOException {
        Map<String, String> identifiersLog = new HashMap<>();
        identifiersLog.put("name", nameOfLog);
        List<RecognitionLog> logs = getObjectsFromDatabase(RecognitionLog.class, identifiersLog);
        if (logs.get(0).gesture_name == null && logs.get(0).euler_name == null)
            throw new IOException("gesture name and euler name are both null"); //method has to be updated for each new algorithm

        List<Frame> frames = getFramesFromDatabase(nameOfLog);

        Map<String, Object> identifiersGesture = new HashMap<>();
        AbstractGesture gesture = null;
        if (logs.get(0).gesture_name != null) {
            identifiersGesture.put("name", logs.get(0).gesture_name);
            identifiersGesture.put("algorithmUsedForCalculation", logs.get(0).gesture_algorithmUsedForCalculation);
            identifiersGesture.put("algorithmParameters", Arrays.toString(logs.get(0).gesture_algorithmParameters)); //TODO THIS DOESN'T WORK
            gesture = getObjectsFromDatabase(Gesture.class, identifiersGesture).get(0);
        }
        if (logs.get(0).euler_name != null) {
            identifiersGesture.put("name", logs.get(0).euler_name);
            identifiersGesture.put("algorithmUsedForCalculation", logs.get(0).euler_algorithmUsedForCalculation);
            identifiersGesture.put("algorithmParameters", Arrays.toString(logs.get(0).euler_algorithmParameters)); //TODO THIS DOESN'T WORK
            gesture = getObjectsFromDatabase(EulerGesture.class, identifiersGesture).get(0);
        }

        Map<Frame, Boolean> logMap = new HashMap<>();
        for (RecognitionLog log : logs) {
            Frame frame = findFrameByFrameNumber(frames, log.frameDB_frameNumber);
            logMap.put(frame, log.wasRecognized);
        }
        return formatLog(logMap, gesture);
    }

    /**
     * finds a frame of a list based on its frameNumber
     *
     * @param frames      a list of frames
     * @param frameNumber the frameNumber
     * @return the frame you've been looking for
     * @throws IOException if the frame is unable to be found
     */
    private static Frame findFrameByFrameNumber(List<Frame> frames, int frameNumber) throws IOException {
        for (Frame frame : frames) {
            if (frame.frameNumber == frameNumber) {
                return frame;
            }
        }
        throw new IOException("frame with frameNumber " + frameNumber + "not found ");
    }

    /**
     * @param logMap  contains a Boolean for each frame indicating whether it was recognized or not
     * @param gesture the gesture that was being checked
     * @return a String containing the Log
     */
    private static String formatLog(Map<Frame, Boolean> logMap, AbstractGesture gesture) {
        Gson gson = new Gson();
        String gestureJson = gson.toJson(gesture);
        String framesJson = gson.toJson(logMap);

        return gestureJson + "\n\n\n" + framesJson;
    }

    /**
     * convenience method that retrieves a list of frames from the database based on their nameOfTask
     */
    public static List<Frame> getFramesFromDatabase(String nameOfTask) throws IOException {
        Map<String, String> identifiers = new HashMap<>();
        identifiers.put("nameOfTask", nameOfTask);

        List<DBFrame> dbFrames = getObjectsFromDatabase(DBFrame.class, identifiers);

        List<Frame> frames = new LinkedList<>();
        for (DBFrame dbFrame : Objects.requireNonNull(dbFrames)) {
            frames.add(dbFrame.convertToFrame());
        }
        return frames;
    }

    /**
     * convenience method that saves a list of frames to the database
     */
    public static boolean saveFramesToDatabase(List<Frame> frames) throws IOException {
        List<DBFrame> dbFrames = new LinkedList<>();
        for (Frame frame : frames) {
            dbFrames.add(new DBFrame((frame)));
        }
        return saveObjectToDatabase(dbFrames);
    }

    /**
     * convenience method that retrieves a list of gestures from the database based on their name
     */
    public static List<Gesture> getGesturesFromDatabase(String name) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        return getObjectsFromDatabase(Gesture.class, map);
    }

    /**
     * convenience method that retrieves a list of eulerGestures from the database based on their name
     */
    public static List<EulerGesture> getEulerGesturesFromDatabase(String name) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        return getObjectsFromDatabase(EulerGesture.class, map);
    }

    /**
     * requests a calculation from the server with the result then being saved to the database.
     * returns the exit code of the task calculator program
     *
     * @param encodedArgument  the encoded metadata of the task that is to be calculated
     * @param versionOfProgram the version of taskCalculator that is queried. example: 1.0.0
     */
    public static int requestCalculationFromServer(String encodedArgument, String versionOfProgram) throws IOException {
        String url = SERVER_URL + NAME_OF_TASK_CALCULATOR +
                "?argv=[\"" +
                encodedArgument +
                "\"]&jar=TaskCalculator-" + versionOfProgram + ".jar";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", AUTHORIZATION_HEADER)
                .build();
        OkHttpClient extendedTimeoutClient = client.newBuilder()
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .build();
        Call call = extendedTimeoutClient.newCall(request);

        Response response = call.execute();
        try {
            String responseBodyString = Objects.requireNonNull(response.body()).string();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseBodyString, JsonObject.class);
            int exitCode = jsonObject.get("exitcode").getAsInt();
            Objects.requireNonNull(response.body()).close();
            return exitCode;
        } catch (NullPointerException e) {
            throw new IOException("server response not as expected");
        }
    }

    public static boolean sendRecognizedGesturesToServer(double timeStamp, Map<Integer, Double> recognizedGestures) throws IOException {
        RecognitionGesture rg = new RecognitionGesture(timeStamp, recognizedGestures);
        return saveObjectToDatabase(rg);
    }

    /**
     * each class has their own equivalent table in the database. This returns the table for a given class
     */
    private static String determineDatabaseTable(Class<?> clazz) {
        if (clazz.getTypeName().equals(Gesture.class.getTypeName())) {
            return NAME_OF_GESTURE_DB;
        }
        if (clazz.getTypeName().equals(EulerGesture.class.getTypeName())) {
            return NAME_OF_EULER_GESTURE_DB;
        }
        if (clazz.getTypeName().equals(DBFrame.class.getTypeName())) {
            return NAME_OF_FRAME_DB;
        }
        if (clazz.getTypeName().equals(RecognitionLog.class.getTypeName())) {
            return NAME_OF_RECOGNITION_LOG;
        }
        if (clazz.getTypeName().equals(RecognitionGesture.class.getTypeName())) {
            return NAME_OF_RECOGNITION_GESTURE_TABLE;
        }
        throw new AssertionError("class not supported by db: " + clazz);
    }

    /**
     * surrounds an attribute/value pair with additional chars that are needed for it to be used in the http request
     */
    private static String surroundWithBoilerplate(String attribute, Object value) {
        String firstPart = "\"\\\"" + attribute + "\\\"\" : ";
        String secondPart;
        if (value instanceof String) {
            secondPart = "\"'" + value + "'\"";
        } else {
            secondPart = value.toString();
        }
        return firstPart + secondPart;
    }

    /**
     * adds some boilerplate that is needed by the http server when sending data in the http request body
     */
    private static String addBoilerplateToJson(String json) {
        return "{\"values\":" + json + "}";
    }





}