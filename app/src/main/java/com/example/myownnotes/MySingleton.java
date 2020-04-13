package com.example.myownnotes;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton instance;

    private RequestQueue requestQueue;
    private static Context mycontext;

    public static String URL = "http://192.168.1.132:4000/";

    private MySingleton(Context context){
        mycontext = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized MySingleton getInstance(Context context){
        if(instance == null){
            instance = new MySingleton(context);
        }

        return instance;
    }

    private RequestQueue getRequestQueue() {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(mycontext.getApplicationContext());
        }

        return requestQueue;
    }

    public <T> void addToRequestQueue (Request<T> request){
        getRequestQueue().add(request);
    }

}
