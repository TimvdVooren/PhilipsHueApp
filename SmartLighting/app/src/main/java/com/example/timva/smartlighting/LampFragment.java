package com.example.timva.smartlighting;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class LampFragment extends Fragment {
    private Lamp lamp;
    private Switch powerSwitch;
    private SeekBar hueSlider;
    private SeekBar saturationSlider;
    private SeekBar brightnessSlider;
    private ImageView lampImage;
    private VolleyConnection connection;
    private TextView title;
    private Button disco;
    private Timer timer;
    private boolean discoOn;
    private TextView hueText;
    private TextView satText;
    private TextView brightnessText;

    private View lampView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        lampView = inflater.inflate(R.layout.lamp_fragment, container, false);
        lampView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        powerSwitch = lampView.findViewById(R.id.PowerSwitch);
        hueSlider = lampView.findViewById(R.id.HueSlider);
        saturationSlider = lampView.findViewById(R.id.SaturationSlider);
        brightnessSlider = lampView.findViewById(R.id.BrightnessSlider);
        lampImage = lampView.findViewById(R.id.LampFragmentImage);
        title = lampView.findViewById(R.id.LampFragmentTitle);
        disco = lampView.findViewById(R.id.LampfragmentDisco);

        timer = new Timer();
        discoOn = false;

        setSliders(false, 0, 0, 0);
        powerSwitch.setEnabled(false);
        disco.setEnabled(false);
        lampImage.setVisibility(View.INVISIBLE);

        hueText = lampView.findViewById(R.id.HueText);
        satText = lampView.findViewById(R.id.SaturationText);
        brightnessText = lampView.findViewById(R.id.BrightnessText);
        if(Locale.getDefault().getLanguage() == "nl")
        {
            hueText.setText("Tint");
            satText.setText("Verzadiging");
            brightnessText.setText("Helderheid");
            powerSwitch.setText("Aan/Uit");
        }
        else
        {
            hueText.setText("Hue");
            satText.setText("Saturation");
            brightnessText.setText("Brightness");
            powerSwitch.setText("Power");
        }


        return lampView;
    }

    public void setLampView(Lamp lamp)
    {
        this.lamp = lamp;
        title.setText("Lamp: " + lamp.getId());
        powerSwitch.setEnabled(true);
        powerSwitch.setChecked(lamp.isOn());
        disco.setEnabled(true);
        if(lamp.isOn())
            lampImage.setVisibility(View.VISIBLE);
        else
            lampImage.setVisibility(View.INVISIBLE);

        setSliders(true, lamp.getHue(), lamp.getSat(), lamp.getBri());
        setListeners();
    }

    private void setSliders(boolean enabled, int hue, int sat, int bri){
        hueSlider.setEnabled(enabled);
        saturationSlider.setEnabled(enabled);
        brightnessSlider.setEnabled(enabled);
        hueSlider.setProgress(hue);
        saturationSlider.setProgress(sat);
        brightnessSlider.setProgress(bri);
        if(lamp != null)
            setLampColor();
    }

    public void setConnection(VolleyConnection connection) {
        this.connection = connection;
    }

    private void setLampColor(){
        float[] lampHSV = new float[3];
        lampHSV[0] = ((float) lamp.getHue()/65535.f) * 360;
        lampHSV[1] = (float) lamp.getSat()/254.f;
        lampHSV[2] = (float) lamp.getBri()/254.f;
        lampImage.setColorFilter(Color.HSVToColor(lampHSV));
        connection.changeLamp(lamp);
    }

    private void setListeners() {
        powerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lamp.setOn(isChecked);
                if(isChecked)
                    lampImage.setVisibility(View.VISIBLE);
                else
                    lampImage.setVisibility(View.INVISIBLE);
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

        disco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(discoOn)
                {
                    discoOn = false;
                    timer.cancel();
                    timer.purge();
                    timer = new Timer();
                }
                else
                {
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            Random random = new Random();
                            lamp.setHue(random.nextInt(65535));
                            setLampColor();
                            setSliders(true, lamp.getHue(), lamp.getSat(), lamp.getBri());
                        }
                    },500,500);
                    discoOn = true;
                }
            }
        });
    }

    public Lamp getLamp() {
        return lamp;
    }
}
