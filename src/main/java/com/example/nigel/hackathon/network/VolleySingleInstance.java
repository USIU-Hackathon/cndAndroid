package com.example.nigel.hackathon.network;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.nigel.hackathon.application.MyApplication;

/**
 * Created by nigel on 3/14/15.
 */
public class VolleySingleInstance {

    private static VolleySingleInstance volleySingleInstance = null;

    private RequestQueue requestQueue;

    private ImageLoader imageLoader;

    private VolleySingleInstance(){

        requestQueue = Volley.newRequestQueue(MyApplication.getMyApplicationContext());

        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {

            LruCache<String, Bitmap> myImageCache = new LruCache<>((int) (Runtime.getRuntime().maxMemory()/1024/8));

            @Override
            public Bitmap getBitmap(String url) {
                return myImageCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                myImageCache.put(url,bitmap);
            }
        });

    }

    public static VolleySingleInstance getVolleySingleInstance(){
        if (volleySingleInstance == null){
            volleySingleInstance = new VolleySingleInstance();
        }
        return volleySingleInstance;
    }

    public RequestQueue getSinglereqRestQueue(){

        return requestQueue;

    }

    public ImageLoader getSingleImageLoader(){

        return imageLoader;

    }


}
