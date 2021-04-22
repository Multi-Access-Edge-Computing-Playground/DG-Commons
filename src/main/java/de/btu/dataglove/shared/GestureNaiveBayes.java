package de.btu.dataglove.shared;

public class GestureNaiveBayes extends AbstractGesture {

    double threshold;
    double[] kappas;
    double[] variances;

    public GestureNaiveBayes(String name, int typeOfGesture, int algorithmUsedForCalculation, double[] algorithmParameters, double threshold,
                             double[] kappas, double[] variances) {
        super(name, typeOfGesture, algorithmUsedForCalculation, algorithmParameters);
        this.threshold = threshold;
        this.kappas = kappas;
        this.variances = variances;
    }
}
