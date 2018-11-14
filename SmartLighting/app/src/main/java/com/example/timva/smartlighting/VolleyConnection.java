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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class VolleyConnection {
    private static VolleyConnection instance = null;
    private JSONObject requestResponse;
    private Context context;
    private RequestQueue queue;

    public VolleyConnection(Context context){
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    public static VolleyConnection getInstance(Context context){
        if(instance == null)
            instance = new VolleyConnection(context);
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

    private void onErrorListener(VolleyError error){
        Log.i("blindwall", "NOK");
    }
}
