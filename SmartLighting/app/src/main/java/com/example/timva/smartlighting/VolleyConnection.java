package com.example.timva.smartlighting;

import android.app.VoiceInteractor;
import android.content.Context;
import android.util.Log;

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
import java.util.Random;

public class VolleyConnection {
    private static VolleyConnection instance = null;
    private JSONObject requestResponse;
    private Context context;
    private RequestQueue queue;
    private VolleyListener volleyListener;

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
        sendRequest(url, "{\"devicetype\":\"TimsHueApp\"}", Request.Method.POST);
    }

    private void sendRequest(String requestUrl, final String requestBody, int requestMethod){
        JsonObjectRequest request = null;

        try {
            request = new JsonObjectRequest(
                    requestMethod,
                    requestUrl,
                    new JSONObject(requestBody),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            requestResponse = response;

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
                            JSONObject lamp1 = response.getJSONObject("lights").getJSONObject("1");
                            JSONObject lamp2 = response.getJSONObject("lights").getJSONObject("2");
                            JSONObject lamp3 = response.getJSONObject("lights").getJSONObject("3");
                            ArrayList<JSONObject> jobjects = new ArrayList<>();
                            jobjects.add(lamp1);
                            jobjects.add(lamp2);
                            jobjects.add(lamp3);
                            int id = 0;
                            for(JSONObject j : jobjects)
                            {
                                id++;
                                Lamp lamp = new Lamp(id,j.getJSONObject("state").getBoolean("on"), j.getJSONObject("state").getInt("bri"), j.getJSONObject("state").getInt("hue"), j.getJSONObject("state").getInt("sat") );
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

    private void onErrorListener(VolleyError error){
        Log.i("HUE", "NOK");


    }
}
