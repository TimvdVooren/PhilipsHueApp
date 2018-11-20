package com.example.timva.smartlighting;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;

public class LampFragment extends Fragment implements VolleyListener {
    private Lamp lamp;
    private Switch powerSwitch;
    private SeekBar hueSlider;
    private SeekBar saturationSlider;
    private SeekBar brightnessSlider;
    private ImageView lampImage;
    private VolleyConnection connection;

    private View lampView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        lampView = inflater.inflate(R.layout.lamp_fragment, container, false);
        powerSwitch = lampView.findViewById(R.id.PowerSwitch);
        hueSlider = lampView.findViewById(R.id.HueSlider);
        saturationSlider = lampView.findViewById(R.id.SaturationSlider);
        brightnessSlider = lampView.findViewById(R.id.BrightnessSlider);

        hueSlider.setEnabled(false);
        saturationSlider.setEnabled(false);
        brightnessSlider.setEnabled(false);
        powerSwitch.setEnabled(false);

        connection = VolleyConnection.getInstance(lampView.getContext(), this);
        return lampView;
    }

    public void setLampView(Lamp lamp)
    {
        this.lamp = lamp;
        lampImage = lampView.findViewById(R.id.LampFragmentImage);
        powerSwitch.setEnabled(true);

        setSliders();
        setListeners();
    }

    private void setLampColor(){
        float[] lampHSV = new float[3];
        lampHSV[0] = ((float) lamp.getHue()/65535.f) * 360;
        lampHSV[1] = (float) lamp.getSat()/254.f;
        lampHSV[2] = (float) lamp.getBri()/254.f;
        lampImage.setColorFilter(Color.HSVToColor(lampHSV));
        connection.changeLamp(lamp);
    }

    private void setSliders(){
        hueSlider.setEnabled(true);
        saturationSlider.setEnabled(true);
        brightnessSlider.setEnabled(true);
        hueSlider.setProgress(lamp.getHue());
        saturationSlider.setProgress(lamp.getSat());
        brightnessSlider.setProgress(lamp.getBri());

        setLampColor();
    }

    private void setListeners() {
        powerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lamp.setOn(isChecked);
                connection.changeLamp(lamp);
            }
        });

        hueSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lamp.setHue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setLampColor();
            }
        });

        saturationSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lamp.setSat(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setLampColor();
            }
        });

        brightnessSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lamp.setBri(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setLampColor();
            }
        });
    }

    @Override
    public void OnLampAvailable(Lamp lamp) {

    }

    @Override
    public void OnLampError(String error) {

    }
}
