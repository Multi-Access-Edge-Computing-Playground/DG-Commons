package de.btu.dataglove.shared;

public class RobotAction {

    private int Sende_ID;
    private String Action_String;
    private int Action_ID;
    private int Bauteil_ID;
    private double[] Koordinaten;

    public RobotAction(int Sende_ID, String Action_String, int Action_ID, int Bauteil_ID, double[] Koordinaten) {
        this.Sende_ID = Sende_ID;
        this.Action_String = Action_String;
        this.Action_ID = Action_ID;
        this.Bauteil_ID = Bauteil_ID;
        this.Koordinaten = Koordinaten;
    }

    public int getSende_ID() {
        return Sende_ID;
    }

    public String getAction_String() {
        return Action_String;
    }

    public int getAction_ID() {
        return Action_ID;
    }

    public int getBauteil_ID() {
        return Bauteil_ID;
    }

    public double[] getKoordinaten() {
        return Koordinaten;
    }

    public void setSende_ID(int sende_ID) {
        Sende_ID = sende_ID;
    }

    public void setAction_String(String action_String) {
        Action_String = action_String;
    }

    public void setAction_ID(int action_ID) {
        Action_ID = action_ID;
    }

    public void setBauteil_ID(int bauteil_ID) {
        Bauteil_ID = bauteil_ID;
    }

    public void setKoordinaten(double[] koordinaten) {
        Koordinaten = koordinaten;
    }
}
