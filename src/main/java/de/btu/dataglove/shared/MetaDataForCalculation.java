package de.btu.dataglove.shared;

import com.google.gson.Gson;
import java.util.List;

/*
this class is used to build transfer information about what should be calculated from the app to the taskCalculator
 */
public class MetaDataForCalculation {
    public final List<String> namesOfRecordings;
    public final String nameOfCalculationResult;
    public final Algorithms usedAlgorithm;
    public final double[] parametersForAlgorithm;

    private static transient final Gson gson = new Gson();

    public MetaDataForCalculation(List<String> namesOfTasks, String nameOfCalculationResult, Algorithms usedAlgorithm, double[] parametersForAlgorithm) {
        this.namesOfRecordings = namesOfTasks;
        this.nameOfCalculationResult = nameOfCalculationResult;
        this.usedAlgorithm = usedAlgorithm;
        this.parametersForAlgorithm = parametersForAlgorithm;
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public static MetaDataForCalculation fromJson(String jsonString) {
        return gson.fromJson(jsonString, MetaDataForCalculation.class);
    }
}
