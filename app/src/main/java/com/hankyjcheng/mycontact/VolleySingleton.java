package com.hankyjcheng.mycontact;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton instance = null;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private static Context context;

    private VolleySingleton(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
        imageLoader = new ImageLoader(this.requestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(20);

            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }
        });
    }

    public static VolleySingleton getInstance(Context cxt) {
        context = cxt;
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return this.requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return this.imageLoader;
    }
}