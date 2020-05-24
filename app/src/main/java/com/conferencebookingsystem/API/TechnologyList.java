package com.conferencebookingsystem.API;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class TechnologyList {

    String url;
    HashMap<Integer,String> technologyList = new HashMap<>();

    private RequestQueue requestQueue;
    private static Context context;

    public TechnologyList(String url, Context context) {
        this.url = url;
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void getTechnologyAPI() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i=0; i<response.length();i++){
                                JSONObject results = response.getJSONObject(i);
                                int id = results.getInt("id");
                                String name = results.getString("name");
                                technologyList.put(id,name);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }

    public HashMap<Integer, String> getTechnologyList() {
        return technologyList;
    }
}
