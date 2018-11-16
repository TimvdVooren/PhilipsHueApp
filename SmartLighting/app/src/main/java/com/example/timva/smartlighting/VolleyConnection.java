package com.example.timva.smartlighting;

import android.app.VoiceInteractor;
import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class VolleyConnection {
    private static VolleyConnection instance = null;
    private Context context;
    private RequestQueue queue;
    private VolleyListener volleyListener;

    private String url = "";
    private String apiCode = null;

    public VolleyConnection(Context context, VolleyListener listener){
        this.context = context;
        queue = Volley.newRequestQueue(context);
        this.volleyListener = listener;
    }

    public static VolleyConnection getInstance(Context context, VolleyListener listener){
        if(instance == null)
            instance = new VolleyConnection(context, listener);
        return instance;
    }

    public void establishConnection(String url){
        this.url = url;
        sendRequest(url, "{\"devicetype\":\"TimsHueApp\"}", Request.Method.POST);
    }

    public void changeLamp(Lamp lamp){
        String lampUrl = url + apiCode + "/lights/" + lamp.getId() + "/state/";
        String requestBody = String.format("{\"on\":%b, \"bri\":%d, \"hue\":%d, \"sat\":%d}", lamp.isOn(), lamp.getBri(), lamp.getHue(), lamp.getSat());
        sendRequest(lampUrl, requestBody, Request.Method.PUT);
    }

    private void sendRequest(final String requestUrl, final String requestBody, int requestMethod){
        CustomJsonRequest request = null;

        try {
            request = new CustomJsonRequest(
                    requestMethod,
                    requestUrl,
                    new JSONObject(requestBody),
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            onResponseListener(response, requestUrl);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            onErrorListener(error);
                        }
                    }
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.queue.add(request);
    }

    public void getLamps(String requestUrl){

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                requestUrl,
                null,

                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("HUE", "OK");
                        try {
                            JSONObject lights = response.getJSONObject("lights");
                            JSONArray jsonArray = lights.toJSONArray(lights.names());
                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject lamp1 = jsonArray.getJSONObject(i);
                                Lamp lamp = new Lamp(i+1,lamp1.getJSONObject("state").getBoolean("on"), lamp1.getJSONObject("state").getInt("bri"), lamp1.getJSONObject("state").getInt("hue"), lamp1.getJSONObject("state").getInt("sat") );
                                volleyListener.OnLampAvailable(lamp);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HUE", "Error");

                        volleyListener.OnLampError(error.toString());
                    }
                }
        );
        this.queue.add(request);
    }

    private void onResponseListener(JSONArray response, String url){
        try {
            if(apiCode == null) {
                apiCode = response.getJSONObject(0).getJSONObject("success").getString("username");
                getLamps(url + apiCode);
            } else{

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void onErrorListener(VolleyError error){
        Log.i("HUE", "NOK");
    }

    public String getApiCode(){
        return apiCode;
    }

    public void setApiCode(String apiCode){
        this.apiCode = apiCode;
    }

    public String getApiCode() {
        return apiCode;
    }
}
