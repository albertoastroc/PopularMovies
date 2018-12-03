package com.example.alberto.popularmovies.helpers;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestQue {

    private static RequestQueue sRequestQueue;

    public static RequestQueue getVolleyRequestQueueInstance(Context context) {

        if (sRequestQueue == null) {
            synchronized (new Object()) {

                sRequestQueue = Volley.newRequestQueue(context);
            }

        }
        return sRequestQueue;

    }

}
