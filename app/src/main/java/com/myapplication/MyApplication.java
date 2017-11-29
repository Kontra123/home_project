package com.myapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by AmirG on 11/28/2017.
 */
public class MyApplication extends Application {

    private static Context context;
    private MyApplicationSharedPreferences myApplicationSharedPreferences;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        myApplicationSharedPreferences = new MyApplicationSharedPreferences();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

    public MyApplicationSharedPreferences getMyApplicationSharedPreferences() {
        return myApplicationSharedPreferences;
    }

}
