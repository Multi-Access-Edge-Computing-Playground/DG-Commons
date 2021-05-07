package de.btu.dataglove.shared;

import de.btu.dataglove.shared.calculations.GestureCalculationCircular;
import de.btu.dataglove.shared.calculations.GestureCalculationLinear;
import de.btu.dataglove.shared.calculations.GestureCalculationNaiveBayes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
represents algorithms used to calculate gestures
 */
public enum Algorithms {
    STATIC_GESTURE_LINEAR(0, 1) {
        @Override
        public Gesture calculateGestureModel(String nameOfResult, List<Frame> frames, double[] parametersForCalculation) {
            return GestureCalculationLinear.calculateGestureModel(nameOfResult, frames, parametersForCalculation);
        }
    },
    STATIC_GESTURE_CIRCULAR(1, 1) {
        @Override
        public EulerGesture calculateGestureModel(String nameOfResult, List<Frame> frames, double[] parametersForCalculation) {
            return GestureCalculationCircular.calculateGestureModel(nameOfResult, frames, parametersForCalculation);
        }
    },
    STATIC_GESTURE_NAIVE_BAYES(2, 1) {
        @Override
        public GestureNaiveBayes calculateGestureModel(String nameOfResult, List<Frame> frames, double[] parametersForCalculation) {
            return GestureCalculationNaiveBayes.calculateGestureModel(nameOfResult, frames, parametersForCalculation);
        }
    };

    private final int value; //the value representing the algorithm in the database
    private final int numberOfParameters;

    /**
     * calculates a gesture model based on a list of frames and some parameters if applicable
     * @param nameOfResult the name of the resulting gesture model
     * @param frames the frames used to calculate the model
     * @param parametersForCalculation parameters for tweaking the model calculation
     * @return a calculated gesture model
     */
    public abstract AbstractGesture calculateGestureModel(String nameOfResult, List<Frame> frames, double[] parametersForCalculation);

    //reverse lookup for getting the corresponding algorithm from an int
    private static final Map<Integer, Algorithms> lookup = new HashMap<>();

    static {
        for (Algorithms a : Algorithms.values()) {
            lookup.put(a.toInt(), a);
        }
    }

    Algorithms(int value, int numberOfParameters) {
        this.value = value;
        this.numberOfParameters = numberOfParameters;
    }

    public int toInt() {
        return value;
    }

    public static Algorithms get(int valueOfAlgorithm) {
        return lookup.get(valueOfAlgorithm);
    }

    public int getNumberOfParameters() {
        return numberOfParameters;
    }
}
