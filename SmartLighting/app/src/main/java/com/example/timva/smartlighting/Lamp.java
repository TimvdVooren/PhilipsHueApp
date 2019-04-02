package com.example.timva.smartlighting;

import android.os.Parcel;
import android.os.Parcelable;

public class Lamp implements Parcelable {
    private int id;
    private boolean on;
    private int bri;
    private int hue;
    private int sat;

    public Lamp(int id, boolean on, int bri, int hue, int sat) {
        super();
        this.id = id;
        this.on = on;
        this.bri = bri;
        this.hue = hue;
        this.sat = sat;
    }

    public Lamp(Parcel parcel){
        this.id = parcel.readInt();
        this.on = parcel.readByte() != 0;
        this.bri = parcel.readInt();
        this.hue = parcel.readInt();
        this.sat = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeByte((byte) (this.on ? 1 : 0));
        dest.writeInt(this.bri);
        dest.writeInt(this.hue);
        dest.writeInt(this.sat);
    }

    public static final Creator<Lamp> CREATOR = new Creator<Lamp>() {
        @Override
        public Lamp createFromParcel(Parcel source) {
            return new Lamp(source);
        }

        @Override
        public Lamp[] newArray(int size) {
            return new Lamp[size];
        }
    };

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
