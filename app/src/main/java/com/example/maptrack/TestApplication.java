package com.example.maptrack;

import android.app.Application;

import com.backendless.Backendless;

public class TestApplication extends Application {
    public static final String APPLICATION_ID  = "YOUR_APPLICATION_ID";
    public static final String API_KEY = "YOUR_API_KEY";
    public static final String SERVER_URL = "https://api.backendless.com";

    @Override
    public void onCreate() {
        super.onCreate();
        Backendless.setUrl(SERVER_URL);
        Backendless.initApp( getApplicationContext(),APPLICATION_ID,API_KEY);
    }
}
