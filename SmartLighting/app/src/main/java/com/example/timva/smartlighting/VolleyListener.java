package com.example.timva.smartlighting;

public interface VolleyListener {

    public void OnLampAvailable(Lamp lamp);
    public void OnLampError(String error);
}
