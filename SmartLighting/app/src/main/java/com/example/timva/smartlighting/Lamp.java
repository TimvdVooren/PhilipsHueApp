package com.example.timva.smartlighting;

import java.io.Serializable;

public class Lamp implements Serializable {
    private int id;
    private boolean on;
    private int bri;
    private int hue;
    private int sat;

    public Lamp(int id, boolean on, int bri, int hue, int sat) {
        this.id = id;
        this.on = on;
        this.bri = bri;
        this.hue = hue;
        this.sat = sat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public int getBri() {
        return bri;
    }

    public void setBri(int bri) {
        this.bri = bri;
    }

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public int getSat() {
        return sat;
    }

    public void setSat(int sat) {
        this.sat = sat;
    }
}
