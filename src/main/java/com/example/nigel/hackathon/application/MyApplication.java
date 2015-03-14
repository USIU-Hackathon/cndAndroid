package com.example.nigel.hackathon.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by nigel on 3/14/15.
 */
public class MyApplication extends Application {

    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this;

    }

    public static Context getMyApplicationContext(){

        return myApplication;

    }


}
