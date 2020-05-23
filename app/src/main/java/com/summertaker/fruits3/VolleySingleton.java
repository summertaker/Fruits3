package com.summertaker.fruits3;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

class VolleySingleton {
    @SuppressLint("StaticFieldLeak")
    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    private VolleySingleton(Context context) {
        // Specify the application context
        mContext = context;
        // Get the request queue
        mRequestQueue = getRequestQueue();
    }

    static synchronized VolleySingleton getInstance(Context context) {
        // If Instance is null then initialize new Instance
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        // Return MySingleton new Instance
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        // If RequestQueue is null the initialize new RequestQueue
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }

        // Return RequestQueue
        return mRequestQueue;
    }

    <T> void addToRequestQueue(Request<T> request) {
        // Add the specified request to the request queue
        getRequestQueue().add(request);
    }
}
